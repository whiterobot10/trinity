package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Entity {

	public int layer;
	public Point2D.Float pos;
	public Point2D.Float vel;
	boolean solid = true;
	boolean left = false;
	public Shape hitbox = new Rectangle(0,0,0,0);

	public static BufferedImage image = Level.images.get("pointer");

	public Entity(Point2D.Float pos) {
		this.pos = pos;
		layer = 0;
	}

	public Entity(Point2D.Float pos, boolean solid) {
		this.pos = pos;
		layer = 0;
		this.solid = solid;
	}

	public Entity(Point2D.Float pos, int layer) {
		this.pos = pos;
		this.layer = layer;
	}

	public Entity(Point2D.Float pos, int layer, boolean solid) {
		this.pos = pos;
		this.layer = layer;
		this.solid = solid;
	}

	public void update() {

	}

	public void damage(float amount) {
		damage(amount, null);
	}

	public void damage(float amount, String type) {

	}

	public void drawSegment(Graphics g, BufferedImage image, Point2D.Float pos) {
		if (left) {
			pos.x *= -1;
		}
		Render.drawImage(g, image, pos, left);
		if (left) {
			pos.x *= -1;
		}

	}

	public void draw(Graphics g, int layer) {
		if (layer == this.layer) {
			drawSegment(g, image, pos);
			if (Game.debug) {
				g.setColor(Color.red);
				Rectangle foo = hitbox.getBounds();
				g.drawRect(foo.x,foo.y,foo.width,foo.height);
				g.setColor(Color.red);
				g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
			}
		}
	}

}
