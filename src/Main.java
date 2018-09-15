import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public final class Main {

	public static final void main (String [] arguments) throws SlickException {
		Object [] options = {"Oui", "Non"};
		int n = JOptionPane.showOptionDialog (
			null,
			"Voulez-vous jouer en plein écran ?",
			"TeleKingdom",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null, // do not use a custom Icon
			options, // the titles of buttons
			options [0] // default button title
		);
		StateBasedGame game = new StateBasedGame ("TeleKingdom") {

			@Override
			public void initStatesList (GameContainer container) {
				this.addState (new pages.Welcome (0));
				this.addState (new pages.Pause (1));
				this.addState (new pages.Defeat (2));
				this.addState (new telekingdom.World (3));
			}

		};
		AppGameContainer container = n == 0 ? new AppGameContainer (game, 1920, 1080, true) : new AppGameContainer (game, 1280, 720, false);
		container.setTargetFrameRate (60);
		container.setVSync (true);
		container.setShowFPS (false);
		container.start ();
	}
}
