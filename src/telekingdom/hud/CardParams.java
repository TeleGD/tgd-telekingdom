package telekingdom.hud;

public class CardParams {

	private CardTemplate cardTemplate;
	private int zone;
	private int quantity;
	private int type;

	public CardParams (CardTemplate cardTemplate, int type, int zone, int quantity) {
		this.cardTemplate = cardTemplate; // le modèle de carte
		this.type = type; // le type de carte
		this.zone = zone; // le pourcentage donnant la taille de la zone de la pioche où insérer les cartes
		this.quantity = quantity; // le nombre de cartes à insérer
	}

	public CardTemplate getCardTemplate () {
		return this.cardTemplate;
	}

	public int getType () {
		return this.type;
	}

	public int getZone () {
		return this.zone;
	}

	public int getQuantity () {
		return this.quantity;
	}

}
