package trinity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import trinity.Render;

public class Level {

	public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	public static void setup() {
		images.put("pointer", Render.loadImage("trinity", "pointer.png"));
	}

	public static Level currentLevel = null;

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();

	public static void update() {
		if (Level.currentLevel != null) {
			for (Entity e : currentLevel.entities) {
				e.update();
			}
		}

	}

	public static void draw(Graphics g) {
		if (currentLevel != null) {
			for (int i = 0; i < Render.canvasLayers; i++) {
				for (Entity e : currentLevel.entities) {
					e.draw(g, i);
				}
				for (Wall e : currentLevel.walls) {
					e.draw(g, i);
				}
			}
		}
	}

}
