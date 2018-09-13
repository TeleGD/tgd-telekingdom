package telekingdom.hud;

import java.io.File;
import java.util.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import telekingdom.World;

public class Interface {
	public World world;
	/* Commentaire modifié*/
	private static Image background;

	private List<Jauge> jauges;

	private Card card;

	private Color boxColor;
	private Color textColor;


	static {
		try {
			background = new Image("images"+File.separator+"background.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	public Interface(World w) {
		this.world = w;

		this.jauges = new ArrayList<Jauge>();
		addJauge(new Jauge("Argent", "a", "b", world));
		addJauge(new Jauge("Reputation", "c ", "d", world));

		card = new Card(this,1);

		boxColor = new Color(72,56,56);
		textColor = new Color(189,176,130);
	}

	private void addJauge(Jauge j) {
		jauges.add(j);

		//on place directement les jauges centrees et separees de 25px
		int n = jauges.size();
		for (int i=0; i<n; i++) {
			jauges.get(i).setX(world.getWidth()/2 - jauges.get(i).getWidth()*n/2 - 25/1280f*world.getWidth()*(n-1)/2 + i*(jauges.get(i).getWidth()+25/1280f*world.getWidth()));
		}
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		for (Jauge j : jauges) {
			if (j.isEmptyOrFull()) {
				//World.endGame(j.getEndingMessage());
				//System.out.println(j.getEndingMessage()); //debug
			}
		}

		card.update(container, game, delta);
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		Color previousColor = context.getColor(); //on retient l'ancienne couleur

		//on draw le fond
		context.drawImage(background, 0, 0, world.getWidth(), world.getHeight(), 0, 0, background.getWidth()-1, background.getHeight()-1);

		//on draw la box en haut de l'ecran
		context.setColor(boxColor);
		context.fillRect(world.getWidth()/3, 0, world.getWidth()/3, 110/1280f*world.getWidth());

		//on draw les jauges et leurs noms dans la box
		context.setColor(textColor);
		for (Jauge j : jauges) {
			j.render(container, game, context);
		}

		//on draw la carte
		card.render(container, game, context);

		//on remet l'ancienne couleur
		context.setColor(previousColor);
	}

	public void addToArgent(int v) {
		for (Jauge j : jauges) {
			if(j.getName() == "Argent")  j.addValeur(v);
		}
	}

	public void addToReputation(int v) {
		for (Jauge j : jauges) {
			if(j.getName() == "Reputation")  j.addValeur(v);
		}
	}


}
