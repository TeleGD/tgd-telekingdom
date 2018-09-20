package telekingdom;

import java.io.File;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.utils.FontUtils;
import telekingdom.hud.Interface;

public class World extends BasicGameState {

	public static final Font FontJauges = FontUtils.loadFont("Kalinga", java.awt.Font.BOLD, 16, true);
	public static final Font Font = FontUtils.loadFont("Kalinga", java.awt.Font.BOLD, 12, true);
	public final static String GAME_FOLDER_NAME="telekingdom";
	public final static String DIRECTORY_MUSICS="musics"+File.separator;

	private static Music music;

	static {
		try {
			music = new Music(DIRECTORY_MUSICS+"main_theme.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private int ID;
	private int state;

	private int width;
	private int height;

	private Interface interf;
	private Player player;

	public World (int ID) {
		this.ID = ID;
		this.state = -1;
	}

	@Override
	public int getID () {
		return this.ID;
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
	public void enter (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		container.getInput ().clearKeyPressedRecord ();
		if (this.state == 0) {
			/* Exécuté une unique fois au lancement du jeu */
			player.init();
			music.loop (1, .3f);
		} else if (this.state == 2) {
			/* Exécuté lors de la reprise du jeu */
			music.resume ();
		}
	}

	@Override
	public void leave (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			/* Exécuté lors de la mise en pause du jeu */
			music.pause ();
		} else if (this.state == 3) {
			/* Exécuté une unique fois à la fin du jeu */
			music.stop ();
		}
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput ();
		if (input.isKeyPressed (Input.KEY_ESCAPE)) {
			this.setState (1);
			game.enterState (1, new FadeOutTransition (), new FadeInTransition ());
		}

		interf.update(container, game, delta);
		//interf.addToArgent(-1); //debug
		//player.addToReputation(1); //debug
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		interf.render(container, game, context);
	}

	public void setState (int state) {
		this.state = state;
	}

	public int getState () {
		return this.state;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Player getPlayer() {
		return player;
	}

}
