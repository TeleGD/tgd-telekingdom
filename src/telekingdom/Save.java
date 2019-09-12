package telekingdom;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

import app.AppLoader;

import telekingdom.hud.Card;
import telekingdom.hud.CardTemplate;
import telekingdom.hud.Jauge;

public class Save {

	private World world;
	private Player player;

	private List<Jauge> jauges;
	private ArrayList<Card> deck;
	private Card activeCard;

	private List<Integer> deckID;
	private List<Integer> jaugesValue;
	private int activeCardID;

	private JSONObject save;
	private JSONArray deckJSON;
	private JSONArray jaugesJSON;

	public Save(World world) {
		this.world = world;
		this.player = this.world.getPlayer();
		this.jauges = this.player.getJauges();
		this.deck = this.player.getDeck();
		this.activeCard = this.player.getActiveCard();
		this.deckID = new ArrayList<Integer>();
		this.jaugesValue = new ArrayList<Integer>();

		for (Card c: deck) {
			int i = (CardTemplate.getIndex(c.getCardTemplate()));
			if (i==-1) {
				System.err.println("[SAVE] Carte non trouvée dans la liste des cartes");
			} else {
				deckID.add(i);
			}
		}
		activeCardID = CardTemplate.getIndex(activeCard.getCardTemplate());
		for (Jauge j: jauges) {
			jaugesValue.add(j.getValeur());
		}

		try {
			save = writeJson();
			AppLoader.saveData("telekingdom/save.json", save.toString(2).replaceAll("  ", "\t") + "\n");
			System.out.println(save.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private JSONObject writeJson() throws JSONException {
		save = new JSONObject();

		deckJSON = new JSONArray();
		jaugesJSON = new JSONArray();

		save.put("activeCard", activeCardID);

		for (int i: deckID) {
			deckJSON.put(i);
		}
		for (int i: jaugesValue) {
			jaugesJSON.put(i);
		}

		save.put("deck",deckJSON);
		save.put("jauges", jaugesJSON);

		return save;
	}

}
