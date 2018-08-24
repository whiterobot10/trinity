package org.engine;

import java.awt.Dimension;

import org.gfx.Render;

public class Game {
	
	public static boolean running = true;

	public static void main(String[] args) {
		Render.init(new Dimension(160,120));

	}

	public static void quit() {
		running = false;
		
	}

}
