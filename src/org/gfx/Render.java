package org.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.engine.Game;

public class Render {

	private static Frame frame;
	private static Canvas canvas;

	private static Dimension canvasSize;
	private static Dimension gameSize;
	
	private static int sizeFactor = 0;

	public static void init(Dimension game_size) {

		gameSize = game_size;

		canvasSize = new Dimension(0, 0);

		getBestSize(Toolkit.getDefaultToolkit().getScreenSize());

		frame = new Frame();
		canvas = new Canvas();

		canvas.setPreferredSize(canvasSize);
		
		makeFullscreen();

		frame.add(canvas);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Game.quit();
				System.exit(0);
			}
		});

		frame.setVisible(true);

	}

	private static void makeFullscreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = env.getDefaultScreenDevice();
		
		if(gd.isFullScreenSupported()) {
			frame.setUndecorated(true);
		}
		
	}

	private static void getBestSize(Dimension screenSize) {
		sizeFactor=0;
		while (canvasSize.width < screenSize.width + gameSize.width
				&& canvasSize.height < screenSize.height + gameSize.height) {
			canvasSize = new Dimension(canvasSize.width + gameSize.width, canvasSize.height + gameSize.height);
			sizeFactor++;
		}
		canvasSize = screenSize;

	}

	public static void resize(Dimension d) {
		canvasSize = d;
		canvas.setSize(canvasSize);
	}

	public static Dimension getSize() {
		return canvasSize;
	}

}
