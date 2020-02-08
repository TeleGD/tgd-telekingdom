package games.telekingdom;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

import games.telekingdom.Defeat;
import games.telekingdom.hud.GaugeTemplate;

public class World extends BasicGameState {

	private int ID;
	private int state;

	private Audio music;
	private float musicPos;
	private Player player;

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID () {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
		this.music = AppLoader.loadAudio("/musics/telekingdom/main-theme.ogg");
		this.player = new Player();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		container.getInput().clearKeyPressedRecord();
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		player.update(container, game, delta);
		int death = player.getDeath();
		if (death != 0) {
			GaugeTemplate gaugeTemplate = player.getJauges().get(Math.abs(death) - 1).getGaugeTemplate();
			String title = death < 0 ? gaugeTemplate.getEmptyTitle() : gaugeTemplate.getFullTitle();
			((Defeat) game.getState(4)).setSubtitle(title);
			this.setState(3);
			game.enterState(4, new FadeOutTransition (), new FadeInTransition ());
		}
		//player.addToArgent(-1); //debug
		//player.addToReputation(1); //debug
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		player.render(container, game, context);
	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.music.playAsMusic(1, .3f, true);
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		this.musicPos = this.music.getPosition();
		this.music.stop();
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		this.music.playAsMusic(1, .3f, true);
		this.music.setPosition(this.musicPos);
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		this.music.stop();
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public void reset(GameContainer container, StateBasedGame game) {
		this.player.reset(container, game);
	}

	public void restore(GameContainer container, StateBasedGame game) {
		this.player.restore(container, game);
	}

	public void save(GameContainer container, StateBasedGame game) {
		this.player.save(container, game);
	}

}
