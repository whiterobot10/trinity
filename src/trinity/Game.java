package trinity;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;

public class Game {

	public static String trinitySubgamePath = System.getProperty("user.dir") + "/cartridges/";

	public static boolean running = true;

	public static boolean debug = false;

	public static String currentName = "trinity";

	public static Random random = new Random();

	public static void main(String[] args) {
		
		Render.init(new Dimension(160, 120), 1);
		Level.clear();
		Level.levels.add(new Level());
		Level.currentLevel = Level.levels.get(0);

		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(10, 20), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(10, 40), "Evo Sym", "evo_sym"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(10, 60), "Disk Game", "disk_game"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(10, 80), "Drone", "counter_warfare"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(100, 20), "Folding Generator 1", "folding_generator_1"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(100, 40), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(100, 60), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartridgeMenuItem(new Twin(100, 80), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new MenuSelecter(new Twin(0, 0)));

	}

	public static void quit() {
		running = false;

	}

	public static void getThing(String classname, String path)
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		File file = new File(path);
		if (file.exists()) {
			System.out.println(path + " exists.");
		}

		// Convert File to a URL
		URL url = file.toURI().toURL(); // file:/c:/myclasses/
		URL[] urls = new URL[] { url };

		// Create a new class loader with the directory
		URLClassLoader cl = new URLClassLoader(urls);
		// Load in the class; MyClass.class should be located in
		// the directory file:/c:/myclasses/com/mycompany

		Class<?> cls = cl.loadClass(classname);
	
		

		cls.getConstructors();
//		try {
//			Field foo = Level.class.getDeclaredField("path_to_gfx");
//			foo.setAccessible(true);
//			foo.set(null, file.getAbsolutePath()+"/..");
//			foo.setAccessible(false);
//		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
//		try {
//			cl.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// return
		
		cls.newInstance();
		
		try {
			cl.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
