package telekingdom;

import telekingdom.hud.Card;
import telekingdom.hud.Interface;

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

public class World extends AppWorld {
	
	public static final Font Font = FontUtils.loadFont("Kalinga", java.awt.Font.BOLD, 18, true);
	public final static String GAME_FOLDER_NAME="telekingdom";
	public final static String DIRECTORY_MUSICS="musics"+File.separator;

	
	private int width;
	private int height;

	private Interface interf;
	private Player player;
	
	private static Music music;
	
	private ArrayList<Card> deck;
	private int nbCards;
	
	public World (int ID) {
		super (ID);
	}
	
	static {
		try {
			music = new Music(DIRECTORY_MUSICS+"main_theme.ogg");
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
	public void enter(GameContainer container, StateBasedGame game) {
		//Ici mettre tous les chargement d'image, creation de perso/decor et autre truc qui mettent du temps
	}

	@Override
	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au lancement du jeu */
		music.loop (1, .3f);
	}

	@Override
	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		music.pause ();
	}

	@Override
	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		music.resume ();
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
