package telekingdom;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import app.AppLoader;

import telekingdom.hud.Card;
import telekingdom.hud.CardTemplate;
import telekingdom.hud.Jauge;

public class Restore {

	private List<Jauge> jauges;
	private List<Card> deck;
	private Card activeCard;

	public Restore(Player player) {
		this.jauges = player.getJauges();
		this.deck = player.getDeck();
		this.activeCard = player.getActiveCard();

		String load = AppLoader.restoreData("/telekingdom/save.json");
		try {
			JSONObject json = new JSONObject (load);
			this.activeCard = new Card(CardTemplate.getItem(json.getInt("activeCard")));

			for (int i = 0, l=json.getJSONArray("deck").length(); i<l; i++) {
				try {
					deck.add(new Card(CardTemplate.getItem(json.getJSONArray("deck").getInt(i))));
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
