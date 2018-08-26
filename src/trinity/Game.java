package trinity;


import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Game {
	
	public static String trinitySubgamePath = System.getProperty("user.dir")+"/..";

	public static boolean running = true;
	
	public static boolean debug = false;

	public static String currentName = "dummy";

	public static void main(String[] args) {
		Render.init(new Dimension(160, 120), 1);
		Level.setup();
		Level.levels.put(null, new Level());
		Level.currentLevel = Level.levels.get(null);
		Level.currentLevel.entities.add(new Entity(new Point2D.Float(20, 20)));
		Level.currentLevel.entities.add(new MenuItem(new Point2D.Float(20, 20), "test"));
		try {
			getThing("","").setup();
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public static void quit() {
		running = false;

	}
	
	public static Level getThing(String classname, String path)
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		File file = new File("/Users/josh/eclipse-workspace/low_battery/bin");

		// Convert File to a URL
		URL url = file.toURI().toURL(); // file:/c:/myclasses/
		URL[] urls = new URL[] { url };

		// Create a new class loader with the directory
		URLClassLoader cl = new URLClassLoader(urls);
		// Load in the class; MyClass.class should be located in
		// the directory file:/c:/myclasses/com/mycompany
		Class cls = cl.loadClass("Cartridge");

		cls.getConstructors();
//		try {
//			Field foo = Level.class.getDeclaredField("path_to_gfx");
//			foo.setAccessible(true);
//			foo.set(null, file.getAbsolutePath()+"/..");
//			foo.setAccessible(false);
//		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
		try {
			cl.close ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Level) cls.newInstance();
	}

}
