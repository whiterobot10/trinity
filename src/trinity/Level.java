package trinity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.glass.events.KeyEvent;

public class Level {

	public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public static HashMap<String, Level> levels = new HashMap<String, Level>();

	{
		images.put("pointer", Render.loadImage("trinity", "pointer.png"));
	}

	public static Level currentLevel = null;

	public List<Entity> entities = Collections.synchronizedList(new ArrayList<Entity>());
	public static List<Entity> newEntities = Collections.synchronizedList(new ArrayList<Entity>());
	public List<Wall> walls = Collections.synchronizedList(new ArrayList<Wall>());
	public static List<Wall> newWalls = Collections.synchronizedList(new ArrayList<Wall>());
	public List<Tile> tiles = Collections.synchronizedList(new ArrayList<Tile>());
	public static List<Tile> newTiles = Collections.synchronizedList(new ArrayList<Tile>());

	public static void clear() {
		levels.clear();
		images.clear();
		Key.reset = true;
		Key.resetKeys();
		images.put("pointer", Render.loadImage("trinity", "pointer.png"));
		images.put("tileset.null", Render.loadImage("trinity", "tileset/null.png"));

	}

	public static void update() {
		if (Level.currentLevel != null) {
			synchronized (Level.currentLevel.entities) {
				for (Entity e : currentLevel.entities) {
					e.update();
				}
			}
			Level.currentLevel.entities.addAll(Level.newEntities);
			Level.currentLevel.walls.addAll(Level.newWalls);
			Level.currentLevel.tiles.addAll(Level.newTiles);
			if (!newTiles.isEmpty()) {
				for (Tile t : Level.currentLevel.tiles) {
					t.Update();
				}
			}
			Level.newEntities.clear();
			Level.newTiles.clear();
			Level.newWalls.clear();
			for (int i = 0; i < Level.currentLevel.entities.size(); i++) {
				if (Level.currentLevel.entities.get(i).remove) {
					Level.currentLevel.entities.remove(i--);
				}
			}

		}
		Key.resetKeys();

	}

	public static void draw(Graphics2D g) {

		if (currentLevel != null) {
			//g.drawImage(Render.tile(images.get("pointer"), 5, 5), 0, 0, null);

			synchronized (Level.currentLevel.entities) {
				synchronized (Level.currentLevel.walls) {
					synchronized (Level.currentLevel.tiles) {
						for (int i = 0; i < Render.canvasLayers; i++) {
							for (Entity e : currentLevel.entities) {
								e.draw(g, i);
							}
							for (Wall e : currentLevel.walls) {
								e.draw(g, i);
							}
							for (Tile e : currentLevel.tiles) {
								e.draw(g, i);
							}
						}
					}
				}
			}
		}
	}

}
