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
							for (int j = 0, l = response.length (); j < l && j < 2; j++) {
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
									List <CardTemplate> cardTemplates = new ArrayList <CardTemplate> ();
									for (int k = 0, lk = option.length (); k < lk; k++) {
										try {
											cardTemplates.add (CardTemplate.getCardTemplate (option.getInt (k)));
										} catch (JSONException error) {}
									}
									if (cardTemplates.size () != 0) {
										cardTemplate.next [j] = (CardTemplate []) cardTemplates.toArray ();
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

	private int type;
	private Character character;
	private String request;
	private String [] response;
	private int [] [] effect;
	private CardTemplate [] [] next;

	public CardTemplate () {
		CardTemplate.instances.add (this);
		this.type = 0;
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
		this.next = new CardTemplate [] [] {
			new CardTemplate [] {
				CardTemplate.getCardTemplate (0)
			},
			new CardTemplate [] {
				CardTemplate.getCardTemplate (0)
			}
		};
	}

	public int getType () {
		return this.type;
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

	public CardTemplate getNext (int option, int index) {
		return this.next [option] [index];
	}

}
