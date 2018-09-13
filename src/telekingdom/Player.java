package telekingdom;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.AppInput;

import telekingdom.hud.Jauge;

public class Player{

	private List<Jauge> jauges;

	public Player(World world) {

		jauges = new ArrayList<Jauge>();	// L'ArrayList des jauges, sera passé à l'interface pour l'affichage

		/*Création et ajout des différentes jauges */
		jauges.add(new Jauge("Argent", "a", "b", world,this));
		jauges.add(new Jauge("Reputation", "c ", "d", world,this));

		//on place directement les jauges centrees et separees de 25px
		int n = jauges.size();
		for (int i=0; i<n; i++) {
			jauges.get(i).setX(world.getWidth()/2 - jauges.get(i).getWidth()*n/2 - 25/1280f*world.getWidth()*(n-1)/2 + i*(jauges.get(i).getWidth()+25/1280f*world.getWidth()));
		}
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
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

	public List<Jauge> getJauges() {
		return jauges;
	}

}
