package pages;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

import games.telekingdom.World;

public class Choice extends AppMenu {

	public Choice(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setTitle("TeleKingdom");
		this.setSubtitle("Bienvenue dans votre royaume");
		this.setMenu(Arrays.asList(new MenuItem[] {
			new MenuItem("Partie en cours") {
				public void itemSelected() {
					((World) game.getState(3)).restore(container, game);
					game.enterState(3, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Nouvelle partie") {
				public void itemSelected() {
					((World) game.getState(3)).reset(container, game);
					game.enterState(3, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Retour") {
				public void itemSelected() {
					game.enterState(0, new FadeOutTransition(), new FadeInTransition());
				}
			}
		}));
		this.setHint("BY TELEGAME DESIGN");
	}

}
