package telekingdom.hud;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardTemplate {

	static private List <CardTemplate> instances = new ArrayList <CardTemplate> ();

	static {
		try {
			BufferedReader streamFilter = new BufferedReader (new FileReader ("data" + File.separator + "cardTemplates.json"));
			String json = "";
			String line;
			while ((line = streamFilter.readLine ()) != null) {
				json += line + "\n";
			}
			streamFilter.close ();
			try {
				JSONArray array = new JSONArray (json);
				for (int i = 0, li = array.length (); i < li; i++) {
					CardTemplate cardTemplate = new CardTemplate ();
					try {
						JSONObject object = array.getJSONObject (i);
						try {
							cardTemplate.character = Character.getCharacter (object.getInt ("character"));
						} catch (JSONException error) {}
						try {
							cardTemplate.request = object.getString ("request");
						} catch (JSONException error) {}
						try {
							JSONArray response = object.getJSONArray ("response");
							for (int j = 0, lj = response.length (); j < lj && j < 2; j++) {
								try {
									cardTemplate.response [j] = response.getString (j);
								} catch (JSONException error) {}
							}
						} catch (JSONException error) {}
						try {
							JSONArray effect = object.getJSONArray ("effect");
							for (int j = 0, lj = effect.length (); j < lj && j < 2; j++) {
								try {
									JSONArray option = effect.getJSONArray (j);
									for (int k = 0, lk = option.length (); k < lk && k < 2; k++) {
										try {
											cardTemplate.effect [j] [k] = option.getInt (k);
										} catch (JSONException error) {}
									}
								} catch (JSONException error) {}
							}
						} catch (JSONException error) {}
					} catch (JSONException error) {}
				}
				for (int i = 0, li = array.length (); i < li; i++) {
					CardTemplate cardTemplate = CardTemplate.getCardTemplate (i);
					try {
						JSONObject object = array.getJSONObject (i);
						try {
							JSONArray next = object.getJSONArray ("next");
							for (int j = 0, lj = next.length (); j < lj && j < 2; j++) {
								try {
									JSONArray option = next.getJSONArray (j);
									List <CardParams> cardParams = new ArrayList <CardParams> ();
									for (int k = 0, lk = option.length (); k < lk; k++) {
										CardTemplate nextCardTemplate = CardTemplate.getCardTemplate (0);
										int type = 0;
										int zone = 0;
										int quantity = 1;
										try {
											JSONArray params = option.getJSONArray (k);
											try {
												nextCardTemplate = CardTemplate.getCardTemplate (params.getInt (0));
											} catch (JSONException error) {}
											try {
												type = params.getInt (1);
											} catch (JSONException error) {}
											try {
												zone = params.getInt (2);
											} catch (JSONException error) {}
											try {
												quantity = params.getInt (3);
											} catch (JSONException error) {}
										} catch (JSONException error) {}
										cardParams.add (new CardParams (nextCardTemplate, type, zone, quantity));
									}
									if (cardParams.size () != 0) {
										cardTemplate.next [j] = (CardParams []) cardParams.toArray ();
									}
								} catch (JSONException error) {}
							}
						} catch (JSONException error) {}
					} catch (JSONException error) {}
				}
			} catch (JSONException error) {}
		} catch (IOException error) {}
		if (CardTemplate.instances.size () == 0) {
			new CardTemplate ();
		}
	}

	static public CardTemplate getCardTemplate (int ID) {
		return CardTemplate.instances.get (ID >= 0 && ID < CardTemplate.instances.size () ? ID : 0);
	}

	private Character character;
	private String request;
	private String [] response;
	private int [] [] effect;
	private CardParams [] [] next;

	public CardTemplate () {
		CardTemplate.instances.add (this);
		this.character = Character.getCharacter (0);
		this.request = "Hmm...";
		this.response = new String [] {
			"Non",
			"Oui"
		};
		this.effect = new int [] [] {
			new int [] {
				0,
				0
			},
			new int [] {
				0,
				0
			}
		};
		this.next = new CardParams [] [] {
			new CardParams [] {
				new CardParams (CardTemplate.getCardTemplate (0), 0, 0, 1)
			},
			new CardParams [] {
				new CardParams (CardTemplate.getCardTemplate (0), 0, 0, 1)
			}
		};
	}

	public Character getCharacter () {
		return this.character;
	}

	public String getRequest () {
		return this.request;
	}

	public String getResponse (int option) {
		return this.response [option];
	}

	public int getEffect (int option, int gauge) {
		return this.effect [option] [gauge];
	}

	public CardParams getNext (int option, int index) {
		return this.next [option] [index];
	}

}
