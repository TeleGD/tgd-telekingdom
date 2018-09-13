import javax.swing.JOptionPane;

import org.newdawn.slick.SlickException;

import app.AppContainer;
import app.AppGame;

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
		AppGame appGame = new AppGame ("TeleKingdom");
		AppContainer container = n == 0 ? new AppContainer (appGame, 1920, 1080, true) : new AppContainer (appGame, 1280, 720, false);
		container.setTargetFrameRate (60);
		container.setVSync (true);
		container.setShowFPS (false);
		container.start ();
	}
}
