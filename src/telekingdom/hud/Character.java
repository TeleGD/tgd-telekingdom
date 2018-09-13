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
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.EmptyImageData;

public class Character {

	static private List <Character> instances = new ArrayList <Character> ();

	static {
		try {
			BufferedReader streamFilter = new BufferedReader (new FileReader ("data" + File.separator + "characters.json"));
			String json = "";
			String line;
			while ((line = streamFilter.readLine ()) != null) {
				json += line + "\n";
			}
			streamFilter.close ();
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
							try {
								character.image = new Image ("images" + File.separator + "characters" + File.separator + object.getString ("image"));
							} catch (SlickException exception) {}
						} catch (JSONException error) {}
					} catch (JSONException error) {}
				}
			} catch (JSONException error) {}
		} catch (IOException error) {}
		if (Character.instances.size () == 0) {
			new Character ();
		}
	}

	static public Character getCharacter (int ID) {
		return Character.instances.get (ID >= 0 && ID < Character.instances.size () ? ID : 0);
	}

	private String name;
	private Image image;

	public Character () {
		Character.instances.add (this);
		this.name = "Inconnu";
		this.image = new Image (new EmptyImageData (0, 0));
	}

	public String getName () {
		return this.name;
	}

	public Image getImage () {
		return this.image;
	}

}
