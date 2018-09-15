package telekingdom.hud;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import telekingdom.Player;
import telekingdom.World;

public class Jauge {

	/* Déclaration des variables & constantes de toutes les jauges*/

	/* Position d'affichage sur l'écran*/
	private float x;
	private float y;

	/* Nom */
	private String name;

	/* Référence vers le World principal pour en récupérer les variables.
	 * Il se donne lui même en argument lors de la création des jauges */
	private World world;

	/* Taille de l'image de la jauge */
	private int width;
	private int height;

	/* Valeur de la jauge */
	private int valeur;

	/* Image (format Slick) de la jauge (vide et pleine pour en gérer le remplissage) */
	private Image emptySprite;
	private Image fullSprite;

	/* Messages de défaite selon la valeur de la jauge */
	private String endMessageEmpty;
	private String endMessageFull;

	public Jauge(String name, String endMessageFull, String endMessageEmpty, World w, Player player) {


		this.x = 0;

		this.name = name;
		this.endMessageEmpty = endMessageEmpty;
		this.endMessageFull = endMessageFull;

		this.world = w;

		this.valeur = 50;

		try {
			emptySprite = new Image("images"+File.separator+name+"_empty.png");
			fullSprite = new Image("images"+File.separator+name+".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.setSize();
	}

	private void setSize() {
		/* met la taille et la position en y de l'image en fonction de la taille de l'ecran */
		float scale = 75/1280f;
		width = (int) (scale*world.getWidth());
		height = width;

		y = 10/1280f*world.getWidth();
	}


	public void update (GameContainer container, StateBasedGame game, int delta) {

	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		//on draw l'image vide
		context.drawImage(emptySprite, x, y, x+width, y+height, 0, 0, emptySprite.getWidth()-1, emptySprite.getHeight()-1);
		//puis on draw la partie de l'image pleine qui correspond a la valeur de la jauge
		context.drawImage(fullSprite, x, y+Math.abs((float)valeur-100)/100*height, x+width, y+height, 0, Math.abs((float)valeur-100)/100*(emptySprite.getHeight()-1), emptySprite.getWidth()-1, emptySprite.getHeight()-1);

		//puis on draw le nom de la jauge
		context.drawString(name, x+width/2-World.FontJauges.getWidth(name)/2, y+height+2/1280f*world.getWidth());
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float newX) {
		this.x = newX;
	}

	public String getName() {
		return name;
	}

	public String getEndingMessage() {
		if(valeur == 0) return endMessageEmpty;
		else return endMessageFull;
	}

	public Boolean isEmptyOrFull() {
		return (valeur == 0 || valeur == 100);
	}

	public void addValeur(int v) {
		this.valeur += v;
		if(valeur>100) valeur = 100;
		else if(valeur<0) valeur = 0;
	}
	
	public void init() {
		this.valeur = 50;
	}
}
