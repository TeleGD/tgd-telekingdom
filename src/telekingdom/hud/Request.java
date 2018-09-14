package telekingdom.hud;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import telekingdom.World;

public class Request {
	
	/* Tableaux contenant les différentes lignes à afficher */
	private String[] lines;
	
	/* Position d'affichage sur l'écran*/
	private float x;
	private float[] y;
	
	/* Taille du texte */
	private int[] width;
	private int height = World.Font.getHeight("abcdefghijklmnopqrstuvwxyz!§:/;.,?><µ*ù%£$ø€^¨=+})]°@àç\\_`è|-[({'#~é²0123456789");

	/* Longueur maximale des lignes */
	private int widthLimit;
	
	/* Hauteur maxiamel de la box */
	private int heightLimit = (int) 170/720;
	
	/* Hauteur totale */
	private int totalHeight;
	
	public Request(String description, World w) {
		/* Décomposition de la chaine de caractères en un tableau 
		 * dont chaque case contient une ligne.
		 */
		lines = description.split("\n");
		int length = lines.length;
		
		/* Calcul de la hauteur totale
		 */
		totalHeight=(int) (1.25*height*length);
		
		x = (int) w.getWidth()/2;
		widthLimit = (int) (280*w.getWidth()/1280);
		
		width = new int [length];
		
		/* Vérification de la hauteur totale
		 */
		if (totalHeight > heightLimit) {
			System.out.println("erreur : description trop longue ou trop de lignes ("+description+")");
		}
		
		
		int topVoid = (heightLimit - totalHeight)/2;
		
		for (int i=0 ; i<lines.length ; i++) {
			/* Calcul de la taille de chaque ligne
			 */
			width[i] = World.Font.getWidth(lines[i]);
			
			/* Vérification de la longueur de la ligne
			 * pour qu'elle ne dépasse pas du cadre
			 */
			if (width[i] > widthLimit) {
				System.out.println("erreur : ligne trop longue ("+description+")");
			}
			
			
			y[i] = (121/720f + topVoid + height*1.25f*i);
			
		}
		
		
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		for (int i=0 ; i<lines.length ; i++) {
			context.drawString(lines[i], x-width[i]/2, y[i]);
		}

	}

}
