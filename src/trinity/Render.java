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

	public static Frame frame;
	private static Canvas canvas;

	private static Dimension canvasSize;
	private static Dimension gameSize;
	public static float sizeFactor = 0;
	public static int canvasLayers;

	private static long lastFpsCheck = 0;
	private static int desiredFPS = 60;
	private static VolatileImage gameScreen;
	
	public static AffineTransform scroll = AffineTransform.getTranslateInstance(0, 0);
	public static Twin scrollOffset= new Twin(40,40);

	

	public static void init(Dimension gameSize, int canvasLayers) {

		Render.gameSize = gameSize;
		Render.canvasLayers = canvasLayers;

		canvasSize = new Dimension(0, 0);

		getBestSize(Toolkit.getDefaultToolkit().getScreenSize());
		// getBestSize(new Dimension(500, 500));
		frame = new Frame();
		canvas = new Canvas();

		canvas.setPreferredSize(canvasSize);
		if (fullscreen) {
			makeFullscreen();
		}
		canvas.addKeyListener(new KeyInput());
		canvas.addMouseListener(new MouseInput());
		canvas.addMouseMotionListener(new MouseMotionInput());
		canvas.setFocusable(true);
		canvas.requestFocus();

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

						g2.drawImage(gameScreen, 0, 0, (int) (gameSize.width * sizeFactor),
								(int) (gameSize.height * sizeFactor), null);
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
//		sizeFactor = 1;
//		while (screenSize.width > canvasSize.width + gameSize.width
//				&& screenSize.height > canvasSize.height + gameSize.height) {
//			canvasSize = new Dimension(canvasSize.width + gameSize.width, canvasSize.height + gameSize.height);
//			sizeFactor++;
//		}
		// canvasSize = screenSize;

		// 100, (50, 25)
		// 2, 4;
		double fooX = screenSize.width * 1.0 / gameSize.width;
		double fooY = screenSize.height * 1.0 / gameSize.height;
		//System.out.println(screenSize.width * 1.0 + " " + screenSize.height * 1.0);
		if (fooX < fooY) {
			canvasSize = new Dimension((int) (gameSize.width * fooX), (int) (gameSize.height * fooX));
			sizeFactor = (float) fooX;
		} else {
			canvasSize = new Dimension((int) (gameSize.width * fooY), (int) (gameSize.height * fooY));
			sizeFactor = (float) fooY;
		}
		//System.out.println(fooX + " " + fooY);
		//System.out.println(canvasSize.width + " " + canvasSize.height);

	}

	public static void resize(Dimension d) {
		canvasSize = d;
		canvas.setSize(canvasSize);
	}

	public static Dimension getSize() {
		return canvasSize;
	}

	public static BufferedImage loadImage(String path, String target) {
		BufferedImage rawImage;
		try {
			rawImage = ImageIO.read(new File(Game.trinitySubgamePath + "/" + target + "/gfx/" + path));
			BufferedImage finalImage = canvas.getGraphicsConfiguration().createCompatibleImage(rawImage.getWidth(),
					rawImage.getHeight(), rawImage.getTransparency());
			finalImage.getGraphics().drawImage(rawImage, 0, 0, null);
			return finalImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static BufferedImage loadImage(String path) {
		return loadImage(path, Game.currentName);
	}

	public static void fixDisplay(Dimension d) {
		d.width = Math.max(d.width, 1);
		d.height = Math.max(d.height, 1);
		if (d.equals(gameSize)) {
			gameScreen = canvas.getGraphicsConfiguration().createCompatibleVolatileImage(d.width, d.height);
		} else {
			gameScreen = canvas.getGraphicsConfiguration().createCompatibleVolatileImage(d.width, d.height);
			gameSize = d;
			getBestSize(Toolkit.getDefaultToolkit().getScreenSize());
			frame.setSize((int) (d.width * sizeFactor), (int) (d.height * sizeFactor));

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

	public static void DrawChain(Graphics2D g, Segment image, Twin pos, int lenght, int space, double rotation) {
		
		//Segment foo = new Segment(tile(image.getImage(), 1, lenght/(image.getImage().getHeight()+space), 0, space));
		//foo.Draw(g, pos, new Twin(-image.getImage().getWidth(),0), false, rotation);
		for(int i = 0; i < lenght; i++) {
			image.Draw(g, pos, new Twin(), false, rotation);
		}
		
		// g.drawLine(pos.ix(), pos.iy(), pos2.ix(), pos2.iy());
//		int j = 0;
//
//		g.drawImage(
//				image.getImage().getScaledInstance(image.getImage().getWidth() / 2, image.getImage().getHeight(), 0), 0,
//				0, null);
//
//		if (Math.abs(pos2.x - pos.x) > Math.abs(pos2.y - pos.y)) {
//
//			for (int i = 1; i < Math.abs(pos2.x - pos.x); i += space) {
//				int rad = 0;
//				if (Math.abs(pos2.y - pos.y) < Math.abs((pos2.x - pos.x))) {
//					rad = 1;
//				}
//
//				if (rad == 0 && pos2.y - pos.y > 0) {
//					rad = 2;
//				}
//				if (rad == 1 && pos2.x - pos.x < 0) {
//
//					rad = 3;
//				}
//				image.Draw(g, pos, new Twin(i * ((pos2.x - pos.x) / Math.abs(pos2.x - pos.x)),
//						(pos2.y - pos.y) * (i / Math.abs(pos2.x - pos.x))), false, pos.getRot(pos2) + rotation);
////				drawImage(g, image, pos.offset(i*((pos2.x - pos.x)/Math.abs(pos2.x - pos.x)), (pos2.y - pos.y) * (i / Math.abs(pos2.x - pos.x))), false,
////						false, rad);
//			}
//		} else {
//			for (float i = 1; i < Math.abs(pos2.y - pos.y); i += space) {
//				int rad = 0;
//				if (Math.abs(pos2.y - pos.y) < Math.abs((pos2.x - pos.x))) {
//					rad = 1;
//				}
//
//				if (rad == 0 && pos2.y - pos.y > 0) {
//					rad = 2;
//				}
//				if (rad == 1 && pos2.x - pos.x < 0) {
//
//					rad = 3;
//				}
////				drawImage(g, image, pos.offset((pos2.x - pos.x) * (i / Math.abs(pos2.y - pos.y)), i*((pos2.y - pos.y)/Math.abs(pos2.y - pos.y))), false,
////						false, rad);
//				image.Draw(g, pos, new Twin((pos2.x - pos.x) * (i / Math.abs(pos2.y - pos.y)),
//						i * ((pos2.y - pos.y) / Math.abs(pos2.y - pos.y))), false, pos.getRot(pos2) + rotation);
//			}
//		}
	}

	public static void drawString(Graphics g, Twin pos, String text) {
		g.setColor(Color.BLACK);
		g.drawString(text, (int) pos.x + 10, (int) pos.y + 4);
	}

	public static BufferedImage tile(BufferedImage b, int x, int y) {
		return tile(b, x, y, 0, 0);

	}
	
	public static BufferedImage tile(BufferedImage b, int x, int y, int xSpace, int ySpace) {
		BufferedImage foo = new BufferedImage(b.getWidth() * x + xSpace * (x-1), b.getHeight() * y + ySpace * (y-1), b.getType());
		Graphics2D g = foo.createGraphics();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				g.drawImage(b, i * (b.getWidth()+xSpace), j * (b.getHeight()+ySpace), null);
			}
		}

		return foo;

	}

}
