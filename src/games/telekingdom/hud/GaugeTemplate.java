package games.telekingdom.hud;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.openal.Audio;

import app.AppLoader;

public class GaugeTemplate {

	static private List <GaugeTemplate> instances;

	static {
		GaugeTemplate.instances = new ArrayList <GaugeTemplate> ();
		GaugeTemplate.load ("/data/telekingdom/gaugeTemplates.json");
		// GaugeTemplate.normalize ();
		// GaugeTemplate.save ("/telekingdom/extensions/fork/gaugeTemplates.json");
		if (GaugeTemplate.instances.size () == 0) {
			new GaugeTemplate ();
		}
	}

	static private void load (String filename) {
		String json = AppLoader.loadData (filename);
		try {
			JSONArray array = new JSONArray (json);
			for (int i = 0, li = array.length (); i < li; i++) {
				GaugeTemplate gaugeTemplate = new GaugeTemplate ();
				try {
					JSONObject object = array.getJSONObject (i);
					try {
						gaugeTemplate.name = object.getString ("name");
					} catch (JSONException error) {}
					try {
						String src = object.getString ("background");
						gaugeTemplate.backgroundImage = AppLoader.loadPicture (src);
						gaugeTemplate.backgroundSrc = src;
					} catch (JSONException error) {}
					try {
						String src = object.getString ("foreground");
						gaugeTemplate.foregroundImage = AppLoader.loadPicture (src);
						gaugeTemplate.foregroundSrc = src;
					} catch (JSONException error) {}
					try {
						JSONObject empty = object.getJSONObject ("empty");
						try {
							gaugeTemplate.emptyTitle = empty.getString ("title");
						} catch (JSONException error) {}
						try {
							gaugeTemplate.emptyDescription = empty.getString ("description");
						} catch (JSONException error) {}
						try {
							String src = empty.getString ("sound");
							gaugeTemplate.emptySound = AppLoader.loadAudio (src);
							gaugeTemplate.emptySrc = src;
						} catch (JSONException error) {}
					} catch (JSONException error) {}
					try {
						JSONObject full = object.getJSONObject ("full");
						try {
							gaugeTemplate.fullTitle = full.getString ("title");
						} catch (JSONException error) {}
						try {
							gaugeTemplate.fullDescription = full.getString ("description");
						} catch (JSONException error) {}
						try {
							String src = full.getString ("sound");
							gaugeTemplate.fullSound = AppLoader.loadAudio (src);
							gaugeTemplate.fullSrc = src;
						} catch (JSONException error) {}
					} catch (JSONException error) {}
				} catch (JSONException error) {}
			}
		} catch (JSONException error) {}
	}

	static private void save (String filename) {
		JSONArray array = new JSONArray ();
		for (GaugeTemplate gaugeTemplate: GaugeTemplate.instances) {
			JSONObject object = new JSONObject ();
			try {
				object.put ("id", array.length ());
			} catch (JSONException error) {}
			try {
				object.put ("name", gaugeTemplate.name);
			} catch (JSONException error) {}
			try {
				object.put ("background", gaugeTemplate.backgroundSrc);
			} catch (JSONException error) {}
			try {
				object.put ("foreground", gaugeTemplate.foregroundSrc);
			} catch (JSONException error) {}
			JSONObject empty = new JSONObject ();
			try {
				empty.put ("title", gaugeTemplate.emptyTitle);
			} catch (JSONException error) {}
			try {
				empty.put ("description", gaugeTemplate.emptyDescription);
			} catch (JSONException error) {}
			try {
				empty.put ("sound", gaugeTemplate.emptySrc);
			} catch (JSONException error) {}
			try {
				object.put ("empty", empty);
			} catch (JSONException error) {}
			JSONObject full = new JSONObject ();
			try {
				full.put ("title", gaugeTemplate.fullTitle);
			} catch (JSONException error) {}
			try {
				full.put ("description", gaugeTemplate.fullDescription);
			} catch (JSONException error) {}
			try {
				full.put ("sound", gaugeTemplate.fullSrc);
			} catch (JSONException error) {}
			try {
				object.put ("full", full);
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

	static public GaugeTemplate getItem (int index) {
		return index >= 0 && index < GaugeTemplate.instances.size () ? GaugeTemplate.instances.get (index) : null;
	}

	static public int getIndex (GaugeTemplate item) {
		return item != null ? GaugeTemplate.instances.indexOf (item) : -1;
	}

	static public int getLength () {
		return GaugeTemplate.instances.size ();
	}

	private String name;
	private Image backgroundImage;
	private String backgroundSrc;
	private Image foregroundImage;
	private String foregroundSrc;
	private String emptyTitle;
	private String emptyDescription;
	private Audio emptySound;
	private String emptySrc;
	private String fullTitle;
	private String fullDescription;
	private Audio fullSound;
	private String fullSrc;

	private GaugeTemplate () {
		GaugeTemplate.instances.add (this);
		this.name = ""; // le nom
		this.backgroundImage = AppLoader.loadPicture (null); // l'image d'arrière-plan
		this.backgroundSrc = null; // la source de l'image d'arrière-plan
		this.foregroundImage = AppLoader.loadPicture (null); // l'image d'avant-plan
		this.foregroundSrc = null; // la source de l'image d'avant-plan
		this.emptyTitle = "Mort de peu"; // le nom de la mort qui survient lorsque la gaugeTemplate est vide
		this.emptyDescription = "Aïe"; // la description de la mort qui survient lorsque la gaugeTemplate est vide
		this.emptySound = AppLoader.loadAudio (null); // le bruitage de la mort qui survient lorsque la gaugeTemplate est vide
		this.fullTitle = "Mort de trop"; // le nom de la mort qui survient lorsque la gaugeTemplate est pleine
		this.fullDescription = "Ouch"; // la description de la mort qui survient lorsque la gaugeTemplate est pleine
		this.fullSound = AppLoader.loadAudio (null); // le bruitage de la mort qui survient lorsque la gaugeTemplate est vide
	}

	public String getName () {
		return this.name;
	}

	public Image getBackgroundImage () {
		return this.backgroundImage;
	}

	public Image getForegroundImage () {
		return this.foregroundImage;
	}

	public String getEmptyTitle () {
		return this.emptyTitle;
	}

	public String getEmptyDescription () {
		return this.emptyDescription;
	}

	public Audio getEmptySound () {
		return this.emptySound;
	}

	public String getFullTitle () {
		return this.fullTitle;
	}

	public String getFullDescription () {
		return this.fullDescription;
	}

	public Audio getFullSound () {
		return this.fullSound;
	}

}
