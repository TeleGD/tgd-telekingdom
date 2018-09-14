package telekingdom.hud;

public class CardParams {

	private CardTemplate cardTemplate;
	private int zone;
	private int quantity;
	private int type;

	public CardParams (CardTemplate cardTemplate, int type, int zone, int quantity) {
		this.cardTemplate = cardTemplate;
		this.type = type;
		this.zone = zone;
		this.quantity = quantity;
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
