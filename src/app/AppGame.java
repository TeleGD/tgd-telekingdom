package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class AppGame extends StateBasedGame {

	public static final int PAGES_WELCOME = 0;
	public static final int PAGES_PAUSE = 1;
	public static final int TELEKINGDOM_WORLD = 2;
	public static final int PAGES_DEFEAT = 3;

	public static final String [] TITLES = new String [] {
		"Accueil",
		"Pause",
		"TeleKingdom",
		"Fin du regne"
	};

	public AppGame (String name) {
		super (name);
	}

	@Override
	public void initStatesList (GameContainer container) {
		this.addState (new pages.Welcome (AppGame.PAGES_WELCOME));
		this.addState (new pages.Pause (AppGame.PAGES_PAUSE));
		this.addState (new telekingdom.World (AppGame.TELEKINGDOM_WORLD));
		this.addState (new pages.Defeat (AppGame.PAGES_DEFEAT));
	}

}
