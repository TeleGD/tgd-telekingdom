package telekingdom.hud;

import java.io.File;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import telekingdom.Player;
import telekingdom.World;

public class Interface {
	public World world;

	private static Image background;

	private List<Jauge> jauges;
	private Player player;

	private Color boxColor;
	private Color textColor;


	static {
		try {
			background = new Image("images"+File.separator+"background.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	public Interface (World world, Player player) {
		this.world = world;
		this.player = player;
		this.jauges = player.getJauges();


		boxColor = new Color(72,56,56);
		textColor = new Color(189,176,130);
	}


	public void update (GameContainer container, StateBasedGame game, int delta) {
		if(!player.isDead()) {
			player.update(container, game, delta);
			player.getActiveCard().update(container, game, delta);

		}
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		Color previousColor = context.getColor(); //on retient l'ancienne couleur

		//on draw le fond
		context.drawImage(background, 0, 0, world.getWidth(), world.getHeight(), 0, 0, background.getWidth()-1, background.getHeight()-1);

		//on draw la box en haut de l'ecran
		context.setColor(boxColor);
		context.fillRect(world.getWidth()/3, 0, world.getWidth()/3, 110/720f*world.getHeight());

		//on draw les jauges et leurs noms dans la box
		context.setColor(textColor);
		for (Jauge j : jauges) {
			j.render(container, game, context);
		}

		//on draw la carte
		player.getActiveCard().render(container, game, context);

		//on remet l'ancienne couleur
		context.setColor(previousColor);
	}
}
