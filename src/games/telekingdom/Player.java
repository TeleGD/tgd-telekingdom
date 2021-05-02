package games.telekingdom;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.telekingdom.hud.Card;
import games.telekingdom.hud.CardParams;
import games.telekingdom.hud.CardTemplate;
import games.telekingdom.hud.Jauge;
import games.telekingdom.hud.GaugeTemplate;


public class Player {


	private List<Jauge> jauges;
	private List<Card> deck;
	private Card activeCard;	// Carte tirée et affiché

	private Color boxColor;
	private Color textColor;
	private Image background;

	public Player() {
		this.jauges = new ArrayList<Jauge>();
		for (int i = 0, li = GaugeTemplate.getLength(); i < li; i++) {
			Jauge jauge = new Jauge(GaugeTemplate.getItem(i));
			this.jauges.add(jauge);
		}
		this.deck = new ArrayList<Card>();	// Création du deck des cartes
		this.activeCard = null;
	}

	public void init(GameContainer container, StateBasedGame game) {
		for (int i = 0, li = GaugeTemplate.getLength(); i < li; i++) {
			Jauge jauge = this.jauges.get(i);
			jauge.init(container, game);
			//on place directement les jauges centrees et separees de 25px
			jauge.setX((container.getWidth() - jauge.getSize() * li) / 2 + (int) (25 / 1280f * container.getWidth()) * (i - (li - 1) / 2) + jauge.getSize() * i);
		}
		this.deck.clear();
		this.activeCard = new Card(CardTemplate.getItem(0));
		this.activeCard.init(container, game);
		this.boxColor = new Color(72, 56, 56);
		this.textColor = new Color(189, 176, 130);
		this.background = AppLoader.loadPicture("/images/telekingdom/tk_background.png");
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.activeCard.update(container, game, delta);
		if (Math.abs(this.activeCard.getState()) == 2) {
			this.applyEffects(this.activeCard.getEffect());
			this.addNextCards(container, game);
		}
		for (Jauge j : jauges) {
			j.update(container, game, delta);
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		Color previousColor = context.getColor(); //on retient l'ancienne couleur

		//on draw le fond
		context.drawImage(background, 0, 0, container.getWidth(), container.getHeight(), 0, 0, background.getWidth(), background.getHeight());

		//on draw la box en haut de l'ecran
		context.setColor(boxColor);
		//context.fillRect(container.getWidth()/3, 0, container.getWidth()/3, 110/720f*container.getHeight());

		//on draw les jauges et leurs noms dans la box
		context.setColor(textColor);
		for (Jauge j : jauges) {
			j.render(container, game, context);
		}

		//on draw la carte
		this.activeCard.render(container, game, context);

		//on remet l'ancienne couleur
		context.setColor(previousColor);
	}

	public void reset(GameContainer container, StateBasedGame game) {
		this.init(container, game);
	}

	public void restore(GameContainer container, StateBasedGame game) {
		this.init(container, game);
		String load = AppLoader.restoreData("/telekingdom/player.json");
		try {
			JSONObject json = new JSONObject(load);
			this.activeCard = new Card(CardTemplate.getItem(json.getInt("activeCard")));
			this.activeCard.init(container, game);
			for (int i = 0, li = json.getJSONArray("deck").length(); i < li; ++i) {
				try {
					this.deck.add(new Card(CardTemplate.getItem(json.getJSONArray("deck").getInt(i))));
				} catch (JSONException error) {}
			}
			for (int i = 0, li = json.getJSONArray("gauges").length(); i < li; ++i) {
				try {
					this.jauges.get(i).addValeur(-50);
					this.jauges.get(i).addValeur(json.getJSONArray("gauges").getInt(i));
				} catch (JSONException error) {}
			}
		} catch (JSONException error) {}
	}

	public void save(GameContainer container, StateBasedGame game) {
		List<Integer> deckID = new ArrayList<Integer>();
		List<Integer> jaugesValue = new ArrayList<Integer>();
		for (Card card: this.deck) {
			int i = CardTemplate.getIndex(card.getCardTemplate());
			if (i == -1) {
				System.err.println("[SAVE] Carte non trouvée dans la liste des cartes");
			} else {
				deckID.add(i);
			}
		}
		int activeCardID = CardTemplate.getIndex(activeCard.getCardTemplate());
		for (Jauge jauge: this.jauges) {
			jaugesValue.add(jauge.getValeur());
		}
		try {
			JSONObject save = new JSONObject();
			save.put("activeCard", activeCardID);
			JSONArray deckJSON = new JSONArray();
			for (int i: deckID) {
				deckJSON.put(i);
			}
			save.put("deck",deckJSON);
			JSONArray jaugesJSON = new JSONArray();
			for (int i: jaugesValue) {
				jaugesJSON.put(i);
			}
			save.put("gauges", jaugesJSON);
			AppLoader.saveData("/telekingdom/player.json", save.toString());
		} catch (JSONException error) {}
	}

	public void addNextCards(GameContainer container, StateBasedGame game) {
		CardParams[] nextCards = activeCard.getCardTemplate().getNext((activeCard.getState() > 0) ? 1 : 0); // En paramètre : on caste le booleen en entier pour passer du choix -1 ou 1 à 0 ou 1
//		CardParams[] nextCards = new CardParams[0];

		Card cardToAdd;
		int index = 0;

		for (int i = 0 ; i < nextCards.length ; i++) {	// Parcours des différentes cartes à ajouter
			for (int j = 0 ; j < nextCards[i].getQuantity() ; j ++) {	// Création du nombre de carte du même template
				cardToAdd = new Card(nextCards[i].getCardTemplate());

				if (nextCards[i].getZone() >= 0) {	// La carte est placée à partir du début du deck (dans les "zone" premiers % du deck), zone = 0 => carte placée au début du deck (donc sera tirée immédiatement)
					index = (int)(Math.random() *(1 + nextCards[i].getZone() * deck.size()/100));
				} else {	// La carte est placée à partir de la fin du deck (dans les - ("zone"+1) derniers % du deck), zone = -1 => carte placée à la fin du deck
					index = (int) (Math.random() * (nextCards[i].getZone() + 1) + 100 ) * deck.size()/100;
				}

				System.out.println("\n Index d'insertion : " + index + " avec zone = " + nextCards[i].getZone() + " et taille de deck : " + deck.size() + "\n");
				deck.add(index, cardToAdd);
			}
		}
		activeCard = deck.remove(0); //Pioche la carte du haut du deck
		activeCard.init(container, game);
	}

	public void applyEffects(int[] effects) {
		for (int i = 0, li = effects.length; i < li && i < this.jauges.size(); i++) {
			this.jauges.get(i).addValeur(effects[i]);
		}
	}

	public List<Jauge> getJauges() {
		return this.jauges;
	}

	public List<Card> getDeck() {
		return this.deck;
	}

	public Card getActiveCard() {
		return activeCard;
	}

	public int getDeath() {
		for (int i = 0, li = GaugeTemplate.getLength(); i < li; i++) {
			Jauge jauge = this.jauges.get(i);
			if (jauge.isEmpty()) { // Si le roi est mort
				return ~i;
			}
			if (jauge.isFull()) { // Si le roi est mort
				return i + 1;
			}
		}
		return 0;
	}

}
