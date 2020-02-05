package games.telekingdom;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.AppLoader;

import games.telekingdom.hud.Card;
import games.telekingdom.hud.CardTemplate;
import games.telekingdom.hud.Jauge;

public class Save {

	private List<Jauge> jauges;
	private List<Card> deck;
	private Card activeCard;

	public Save(Player player) {
		this.jauges = player.getJauges();
		this.deck = player.getDeck();
		this.activeCard = player.getActiveCard();
		List<Integer> deckID = new ArrayList<Integer>();
		List<Integer> jaugesValue = new ArrayList<Integer>();

		for (Card c: deck) {
			int i = (CardTemplate.getIndex(c.getCardTemplate()));
			if (i==-1) {
				System.err.println("[SAVE] Carte non trouvée dans la liste des cartes");
			} else {
				deckID.add(i);
			}
		}
		int activeCardID = CardTemplate.getIndex(activeCard.getCardTemplate());
		for (Jauge j: jauges) {
			jaugesValue.add(j.getValeur());
		}

		try {
			JSONObject save = new JSONObject();

			JSONArray deckJSON = new JSONArray();
			JSONArray jaugesJSON = new JSONArray();

			save.put("activeCard", activeCardID);

			for (int i: deckID) {
				deckJSON.put(i);
			}
			for (int i: jaugesValue) {
				jaugesJSON.put(i);
			}

			save.put("deck",deckJSON);
			save.put("jauges", jaugesJSON);
			AppLoader.saveData("/telekingdom/save.json", save.toString(2).replaceAll("^  ", "\t") + "\n");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
