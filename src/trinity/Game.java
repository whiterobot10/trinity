package trinity;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
	
	public static List<Key> keys = Collections.synchronizedList(new ArrayList<Key>());
	static Point mouse = new Point(0, 0);

	public static String trinitySubgamePath = System.getProperty("user.dir") + "/..";

	public static boolean running = true;

	public static boolean debug = true;

	public static String currentName = "dummy";
	
	public static Random random = new Random();

	public static void main(String[] args) {
		Render.init(new Dimension(160, 120), 1);
		Level.clear();
		Level.levels.put(null, new Level());
		Level.currentLevel = Level.levels.get(null);
		
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(10, 20), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(10, 40), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(10, 60), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(10, 80), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(100, 20), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(100, 40), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(100, 60), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new CartrageMenuItem(new Point2D.Float(100, 80), "Low Battery", "low_battery"));
		Level.currentLevel.entities.add(new MenuSelecter(new Point2D.Float(0, 0)));
	
		

	}

	public static void quit() {
		running = false;

	}

	public static void getThing(String classname, String path)
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		File file = new File(path);
		if(file.exists()) {
			System.out.println(path+" is valid.");
		}

		// Convert File to a URL
		URL url = file.toURI().toURL(); // file:/c:/myclasses/
		System.out.println(url);
		URL[] urls = new URL[] { url };

		// Create a new class loader with the directory
		URLClassLoader cl = new URLClassLoader(urls);
		// Load in the class; MyClass.class should be located in
		// the directory file:/c:/myclasses/com/mycompany
		
		System.out.println(classname);
		
		
		

		
		Class cls = cl.loadClass(classname);
		
		
		
		

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
		//return
		cls.newInstance();
	}

}
