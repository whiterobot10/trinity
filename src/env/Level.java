package env;

import java.awt.Graphics;
import java.util.ArrayList;

import object.Entity;
import object.Wall;
import render.Render;

public class Level {

	public static Level currentLevel = null;

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Wall> Walls = new ArrayList<Wall>();

	public static void update() {
		for (Entity e : currentLevel.entities) {
			e.update();
		}

	}

	public static void draw(Graphics g) {
		if (currentLevel != null) {
			for (int i = 0; i < Render.canvasLayers; i++) {
				for (Entity e : currentLevel.entities) {
					e.draw(g, i);
				}
			}
		}
	}

}
