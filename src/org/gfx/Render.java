package org.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;

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

		startRendering();

	}

	private static void startRendering() {
		Thread thread = new Thread() {
			GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
			VolatileImage vImage = gc.createCompatibleVolatileImage(gameSize.width, gameSize.width);

			public void run() {
				while (Game.running) {
					if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
						vImage = gc.createCompatibleVolatileImage(gameSize.width, gameSize.height);
					}

					Graphics g = vImage.getGraphics();

					g.setColor(Color.black);
					g.fillRect(0, 0, gameSize.width, gameSize.height);
					g.setColor(Color.red);
					g.fillRect(1, 1, 119, 119);

					g.dispose();

					g = canvas.getGraphics();

					System.out.println(sizeFactor + " " + canvasSize.width + " " + canvasSize.height);

					g.drawImage(vImage, 0, 0, gameSize.width*sizeFactor, gameSize.height*sizeFactor, null);

					g.dispose();

					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		thread.setName("Renderer");
		thread.start();

	}

	private static void makeFullscreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = env.getDefaultScreenDevice();

		if (gd.isFullScreenSupported()) {
			frame.setUndecorated(true);
			gd.setFullScreenWindow(frame);
		}

	}

	private static void getBestSize(Dimension screenSize) {
		sizeFactor = 0;
		while (screenSize.width > canvasSize.width + gameSize.width
				&& screenSize.height > canvasSize.height + gameSize.height) {
			canvasSize = new Dimension(canvasSize.width + gameSize.width, canvasSize.height + gameSize.height);
			System.out.println(canvasSize.width + " " + canvasSize.height);
			System.out.println("test " + screenSize.width + " " + screenSize.height);
			sizeFactor++;
		}
		//canvasSize = screenSize;

	}

	public static void resize(Dimension d) {
		canvasSize = d;
		canvas.setSize(canvasSize);
	}

	public static Dimension getSize() {
		return canvasSize;
	}

}
