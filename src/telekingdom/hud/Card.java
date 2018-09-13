package telekingdom.hud;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Card {
	//image de la carte
	private Image image;
	
	//dimensions
	private int x;
	private int y;
	private int length;
	
	//etat de la carte : 0 au milieu, 1 à droite et -1 à gauche
	private int state;
	private Input input;
	
	private int id;
	
	//a recuperer dans la base des cartes
	private String texte;
	private List<Integer> effet;
	private List<Integer> pool;
	
	//pour manipulation de fichiers
	private Path cardPath;
	
	public Card(Interface i, int id) {
		this.id = id;
		texte = "carte de base";
		
		try {
			image = new Image("images"+File.separator+"cards"+File.separator+"card"+id+".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		state = 0; //on commence carte au milieu
		
		effet = new ArrayList<Integer>();
		effet.add(-20);
		effet.add(20);
		
		length = 300;
		x = i.world.getWidth()/2 - length/2;
		y = i.world.getHeight()/2;
	}
	
	public void init(GameContainer container, StateBasedGame game) {
		cardPath = Paths.get("data"+File.separator+"cards.csv");
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		input = container.getInput();
		if (input.isKeyPressed(Input.KEY_LEFT) && state != -1) { //si on appuie sur gauche et qu'on est pas à gauche
			shiftLeft();
		}
		if (input.isKeyPressed(Input.KEY_RIGHT) && state != 1) { //si on appuie sur droite et qu'on est pas à droite
			shiftRight();
		}
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
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
