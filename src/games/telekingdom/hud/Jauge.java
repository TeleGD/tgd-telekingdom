package games.telekingdom.hud;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

public class Jauge {

	/* Template de la jauge */
	private GaugeTemplate gaugeTemplate;

	/* Valeur de la jauge */
	private int valeur;
	private double valeurAffichee;
	private double vitesseJauge;

	private Font font;

	/* Taille et position sur l'écran */
	private int size;
	private int x;
	private int y;

	public Jauge(GaugeTemplate gaugeTemplate) {
		this.gaugeTemplate = gaugeTemplate;
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.vitesseJauge = 0.01;
		this.valeur = 50;
		this.valeurAffichee = valeur;
		// this.font = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 16);
		this.font = AppLoader.loadFont("/fonts/telekingdom/special-elite.ttf", AppFont.BOLD, 16);
		this.size = (int) (75 / 1280f * container.getWidth());
		this.x = 0;
		this.y = (int) (10 / 1280f * container.getWidth());
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (valeurAffichee + vitesseJauge * delta < valeur) {
			valeurAffichee += vitesseJauge * delta;
		} else if (valeurAffichee - vitesseJauge * delta > valeur) {
			valeurAffichee -= vitesseJauge * delta;
		} else {
			valeurAffichee = valeur;
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		//on draw l'image vide
		Image backgroundImage = this.gaugeTemplate.getBackgroundImage();
		context.drawImage(backgroundImage, x, y, x + size, y + size, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());
		//puis on draw la partie de l'image pleine qui correspond a la valeur de la jauge
		Image foregroundImage = this.gaugeTemplate.getForegroundImage();
		context.drawImage(foregroundImage, x, y + (int) Math.abs(valeurAffichee - 100) * size / 100, x + size, y + size, 0, (int) Math.abs(valeurAffichee - 100) * foregroundImage.getHeight() / 100, foregroundImage.getWidth(), foregroundImage.getHeight());
		//puis on draw le nom de la jauge
		String name = this.gaugeTemplate.getName();
		context.setFont(this.font);
		context.drawString(name, x + (size - this.font.getWidth(name)) / 2, y + size + (int) (2 / 1280f * container.getWidth()));
	}

	public int getSize() {
		return this.size;
	}

	public void setX(int x) {
		this.x = x;
	}

	public String getEndingMessage() {
		if (valeur == 0) {
			this.gaugeTemplate.getEmptySound().playAsSoundEffect(1, .6f, false);
			return this.gaugeTemplate.getEmptyDescription();
		} else {
			this.gaugeTemplate.getFullSound().playAsSoundEffect(1, .6f, false);
			return this.gaugeTemplate.getFullDescription();
		}
	}

	public Boolean isEmpty() {
		return valeur <= 0;
	}

	public Boolean isFull() {
		return valeur >= 100;
	}

	public void addValeur(int v) {
		this.valeur += v;
		if(valeur>100) valeur = 100;
		else if(valeur<0) valeur = 0;
	}

	public int getValeur() {
		return valeur;
	}

	public GaugeTemplate getGaugeTemplate() {
		return gaugeTemplate;
	}

}
