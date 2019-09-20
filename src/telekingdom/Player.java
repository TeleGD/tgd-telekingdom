package telekingdom;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import telekingdom.hud.Card;
import telekingdom.hud.CardParams;
import telekingdom.hud.CardTemplate;
import telekingdom.hud.Jauge;
import telekingdom.hud.GaugeTemplate;

public class Player {

	private static Image background;

	static {
		Player.background = AppLoader.loadPicture ("/images/tk_background.png");
	}

	private List<Jauge> jauges;
	private List<Card> deck;
	private Card activeCard;	// Carte tirée et affiché

	private Color boxColor;
	private Color textColor;

	public Player () {
		this.jauges = new ArrayList<Jauge>();
		for (int i = 0, li = GaugeTemplate.getLength(); i < li; i++) {
			Jauge jauge = new Jauge(GaugeTemplate.getItem(i));
			this.jauges.add(jauge);
		}
		this.deck = new ArrayList<Card>();	// Création du deck des cartes
		this.activeCard = new Card (CardTemplate.getItem (0));
		boxColor = new Color(72, 56, 56);
		textColor = new Color(189, 176, 130);
	}

	public void init(GameContainer container, StateBasedGame game) {
		for (int i = 0, li = GaugeTemplate.getLength(); i < li; i++) {
			Jauge jauge = this.jauges.get(i);
			jauge.init(container, game);
			//on place directement les jauges centrees et separees de 25px
			jauge.setX((container.getWidth() - jauge.getSize() * li) / 2 + (int) (25 / 1280f * container.getWidth()) * (i - (li - 1) / 2) + jauge.getSize() * i);
		}
		this.activeCard.init(container, game);
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		this.activeCard.update(container, game, delta);
		if (Math.abs(this.activeCard.getState()) == 2) {
			this.applyEffects(this.activeCard.getEffect());
			this.addNextCards(container, game);
		}
		for (Jauge j : jauges) {
			j.update(container, game, delta);
		}
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
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

	public void setJauges(List<Jauge> jauges) {
		this.jauges = jauges;
	}

	public List<Jauge> getJauges() {
		return this.jauges;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}

	public List<Card> getDeck() {
		return this.deck;
	}

	public void setActiveCard(Card activeCard) {
		this.activeCard = activeCard;
	}

	public Card getActiveCard() {
		return activeCard;
	}

	public int getDeath() {
		for (int i = 0, li = GaugeTemplate.getLength(); i < li; i++) {
			Jauge jauge = this.jauges.get(i);
			if (jauge.isEmpty()) { // Si le roi est mort
				return -(i + 1);
			}
			if (jauge.isFull()) { // Si le roi est mort
				return i + 1;
			}
		}
		return 0;
	}

}
