package trinity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

class SortbyY implements Comparator<Object> {
	public int compare(Object o1, Object o2) {
		return (int) (o1.pos.y - o2.pos.y);
	}
}

public class Level {
	

	Comparator<Object> objectSorter = new SortbyY();

	public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public static ArrayList<Level> levels = new ArrayList<Level>();

	{
		importImage("pointer.png", "trinity");
	}

	// public static Level currentLevel = null;

	public List<Object> objects = Collections.synchronizedList(new ArrayList<Object>());
	public List<Object> newObjects = Collections.synchronizedList(new ArrayList<Object>());
	public Tile[][] tiles = new Tile[100][100];
	public int tileSize = 16;
	public boolean shouldProcess;
	public ArrayList<VolatileImage> drawTo = new ArrayList<VolatileImage>();

	public Level() {
		this(true);
	}

	public Level(boolean process) {
		shouldProcess = process;
		drawTo.add(Render.getGameScreen());
	}

	public static void importImage(String path) {
		String foo = path.toLowerCase();
		foo = foo.replace('/', '.');
		foo = foo.replace(".png", "");

		images.put(foo, Render.loadImage(path));
	}

	private void importImage(String path, String target) {
		images.put(path, Render.loadImage(path, target));

	}

	public static void clear() {
		levels.clear();
		images.clear();
		Key.reset = true;
		// Key.resetKeys();
		images.put("pointer", Render.loadImage("pointer.png", "trinity"));
		images.put("tileset.null", Render.loadImage("tileset/null.png", "trinity"));

	}
	
	public void updateObjects() {
		objects.addAll(newObjects);
		newObjects.clear();
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i)==null||objects.get(i).remove) {
				objects.remove(i--);
			}
		}
	}
	

	public static void update() {

		for (Level l : levels) {
			if (l.shouldProcess) {
				synchronized (l.objects) {
					for (Object o : l.objects) {
						o.update(l);
					}
					l.updateObjects();
				}
			}
		}

//		if (Level.currentLevel != null) {
//			synchronized (Level.currentLevel.entities) {
//				for (Entity e : currentLevel.entities) {
//					e.update();
//				}
//			}
//			Level.currentLevel.entities.addAll(Level.newEntities);
//			Level.currentLevel.walls.addAll(Level.newWalls);
//			if(!Level.newEntities.isEmpty()&&Level.currentLevel.objectSorter!=null) {
//				Collections.sort(Level.currentLevel.entities, Level.currentLevel.objectSorter); 
//			}
//			Level.newEntities.clear();
//			Level.newWalls.clear();
//			for (int i = 0; i < Level.currentLevel.entities.size(); i++) {
//				if (Level.currentLevel.entities.get(i).remove) {
//					Level.currentLevel.entities.remove(i--);
//				}
//			}
//
//		}
		Key.resetKeys();

	}

	public static void draw() {

		for (Level l : levels) {

			for (VolatileImage v : l.drawTo) {
				Graphics2D g = v.createGraphics();
				Graphics2D g_nonshift = v.createGraphics();
				//AffineTransform old = g.getTransform();
				g.setTransform((AffineTransform) Render.scroll.clone());
				synchronized (l.objects) {
					for (int i = 0; i < Render.canvasLayers; i++) {
						for (Object o : l.objects) {
							if (o instanceof Gui) {
								o.draw(g_nonshift, i);
							} else {
								o.draw(g, i);
							}
						}

					}
				}
				//g.setTransform(old);
			}

		}

//		if (currentLevel != null) {
//			//g.drawImage(Render.tile(images.get("pointer"), 5, 5), 0, 0, null);
//			Graphics2D g_nonshift = (Graphics2D) g.create();
//			AffineTransform old = g.getTransform();
//			g.setTransform((AffineTransform) Render.scroll.clone());
//
//
//			synchronized (Level.currentLevel.entities) {
//				synchronized (Level.currentLevel.walls) {
//				
//					
//					
//						for (int i = 0; i < Render.canvasLayers; i++) {
//							
//							for (Entity e : currentLevel.entities) {
//								
//								if(e instanceof Gui) {
//									e.draw(g_nonshift, i);
//									} else {
//									e.draw(g, i);
//									}
//							}
//							for (Wall e : currentLevel.walls) {
//								e.draw(g, i);
//							}
//							Tile.draw(g, currentLevel.tileSize, i, currentLevel.tiles);	
//					}
//				}
//			}
//		g.setTransform(old);
//		}
	}

}
