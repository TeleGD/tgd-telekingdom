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
	//template de la carte
	private CardTemplate cardTemplate;

	//dimensions
	private int x;
	private int y;
	private int length;

	//etat de la carte : 0 au milieu, 1 à droite et -1 à gauche
	private int state;

	//a recuperer dans la base des cartes
	private List<Integer> effet;
	private List<Integer> pool;


	public Card (World world, CardTemplate cardTemplate) {
		this.cardTemplate = cardTemplate;

		state = 0; //on commence carte au milieu

		effet = new ArrayList<Integer>();
		effet.add(-20);
		effet.add(20);

		length = 300;
		x = world.getWidth()/2 - length/2;
		y = world.getHeight()/2;
		System.out.println (this.cardTemplate.getType ());
		System.out.println (this.cardTemplate.getCharacter ().getName ());
		System.out.println (this.cardTemplate.getRequest ());
		System.out.println (this.cardTemplate.getResponse (0));
		System.out.println (this.cardTemplate.getResponse (1));
		System.out.println (this.cardTemplate.getEffect (0, 0));
		System.out.println (this.cardTemplate.getEffect (0, 1));
		System.out.println (this.cardTemplate.getEffect (1, 0));
		System.out.println (this.cardTemplate.getEffect (1, 1));
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_LEFT) && state != -1) { //si on appuie sur gauche et qu'on est pas à gauche
			shiftLeft();
		}
		if (input.isKeyPressed(Input.KEY_RIGHT) && state != 1) { //si on appuie sur droite et qu'on est pas à droite
			shiftRight();
		}
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		Image image = this.cardTemplate.getCharacter ().getImage ();
		context.drawImage(image, x, y, x+length, y+length,0,0,image.getWidth()-1, image.getHeight()-1);
	}



	private void shiftRight() { //on décale la carte à droite
		state += 1;
		x += 100;
	}

	private void shiftLeft() { // on décale la carte à gauche
		state -= 1;
		x-=100;
	}

}
