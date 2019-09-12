package telekingdom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import app.AppLoader;

import telekingdom.hud.Card;
import telekingdom.hud.CardTemplate;
import telekingdom.hud.Jauge;

public class Load {

	private World world;
	private Player player;

	private List<Jauge> jauges;
	private ArrayList<Card> deck;
	private Card activeCard;

	public Load(World world) {

		this.world = world;
		this.player = this.world.getPlayer();

		this.player.init();

		this.jauges = this.player.getJauges();
		this.deck = this.player.getDeck();
		this.activeCard = this.player.getActiveCard();

		String load = AppLoader.loadData ("telekingdom/save.json");
		try {
			JSONObject json = new JSONObject (load);
			this.activeCard = new Card(this.world,CardTemplate.getItem(json.getInt("activeCard")));

			for (int i = 0, l=json.getJSONArray("deck").length(); i<l; i++) {
				try {
					deck.add(new Card(this.world,CardTemplate.getItem(json.getJSONArray("deck").getInt(i))));
				} catch (JSONException e) {}
			}

			for (int i = 0, l=json.getJSONArray("jauges").length(); i<l; i++) {
				try {
					jauges.get(i).addValeur(-50);
					jauges.get(i).addValeur(json.getJSONArray("jauges").getInt(i));
				} catch (JSONException e) {}
			}
		} catch (JSONException e) {}

		player.setActiveCard(activeCard);
		player.setDeck(deck);
		player.setJauges(jauges);
	}
}
