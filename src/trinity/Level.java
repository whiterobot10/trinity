package trinity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import trinity.Render;

public class Level {

	public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public static HashMap<String, Level> levels = new HashMap<String, Level>();

	public static void setup() {
		images.put("pointer", Render.loadImage("trinity", "pointer.png"));
	}

	public static Level currentLevel = null;

	public List<Entity> entities = Collections.synchronizedList(new ArrayList<Entity>());
	public List<Wall> walls = Collections.synchronizedList(new ArrayList<Wall>());

	public static void clear() {
		levels.clear();
		images.clear();
	}
	
	public static void update() {
		if (Level.currentLevel != null) {
			synchronized (Level.currentLevel.entities) {
				for (Entity e : currentLevel.entities) {
					e.update();
				}
			}
			KeyInput.reset();
		}

	}

	public static void draw(Graphics g) {
		if (currentLevel != null) {
			synchronized (Level.currentLevel.entities) {
				synchronized (Level.currentLevel.walls) {
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
	}

}
