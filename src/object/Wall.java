package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import render.Render;

public class Wall {

	int layer;
	Point2D.Float pos;
	Point2D.Float size;
	static boolean debug = true;
	boolean solid = true;

	public static BufferedImage image = null;
	
	public Wall(Point2D.Float pos, Point2D.Float size) {
		this.pos = pos;
		this.size = size;
	}
	
	public Wall(Point2D.Float pos, Point2D.Float size, boolean solid) {
		this.pos = pos;
		this.size = size;
		this.solid = solid;
	}

	public void draw(Graphics g, int Layer) {
		if (debug) {
			g.setColor(Color.red);
			g.fillRect((int) (pos.x - (size.x / 2)), (int) (pos.y - (size.y / 2)), (int) size.x, (int) size.y);
		}
		Render.drawImage(g, image, pos, true);
		g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
	}

}
