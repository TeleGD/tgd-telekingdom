package games.telekingdom;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;
import app.AppMenu;
import app.elements.MenuItem;

public class Defeat extends AppMenu {

	private Audio music;

	public Defeat(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setTitle("Fin du regne");
		this.setSubtitle("Le roi est mort, vive le roi !");
		this.setMenu(Arrays.asList(new MenuItem[] {
			new MenuItem("Quitter") {
				public void itemSelected() {
					game.enterState(1, new FadeOutTransition(), new FadeInTransition());
				}
			}
		}));
		// this.setHint("HAVE A SNACK");
		this.music = AppLoader.loadAudio("/musics/telekingdom/defeat.ogg");
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		music.playAsMusic(1, .3f, true);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		music.stop();
	}

}
