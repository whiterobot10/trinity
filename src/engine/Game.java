package engine;

import java.awt.Dimension;
import java.io.IOException;

import render.Render;

public class Game {
	
	public static boolean running = true;

	public static void main(String[] args) {
		Render.init(new Dimension(160,120),1);
		
		try {
			System.out.println(Render.loadImage("/gfx/dummy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void quit() {
		running = false;
		
	}

}
