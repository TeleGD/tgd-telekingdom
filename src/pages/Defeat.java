package pages;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppGame;
import app.AppMenu;
import app.AppWorld;
import app.elements.MenuItem;
import telekingdom.hud.Card;

public class Defeat extends AppMenu {

	private int previousID;
	private int nextID;
	
	public final static String DIRECTORY_MUSICS="musics"+File.separator;

	private static Music music2;
	
	static {
		try {
			music2 = new Music(DIRECTORY_MUSICS+"defeat.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Defeat (int ID) {
		super (ID);
	}

	@Override
	public void init (GameContainer container, StateBasedGame game) {
		super.initSize (container, game, 600, 400);
		super.init (container, game);
		this.setTitle ("Fin du regne");
		this.setSubtitle ("Le roi est mort, vive le roi !");
		Defeat defeat = (Defeat) game.getState (AppGame.PAGES_DEFEAT);
		this.setMenu (Arrays.asList (new MenuItem [] {
			new MenuItem ("Quitter") {
				public void itemSelected () {
					// ((AppWorld) game.getState (Pause.this.previousID)).stop (container, game);
					defeat.setNextID (AppGame.PAGES_WELCOME);
					music2.stop();
					game.enterState (Defeat.this.nextID, new FadeOutTransition (), new FadeInTransition ());
				}
			}
		}));
		//this.setHint ("HAVE A SNACK");
	}

	public void setPreviousID (int ID) {
		this.previousID = ID;
	}

	public int getPreviousID () {
		return this.previousID;
	}

	public void setNextID (int ID) {
		this.nextID = ID;
	}

	public int getNextID () {
		return this.nextID;
	}

}