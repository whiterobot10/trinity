package trinity;

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
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import trinity.Level;

public class Render {

	private static boolean fullscreen = false;

	private static Frame frame;
	private static Canvas canvas;

	private static Dimension canvasSize;
	private static Dimension gameSize;
	public static int sizeFactor = 0;
	public static int canvasLayers;

	private static long lastFpsCheck = 0;
	private static int desiredFPS = 60;
	private static VolatileImage gameScreen;

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
		frame.addKeyListener(new KeyInput());
		canvas.addMouseListener(new MouseInput());
		canvas.addMouseMotionListener(new MouseMotionInput());

		frame.add(canvas);
		frame.pack();
		frame.setResizable(false);
		//frame.setLocationRelativeTo(null);

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

			public void run() {
				fixDisplay(gameSize);
				while (Game.running) {

					if (Render.settleFPS()) {
						if (gameScreen.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
							fixDisplay(gameSize);

						}

						Level.update();

						Graphics g = gameScreen.getGraphics();

						g.setColor(Color.white);
						g.fillRect(0, 0, gameSize.width, gameSize.height);

						Level.draw(g);

						g.setColor(Color.LIGHT_GRAY);
						g.drawOval(Key.mousePos.x - 10, Key.mousePos.y - 10, 20, 20);
						g.drawLine(0, 0, Key.mousePos.x, Key.mousePos.y);
						if (Level.images.get("pointer") != null) {
							drawImage(g, Level.images.get("pointer"),
									new Point2D.Float(Key.mousePos.x, Key.mousePos.y));
						}

						g.dispose();

						g = canvas.getGraphics();

						g.drawImage(gameScreen, 0, 0, gameSize.width * sizeFactor, gameSize.height * sizeFactor, null);
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
		System.out.println("HI");
		canvasSize = new Dimension(gameSize.width, gameSize.height);
		sizeFactor = 1;
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

	public static BufferedImage loadImage(String path) {
		BufferedImage rawImage;
		try {
			rawImage = ImageIO.read(new File(Game.trinitySubgamePath + "/" + Game.currentName + "/gfx/" + path));
			BufferedImage finalImage = canvas.getGraphicsConfiguration().createCompatibleImage(rawImage.getWidth(),
					rawImage.getHeight(), rawImage.getTransparency());
			finalImage.getGraphics().drawImage(rawImage, 0, 0, null);
			return finalImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void fixDisplay(Dimension d) {
		d.width = Math.max(d.width, 1);
		d.height = Math.max(d.height, 1);
		if (d.equals(gameSize)) {
			gameScreen = canvas.getGraphicsConfiguration().createCompatibleVolatileImage(d.width, d.height);
		} else {
			gameScreen = canvas.getGraphicsConfiguration().createCompatibleVolatileImage(d.width, d.height);
			getBestSize(Toolkit.getDefaultToolkit().getScreenSize());
			gameSize = d;
			frame.setSize(d.width * sizeFactor, d.height * sizeFactor);

		}

	}

	public static BufferedImage loadImage(String game, String path) {
		try {
			BufferedImage rawImage = ImageIO.read(new File(Game.trinitySubgamePath + "/" + game + "/gfx/" + path));
			BufferedImage finalImage = canvas.getGraphicsConfiguration().createCompatibleImage(rawImage.getWidth(),
					rawImage.getHeight(), rawImage.getTransparency());
			finalImage.getGraphics().drawImage(rawImage, 0, 0, null);
			return finalImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void drawImage(Graphics g, BufferedImage image, Point2D.Float pos) {
		drawImage(g, image, pos, false, false);
	}

	public static void drawImage(Graphics g, BufferedImage image, Point2D.Float pos, boolean flipped) {
		drawImage(g, image, pos, flipped, false);
	}

	public static void drawImage(Graphics g, BufferedImage image, Point2D.Float pos, boolean flippedHorz,
			boolean flippedVert) {

		int horzMult = 1;
		int vertMult = 1;
		if (flippedHorz) {
			horzMult = -1;
		}
		if (flippedVert) {
			vertMult = -1;
		}
		int width = image.getWidth();
		int height = image.getWidth();

		g.drawImage(image, (int) (pos.x) - (width / 2) * horzMult, (int) (pos.y) - (height / 2) * vertMult,
				width * horzMult, (height * vertMult), null);

//		if (flippedHorz && flippedVert) {
//			g.drawImage(image, (int) pos.x + (image.getWidth() / 2), (int) pos.y + (image.getHeight() / 2),
//					-image.getWidth(), -image.getHeight(), null);
//		} else if (flippedHorz) {
//			g.drawImage(image, (int) pos.x + (image.getWidth() / 2), (int) pos.y - (image.getHeight() / 2),
//					-image.getWidth(), image.getHeight(), null);
//		} else if (flippedVert) {
//			g.drawImage(image, (int) pos.x - (image.getWidth() / 2), (int) pos.y + (image.getHeight() / 2),
//					image.getWidth(), -image.getHeight(), null);
//		} else {
//			g.drawImage(image, (int) pos.x - (image.getWidth() / 2), (int) pos.y - (image.getHeight() / 2),
//					image.getWidth(), image.getHeight(), null);
//		}
	}

	public static void drawString(Graphics g, Point2D.Float pos, String text) {
		g.setColor(Color.BLACK);
		g.drawString(text, (int) pos.x + 10, (int) pos.y + 4);
	}

}
