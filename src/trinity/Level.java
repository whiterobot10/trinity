package trinity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

class SortbyY implements Comparator<Entity>{
	public int compare(Entity o1, Entity o2) {
		return (int) (o1.pos.y-o2.pos.y);
	}
}





public class Level {
	
	Comparator<Entity> entitySorter = new SortbyY();

	public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public static ArrayList<Level> levels = new ArrayList<Level>();

	{
		importImage("pointer.png", "trinity");
	}

	public static Level currentLevel = null;

	public List<Entity> entities = Collections.synchronizedList(new ArrayList<Entity>());
	public static List<Entity> newEntities = Collections.synchronizedList(new ArrayList<Entity>());
	public List<Wall> walls = Collections.synchronizedList(new ArrayList<Wall>());
	public static List<Wall> newWalls = Collections.synchronizedList(new ArrayList<Wall>());
	public Tile[][] tiles = new Tile[100][100];
	public int tileSize = 16;
	
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
		//Key.resetKeys();
		images.put("pointer", Render.loadImage("pointer.png", "trinity"));
		images.put("tileset.null", Render.loadImage("tileset/null.png", "trinity"));

	}

	public static void update() {

		if (Level.currentLevel != null) {
			synchronized (Level.currentLevel.entities) {
				for (Entity e : currentLevel.entities) {
					e.update();
				}
			}
			Level.currentLevel.entities.addAll(Level.newEntities);
			Level.currentLevel.walls.addAll(Level.newWalls);
			if(!Level.newEntities.isEmpty()&&Level.currentLevel.entitySorter!=null) {
				Collections.sort(Level.currentLevel.entities, Level.currentLevel.entitySorter); 
			}
			Level.newEntities.clear();
			Level.newWalls.clear();
			for (int i = 0; i < Level.currentLevel.entities.size(); i++) {
				if (Level.currentLevel.entities.get(i).remove) {
					Level.currentLevel.entities.remove(i--);
				}
			}

		}
		Key.resetKeys();

	}
	

	public static void draw(Graphics2D g) {
		
		

		if (currentLevel != null) {
			//g.drawImage(Render.tile(images.get("pointer"), 5, 5), 0, 0, null);
			Graphics2D g_nonshift = (Graphics2D) g.create();
			AffineTransform old = g.getTransform();
			g.setTransform((AffineTransform) Render.scroll.clone());


			synchronized (Level.currentLevel.entities) {
				synchronized (Level.currentLevel.walls) {
				
					
					
						for (int i = 0; i < Render.canvasLayers; i++) {
							
							for (Entity e : currentLevel.entities) {
								
								if(e instanceof Gui) {
									e.draw(g_nonshift, i);
									} else {
									e.draw(g, i);
									}
							}
							for (Wall e : currentLevel.walls) {
								e.draw(g, i);
							}
							Tile.draw(g, currentLevel.tileSize, i, currentLevel.tiles);	
					}
				}
			}
		g.setTransform(old);
		}
	}

}
