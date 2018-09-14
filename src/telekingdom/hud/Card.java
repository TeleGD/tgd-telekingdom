package telekingdom.hud;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import telekingdom.World;

public class Card {

	private World w;

	//template de la carte
	private CardTemplate cardTemplate;

	private boolean animGo;
	private boolean animGetOut;
	private boolean animGetIn;

	private double speed;
	private boolean speedPos;
	private double acc;
	private int goal;
	private float tmax;
	private int decalage;

	//dimensions
	private int x;
	private int y;
	private int length;

	//etat de la carte : 0 au milieu, 1 à droite et -1 à gauche
	private int state;
	private boolean piochee;

	//a recuperer dans la base des cartes
	private List<Integer> effet;


	public Card (World world, CardTemplate cardTemplate) {
		this.cardTemplate = cardTemplate;

		w = world;

		state = 0; //on commence carte au milieu

		effet = new ArrayList<Integer>();
		effet.add(-20);
		effet.add(20);
		
		piochee = false;

		animGo = false;
		animGetOut = false;
		animGetIn = false;
		
		length = (int) (0.22*world.getWidth());
		
		x = world.getWidth()/2 - length/2;
		y = -length;
		System.out.println (this.cardTemplate.getType ());
		System.out.println (this.cardTemplate.getCharacter ().getName ());
		System.out.println (this.cardTemplate.getRequest ());
		System.out.println (this.cardTemplate.getResponse (0));
		System.out.println (this.cardTemplate.getResponse (1));
		System.out.println (this.cardTemplate.getEffect (0, 0));
		System.out.println (this.cardTemplate.getEffect (0, 1));
		System.out.println (this.cardTemplate.getEffect (1, 0));
		System.out.println (this.cardTemplate.getEffect (1, 1));
		
		//test
		//setPiocheeTrue();
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if (piochee) {
			
			if(animGetIn) {
				getIn(delta);
			} else {
				if (animGo) {
					go(delta);
				} else {
					if (animGetOut) {
						getOut(delta);
					} else {
						if (input.isKeyPressed(Input.KEY_LEFT) && !input.isKeyPressed(Input.KEY_RIGHT)) { //si on appuie sur gauche et pas droite
							if (state==-1) {
								confirmLeft();
							} else {
								shiftLeft();
							}
						}
						if (input.isKeyPressed(Input.KEY_RIGHT) && !input.isKeyPressed(Input.KEY_LEFT)) { //si on appuie sur droite et pas gauche
							if (state==1) {
								confirmRight();
							} else {
								shiftRight();
							}
						}
					}
				}
			}
			
		}
		
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		Image image = this.cardTemplate.getCharacter ().getImage ();
		context.drawImage(image, x, y, x+length, y+length,0,0,image.getWidth()-1, image.getHeight()-1);
	}


	private void shiftRight() { //on décale la carte à droite
		state += 1;
		decalage = w.getWidth()/8;
		initGo(x,x+decalage);
	}

	private void shiftLeft() { // on décale la carte à gauche
		state -= 1;
		decalage = w.getWidth()/8;
		initGo(x,x-decalage);
	}

	//initGo et go : pour les etats -1, 0 et 1

	public void initGo(int dep, int fin) {
		tmax = 400;
		speed = 2*(fin-dep)/tmax;
		acc = -speed/tmax;
		animGo = true;
		speedPos = (speed >= 0);
		goal = fin;
	}

	public void go(int d) {
		if (speedPos == (speed>0) && x*(speedPos ? 1 : -1) < goal*(speedPos ? 1 : -1)) {
			x+=d*speed;
			speed += d*acc;
		} else {
			x=goal;
			animGo = false;
		}

	}

	//initGetOut et getOut :pour confirmer un choix

	public void initGetOut(int dep, int fin) {
		tmax = 500;
		speed = 2*(fin-dep)/tmax;
		acc = -speed/tmax;
		animGetOut = true;
		speedPos = (speed >= 0);
		goal = fin;
	}

	public void getOut(int d) {
		if (speedPos == (speed>0) && y*(speedPos ? 1 : -1) < goal*(speedPos ? 1 : -1)) {
			y+=d*speed;
			speed += d*acc;
		} else {
			y=goal;
			animGetOut = false;
		}

	}

	public void confirmLeft() {
		state -= 1;
		decalage = w.getHeight()/2;
		initGetOut(y,y+decalage);
	}

	public void confirmRight() {
		state += 1;
		decalage = w.getHeight()/2;
		initGetOut(y,y+decalage);
	}
	
	public void initGetIn(int dep, int fin) {
		tmax = 700;
		speed = 2*(fin-dep)/tmax;
		acc = -speed/tmax;
		animGetIn = true;
		speedPos = (speed >= 0);
		goal = fin;
	}
	
	public void getIn(int d) {
		if (speedPos == (speed>0) && y*(speedPos ? 1 : -1) < goal*(speedPos ? 1 : -1)) {
			y+=d*speed;
			speed += d*acc;
		} else {
			y=goal;
			animGetIn = false;
		}
		
	}
	
	public void setPiocheeTrue() {
		piochee = true;
		initGetIn(y,(int) (312*w.getHeight())/720);
	}
}
