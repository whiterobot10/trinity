package trinity;


import java.awt.Graphics;
import java.util.ArrayList;

import trinity.Render;

public class Level {

	public static Level currentLevel = null;

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();

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
				for (Wall e : currentLevel.walls) {
					e.draw(g, i);
				}
			}
		}
	}

}
