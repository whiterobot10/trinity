package engine;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import env.Level;
import object.Entity;
import render.Render;

public class Game {

	public static boolean running = true;

	public static void main(String[] args) {
		Render.init(new Dimension(160, 120), 1);

		Level.currentLevel = new Level();
		Level.currentLevel.entities.add(new Entity(new Point2D.Float(20, 20), 0));

	}

	public static void quit() {
		running = false;

	}

}
