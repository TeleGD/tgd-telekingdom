package telekingdom;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppGame;
import app.AppInput;
import app.AppWorld;
import app.utils.FontUtils;
import pages.Defeat;
import telekingdom.hud.Card;
import telekingdom.hud.Interface;

public class World extends AppWorld {

	public static final Font Font = FontUtils.loadFont("Kalinga", java.awt.Font.BOLD, 18, true);
	public final static String GAME_FOLDER_NAME="telekingdom";
	public final static String DIRECTORY_MUSICS="musics"+File.separator;


	private int width;
	private int height;

	private Interface interf;
	private Player player;

	private static Music music1;
	private static Music music2;
	
	public static int state = 1;

	private ArrayList<Card> deck;
	private int nbCards;

	public World (int ID) {
		super (ID);
	}

	static {
		try {
			music1 = new Music(DIRECTORY_MUSICS+"main_theme.ogg");
			music2 = new Music(DIRECTORY_MUSICS+"defeat.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
		this.width = container.getWidth ();
		this.height = container.getHeight ();

		this.player = new Player(this);
		this.interf = new Interface(this,player);

	}

	@Override
	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au lancement du jeu */
		music1.loop (1, .3f);
	}

	@Override
	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		music1.pause ();
	}

	@Override
	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		music1.resume ();
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		super.update (container, game, delta);

		interf.update(container, game, delta);
		//interf.addToArgent(-1); //debug
		//interf.addToReputation(1); //debug

		AppInput appInput = (AppInput) container.getInput ();
		AppGame appGame = (AppGame) game;
		if (state == 0) { // Si le roi est mort
			//AppInput appInput = (AppInput) container.getInput ();
			//AppGame appGame = (AppGame) game;
			//if (appInput.isKeyPressed (AppInput.KEY_ESCAPE)) {
			music2.loop(1, (float) 0.3);
			appGame.enterState (AppGame.PAGES_DEFEAT, new FadeOutTransition (), new FadeInTransition ());
			//}
		}

	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		interf.render(container, game, context);
		
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
