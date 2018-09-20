package telekingdom.hud;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import telekingdom.World;

public class Request {

	private static float WINDOW_WIDTH = 1280f;
	private static float WINDOW_HEIGHT = 720f;
	private static float BOX_X = 498f;
	private static float BOX_Y = 160f;
	private static float BOX_WIDTH = 284f;
	private static float BOX_HEIGHT = 168f;
	private static float LINE_HEIGHT = .8f;

	/* Position centrale de la boîte de texte sur l'écran */
	private float x;
	private float y;

	/* Tableaux contenant les différentes lignes de texte à afficher et leur taille respective */
	private String [] lines;
	private float [] lineWidths;
	private float lineHeight;

	public Request (String description, World world) {
		/* Décomposition de la chaîne de caractères `description` pour l'afficher sur plusieurs lignes */

		float horizontalZoom = world.getWidth () / Request.WINDOW_WIDTH;
		float verticalZoom = world.getHeight () / Request.WINDOW_HEIGHT;
		float width = Request.BOX_WIDTH * horizontalZoom;
		float height = Request.BOX_HEIGHT * verticalZoom;
		x = Request.BOX_X * horizontalZoom + width / 2f;
		y = Request.BOX_Y * verticalZoom + height / 2f;

		description = description.replaceAll ("^\\s+|\\s+$", "");

		if (description.isEmpty ()) {
			lines = new String [0];
			lineWidths = new float [0];
		} else {
			String [] words = description.split ("\\s+");
			int length = 1;
			for (int i = 0, j = 1, l = words.length; j < l; j++) {
				String word = words [i] + " " + words [j];
				if (World.Font.getWidth (word) < width) {
					words [i] = word;
					words [j] = null;
				} else {
					i = j;
					length++;
				}
			}
			lines = new String [length];
			lineWidths = new float [length];
			for (int i = 0, j = 0, l = words.length; j < l; j++) {
				if (words [j] != null) {
					lineWidths [i] = World.Font.getWidth (lines [i++] = words [j]);
				}
			}
		}
		lineHeight = World.Font.getHeight (description) * Request.LINE_HEIGHT;
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor (Color.black);
		context.setFont (World.Font);
		for (int i = 0, l = lines.length; i < l; i++) {
			float x = (float) this.x - this.lineWidths [i] / 2f;
			float y = (float) this.y - this.lineHeight * (((float) l - 1f + 1f / Request.LINE_HEIGHT) / 2f - (float) i);
			context.drawString (this.lines [i], (int) x, (int) y);
		}
	}

}
