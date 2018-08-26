package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import trinity.Render;

public class Wall {

	int layer;
	Point2D.Float pos;
	Point2D.Float size;
	
	boolean solid = true;

	public static BufferedImage image = null;

	public Wall(Point2D.Float pos, Point2D.Float size) {
		this.pos = pos;
		this.size = size;
		layer = 0;
	}

	public Wall(Point2D.Float pos, Point2D.Float size, boolean solid) {
		this.pos = pos;
		this.size = size;
		this.solid = solid;
		layer = 0;
	}

	public Wall(Point2D.Float pos, Point2D.Float size, int layer) {
		this.pos = pos;
		this.size = size;
		this.layer = layer;
	}

	public Wall(Point2D.Float pos, Point2D.Float size, boolean solid, int layer) {
		this.pos = pos;
		this.size = size;
		this.solid = solid;
		this.layer = layer;
	}

	public void draw(Graphics g, int Layer) {
		if (Game.debug) {
			g.setColor(Color.red);
			g.fillRect((int) (pos.x - (size.x / 2)), (int) (pos.y - (size.y / 2)), (int) size.x, (int) size.y);
		}
		Render.drawImage(g, image, pos, true);
		g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
	}

}
