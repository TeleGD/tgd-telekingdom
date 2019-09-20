package pages;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

import telekingdom.World;

public class Pause extends AppMenu {

	public Pause (int ID) {
		super (ID);
	}

	@Override
	public void init (GameContainer container, StateBasedGame game) {
		super.initSize (container, game, 600, 400);
		super.init (container, game);
		this.setTitle ("Pause");
		this.setSubtitle ("Le temps de prendre un goûter");
		this.setMenu (Arrays.asList (new MenuItem [] {
			new MenuItem ("Retour") {
				public void itemSelected () {
					((World) game.getState (4)).setState (2);
					game.enterState (4, new FadeOutTransition (), new FadeInTransition ());
				}
			},
			new MenuItem ("Sauvegarder partie") {
				public void itemSelected () {
					((World) game.getState(4)).saveGame ();
					((World) game.getState (4)).setState (0);
					((World) game.getState(4)).init (container, game);
					game.enterState (1, new FadeOutTransition (), new FadeInTransition ());
				}
			},
			new MenuItem ("Abandonner partie") {
				public void itemSelected () {
					((World) game.getState (4)).setState (0);
					((World) game.getState(4)).init (container, game);
					game.enterState (1, new FadeOutTransition (), new FadeInTransition ());
				}
			}
		}));
		this.setHint ("HAVE A SNACK");
	}

}
