package trinity;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;
import java.util.Scanner;

public class Game {

	public static String trinitySubgamePath = System.getProperty("user.dir") + "/cartridges/";

	public static boolean running = true;

	public static boolean debug = false;

	public static String currentName = "trinity";

	public static Random random = new Random();

	public static void main(String[] args) {

		Render.init(new Dimension(160*2, 120*2), 1);
		Level.clear();
		Level.levels.add(new Level());
		Level foo = Level.levels.get(0);

		Scanner input = null;

		try {
			input = new Scanner(new File(System.getProperty("user.dir") + "/cartridges/list.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int i = 0;
		
		while (input.hasNext()) {
			String next = input.next();
			Level.images.put(next, Render.loadImage("icon.png", next));
			new CartridgeMenuItem(new Twin(40+(80*i), 55), foo, next);
			i++;
			System.out.println(next);
		}

//		new CartridgeMenuItem(new Twin(10, 20), foo, "Low Battery", "low_battery");
//		new CartridgeMenuItem(new Twin(10, 40), foo, "Evo Sym", "evo_sym");
//		new CartridgeMenuItem(new Twin(10, 60), foo, "Disk Game", "disk_game");
//		new CartridgeMenuItem(new Twin(10, 80), foo, "Drone", "counter_warfare");
//		new CartridgeMenuItem(new Twin(100, 20), foo, "Folding Generator 1", "folding_generator_1");
//		new CartridgeMenuItem(new Twin(100, 40), foo, "Low Battery", "low_battery");
//		new CartridgeMenuItem(new Twin(100, 60), foo, "Low Battery", "low_battery");
//		new CartridgeMenuItem(new Twin(100, 80), foo, "Low Battery", "low_battery");
		foo.updateObjects();
		new MenuSelecter(new Twin(0, 0), foo);

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
