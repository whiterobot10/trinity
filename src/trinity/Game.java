package trinity;


import java.awt.Dimension;
import java.awt.geom.Point2D;

import trinity.Level;
import trinity.Render;

public class Game {
	
	public static String trinitySubgamePath = System.getProperty("user.dir")+"/..";

	public static boolean running = true;
	
	public static boolean debug = false;

	public static String currentName = "dummy";

	public static void main(String[] args) {
		Render.init(new Dimension(160, 120), 1);
		Level.setup();
		Level.currentLevel = new Level();
		Level.currentLevel.entities.add(new Entity(new Point2D.Float(20, 20)));
		Level.currentLevel.entities.add(new menu_item(new Point2D.Float(20, 20), "test"));

	}

	public static void quit() {
		running = false;

	}

}
