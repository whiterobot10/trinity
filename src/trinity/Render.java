package trinity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		// frame.setLocationRelativeTo(null);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Game.quit();
				System.exit(0);
			}
		});

		frame.setVisible(true);

		startRendering();

	}
	
	public static Twin getGameSize() {
		return new Twin(gameSize);
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

						Graphics2D g = gameScreen.createGraphics();

						g.setColor(Color.white);
						g.fillRect(0, 0, gameSize.width, gameSize.height);

						Level.draw(g);

//						if (Level.images.get("pointer") != null) {
//							drawImage(g, Level.images.get("pointer"), new Twin(Key.mousePos.x, Key.mousePos.y));
//						}

						g.dispose();

						Graphics g2 = canvas.getGraphics();

						g2.drawImage(gameScreen, 0, 0, gameSize.width * sizeFactor, gameSize.height * sizeFactor, null);
						g2.dispose();
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

	public static void drawImage(Graphics2D g, BufferedImage image, Twin pos) {
		drawImage(g, image, pos, false, false, 0);
	}

	public static void drawImage(Graphics2D g, BufferedImage image, Twin pos, boolean flipped) {
		drawImage(g, image, pos, flipped, false, 0);
	}

	public static void drawImage(Graphics2D g, BufferedImage image, Twin pos, boolean flippedHorz, boolean flippedVert,
			float rotate) {
		rotate %= 4;
		int yoff = 0;
		int xoff = 0;
//		if (rotate == 1 || rotate == 2) {
//			yoff = -1;
//		}
//		if (rotate == 2 || rotate == 3) {
//			xoff = -1;
//		}
		int horzMult = 1;
		int vertMult = 1;
		if (flippedHorz) {
			horzMult = -1;
		}
		if (flippedVert) {
			vertMult = -1;
		}
		int width = image.getWidth();
		int height = image.getHeight();

		AffineTransform old = g.getTransform();
		g.rotate(Math.toRadians(rotate * 90), pos.ix(), pos.iy());
		// g.rotate(-Math.toRadians(rotate), pos.x, pos.y);

		g.drawImage(image, pos.ix() + xoff - (width / 2) * horzMult, pos.iy() + yoff - (height / 2) * vertMult,
				width * horzMult, (height * vertMult), null);
		g.setTransform(old);

	}

	public static void DrawChain(Graphics2D g, BufferedImage image, Twin pos, Twin pos2, float space) {
		// g.drawLine(pos.ix(), pos.iy(), pos2.ix(), pos2.iy());
		for (float i = 0; i < pos.distance(pos2); i += space) {
			int rad = 0;
			if (Math.abs(pos2.y - pos.y) < Math.abs((pos2.x - pos.x))) {
				rad = 1;
			}

			if (rad == 0 && pos2.y - pos.y > 0) {
				rad = 2;
			}
			if (rad == 1 && pos2.x - pos.x < 0) {
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				rad = 3;
			}
			drawImage(g, image, pos.move((pos2.x - pos.x) * (i / pos.distance(pos2)),
					(pos2.y - pos.y) * (i / pos.distance(pos2)), false), false, false, rad);
		}
	}

	public static void drawString(Graphics g, Twin pos, String text) {
		g.setColor(Color.BLACK);
		g.drawString(text, (int) pos.x + 10, (int) pos.y + 4);
	}

}
