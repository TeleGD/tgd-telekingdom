import javax.swing.JOptionPane;

import org.newdawn.slick.SlickException;

import app.AppContainer;
import app.AppGame;

public final class Main {

	public static final void main (String [] arguments) throws SlickException {
		AppGame appGame = new AppGame ("TeleKingdom");
		AppContainer container = new AppContainer (appGame, 1280, 720, false);
		Object[] options = {"Oui",
        "Non"};
		int n = JOptionPane.showOptionDialog(null,
				"Voulez-vous jouer en plein écran ?",
				"42",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		
		if (n==0) {
			container = new AppContainer (appGame, 1920, 1080, false);
			container.setFullscreen(true);
		} else {
			container.setFullscreen(false);
		}
		container.setTargetFrameRate (60);
		container.setVSync (true);
		container.setShowFPS (false);
		container.start ();
	}
}
