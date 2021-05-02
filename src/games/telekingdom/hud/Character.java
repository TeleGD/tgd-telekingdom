package games.telekingdom.hud;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Image;

import app.AppLoader;

public class Character {

	static private List <Character> instances;

	static {
		Character.instances = new ArrayList <Character> ();
		Character.load ("/data/telekingdom/characters.json");
		// Character.normalize ();
		// Character.save ("/telekingdom/extensions/fork/characters.json");
		if (Character.instances.size () == 0) {
			new Character ();
		}
	}

	static private void load (String filename) {
		String json = AppLoader.loadData (filename);
		try {
			JSONArray array = new JSONArray (json);
			for (int i = 0, li = array.length (); i < li; i++) {
				Character character = new Character ();
				try {
					JSONObject object = array.getJSONObject (i);
					try {
						character.name = object.getString ("name");
					} catch (JSONException error) {}
					try {
						String src = object.getString ("image");
						character.image = AppLoader.loadPicture (src);
						character.src = src;
					} catch (JSONException error) {}
				} catch (JSONException error) {}
			}
		} catch (JSONException error) {}
	}

	static private void save (String filename) {
		JSONArray array = new JSONArray ();
		for (Character character: Character.instances) {
			JSONObject object = new JSONObject ();
			try {
				object.put ("id", array.length ());
			} catch (JSONException error) {}
			try {
				object.put ("name", character.name);
			} catch (JSONException error) {}
			try {
				object.put ("image", character.src);
			} catch (JSONException error) {}
			array.put (object);
		}
		String json = "";
		try {
			json = array.toString (2).replaceAll ("  ", "\t") + "\n";
		} catch (JSONException error) {}
		AppLoader.saveData (filename, json);
	}

	static private void normalize () {}

	static public Character getItem (int index) {
		return index >= 0 && index < Character.instances.size () ? Character.instances.get (index) : null;
	}

	static public int getIndex (Character item) {
		return item != null ? Character.instances.indexOf (item) : -1;
	}

	static public int getLength () {
		return Character.instances.size ();
	}

	private String name;
	private Image image;
	private String src;

	private Character () {
		Character.instances.add (this);
		this.name = "Inconnu"; // le nom
		this.image = AppLoader.loadPicture (null); // l'image
		this.src = null; // la source de l'image
	}

	public String getName () {
		return this.name;
	}

	public Image getImage () {
		return this.image;
	}

}
