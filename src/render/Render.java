package render;

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
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.javafx.webkit.theme.Renderer;

import engine.Game;

public class Render {

	private static boolean fullscreen = false;

	private static Frame frame;
	private static Canvas canvas;

	private static Dimension canvasSize;
	private static Dimension gameSize;
	private static int sizeFactor = 0;
	public static int canvasLayers;

	private static long lastFpsCheck = 0;
	private static int desiredFPS = 60;

	private static double test = 10;
	
	

	public static void init(Dimension gameSize, int canvasLayers) {

		Render.gameSize = gameSize;
		Render.canvasLayers = canvasLayers;

		canvasSize = new Dimension(0, 0);

		getBestSize(Toolkit.getDefaultToolkit().getScreenSize());

		frame = new Frame();
		canvas = new Canvas();

		canvas.setPreferredSize(canvasSize);
		if (fullscreen) {
			makeFullscreen();
		}

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
			VolatileImage vImage = gc.createCompatibleVolatileImage(gameSize.width, gameSize.height);

			public void run() {
				while (Game.running) {

					if (Render.settleFPS()) {
						if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
							vImage = gc.createCompatibleVolatileImage(gameSize.width, gameSize.height);
						}

						Graphics g = vImage.getGraphics();

						g.setColor(Color.red);
						g.fillRect(0, 0, gameSize.width, gameSize.height);
						g.setColor(Color.black);
						g.drawRect(10, (int) test, 20, 20);
						test += 0.01;

						g.setColor(Color.LIGHT_GRAY);

						g.dispose();

						g = canvas.getGraphics();

						g.drawImage(vImage, 0, 0, gameSize.width * sizeFactor, gameSize.height * sizeFactor, null);

						g.dispose();
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

	private static boolean settleFPS() {
		if (System.nanoTime() > lastFpsCheck + (1000000000 / desiredFPS)) {
			lastFpsCheck = System.nanoTime();
			return true;
		}
		return false;

	}

	private static void getBestSize(Dimension screenSize) {
		sizeFactor = 0;
		while (screenSize.width > canvasSize.width + gameSize.width
				&& screenSize.height > canvasSize.height + gameSize.height) {
			canvasSize = new Dimension(canvasSize.width + gameSize.width, canvasSize.height + gameSize.height);
			sizeFactor++;
		}
		// canvasSize = screenSize;

	}

	public static void resize(Dimension d) {
		canvasSize = d;
		canvas.setSize(canvasSize);
	}

	public static Dimension getSize() {
		return canvasSize;
	}
	
	public static BufferedImage loadImage(String path) throws IOException {
		BufferedImage rawImage = ImageIO.read(Render.class.getResource(path));
		return canvas.getGraphicsConfiguration().createCompatibleImage(rawImage.getWidth(), rawImage.getHeight(), rawImage.getTransparency());
		
	}

}
