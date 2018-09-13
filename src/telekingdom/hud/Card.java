package telekingdom.hud;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Card {
	//image de la carte
	private Image image;
	
	//dimensions
	private int x;
	private int y;
	private int length;
	
	private int id;
	
	//a recuperer dans la base des cartes
	private String texte;
	private List<Integer> effet;
	private List<Integer> pool;
	
	public Card(Interface i, int id) {
		this.id = id;
		texte = "carte de base";
		
		try {
			image = new Image("images"+File.separator+"cards"+File.separator+"card"+id+".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		effet = new ArrayList<Integer>();
		effet.add(-20);
		effet.add(20);
		
		length = 300;
		x = i.world.getWidth()/2 - length/2;
		y = i.world.getHeight()/2;
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.drawImage(image, x, y, x+length, y+length,0,0,image.getWidth()-1, image.getHeight()-1);
	}

}
