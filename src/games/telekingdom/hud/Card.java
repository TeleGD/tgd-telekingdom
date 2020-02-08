package games.telekingdom.hud;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

public class Card {

	/* Template de la carte */
	private CardTemplate cardTemplate;

	private Request request;

	//etat de la carte : 0 au milieu, 1 à droite, -1 à gauche, 2 si confirmé à droite et -2 si confirmé à gauche
	private int state;
	private boolean animGo;
	private boolean animGetOut;
	private boolean animGetIn;

	private Font font;
	private Audio sound;

	/* Taille et position sur l'écran */
	private int size;
	private int x;
	private int y;

	private double speed;
	private boolean speedPos;
	private double acc;
	private int goal;
	private float tmax;
	private int decalage;

	//a recuperer dans la base des cartes
	private int[] effet;

	private Color answerBackground = new Color(200,200,200,150);

	public Card(CardTemplate cardTemplate) {
		this.cardTemplate = cardTemplate;
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.request = new Request(container, game, this.cardTemplate.getRequest());
		this.state = 0; //on commence carte au milieu
		this.animGo = false;
		this.animGetOut = false;
		this.animGetIn = false;
		// this.font = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 12);
		this.font = AppLoader.loadFont("/fonts/telekingdom/SpecialElite.ttf", AppFont.BOLD, 12);
		this.sound = AppLoader.loadAudio("/sounds/telekingdom/turn-page.ogg");
		this.size = (int) (426 / 1920f * container.getWidth());
		this.x = (container.getWidth() - this.size) / 2;
		this.y = -this.size;
		this.initGetIn(y, (int) (343 / 720f * container.getHeight()), (int) ((720 - 40) / 720f * container.getHeight()) - size);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if(animGetIn) {
			getIn(container, game, delta);
		} else {
			if (animGo) {
				go(container, game, delta);
			} else {
				if (animGetOut) {
					getOut(container, game, delta);
				} else {
					if (input.isKeyPressed(Input.KEY_LEFT) && !input.isKeyPressed(Input.KEY_RIGHT)) { //si on appuie sur gauche et pas droite
						if (state==-1) {
							confirmLeft(container, game);
						} else {
							shiftLeft(container, game);
						}
					}
					if (input.isKeyPressed(Input.KEY_RIGHT) && !input.isKeyPressed(Input.KEY_LEFT)) { //si on appuie sur droite et pas gauche
						if (state==1) {
							confirmRight(container, game);
						} else {
							shiftRight(container, game);
						}
					}
				}
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		request.render(container, game, context);
		Image image = this.cardTemplate.getCharacter ().getImage ();
		context.drawImage(image, x, y, x + size, y + size, 0, 0, image.getWidth(), image.getHeight());
		if (!animGetIn && !animGo && !animGetOut) {
			context.setColor(answerBackground);
			if (state==-1) {
				context.fillRect(x, y, this.font.getWidth(this.cardTemplate.getResponse(0)) + 8, this.font.getHeight(this.cardTemplate.getResponse(0)) + 8);
				context.setColor(Color.black);
				context.setFont(this.font);
				context.drawString(this.cardTemplate.getResponse (0), x+4, y+4);
			} else if (state==1) {
				context.fillRect(x + size - this.font.getWidth(this.cardTemplate.getResponse(1)) - 8, y, this.font.getWidth(this.cardTemplate.getResponse(1)) + 8, this.font.getHeight(this.cardTemplate.getResponse(1)) +8);
				context.setColor(Color.black);
				context.setFont(this.font);
				context.drawString(this.cardTemplate.getResponse (1), x + size - this.font.getWidth(this.cardTemplate.getResponse(1))-4, y+4);
			}
		}
	}

	private void shiftRight(GameContainer container, StateBasedGame game) { //on décale la carte à droite
		state += 1;
		decalage = container.getWidth()/8;
		initGo(x,x+decalage);
	}

	private void shiftLeft(GameContainer container, StateBasedGame game) { // on décale la carte à gauche
		state -= 1;
		decalage = container.getWidth()/8;
		initGo(x,x-decalage);
	}

	//initGo et go : pour les etats -1, 0 et 1

	private void initGo(int dep, int fin) {
		tmax = 400;
		speed = 2*(fin-dep)/tmax;
		acc = -speed/tmax;
		animGo = true;
		speedPos = (speed >= 0);
		goal = fin;
	}

	private void go(GameContainer container, StateBasedGame game, int d) {
		if (speedPos == (speed>0) && x*(speedPos ? 1 : -1) < goal*(speedPos ? 1 : -1)) {
			x+=d*speed;
			speed += d*acc;
		} else {
			x=goal;
			animGo = false;
		}
	}

	//initGetOut et getOut :pour confirmer un choix

	private void initGetOut(int dep, int fin) {
		tmax = 500;
		speed = 2*(fin-dep)/tmax;
		acc = -speed/tmax;
		animGetOut = true;
		speedPos = (speed >= 0);
		goal = fin;
	}

	private void getOut(GameContainer container, StateBasedGame game, int d) {
		if (speedPos == (speed>0) && y*(speedPos ? 1 : -1) < goal*(speedPos ? 1 : -1)) {
			y+=d*speed;
			speed += d*acc;
		} else {
			y=goal;
			animGetOut = false;
		}
	}

	private void confirmLeft(GameContainer container, StateBasedGame game) {
		state = -2;
		effet = cardTemplate.getEffect(0);
		initGetOut(y,(int) (container.getHeight()*1.2));
		this.sound.playAsSoundEffect(1, .6f, false);
	}

	private void confirmRight(GameContainer container, StateBasedGame game) {
		state = 2;
		effet = cardTemplate.getEffect(1);
		initGetOut(y,(int) (container.getHeight()*1.2));
		this.sound.playAsSoundEffect(1, .6f, false);
	}

	private void initGetIn(int dep, int fin, int max) {
		tmax = 700;
		speed = 2*(max-dep)/tmax;
		acc = -speed/tmax;
		animGetIn = true;
		speedPos = (speed >= 0);
		goal = fin;
	}

	private void getIn(GameContainer container, StateBasedGame game, int d) {
		if (speed>0 || y>goal) {
			y+=d*speed;
			speed += d*acc;
		} else {
			y=goal;
			animGetIn = false;
		}
	}

	public int getState() {
		return state;
	}

	public int[] getEffect() {
		return effet;
	}

	public CardTemplate getCardTemplate() {
		return cardTemplate;
	}

}
