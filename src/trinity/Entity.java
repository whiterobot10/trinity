package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import trinity.Render;

public class Entity {

	int layer;
	Point2D.Float pos;
	Point2D.Float vel;
	boolean solid = true;

	public static BufferedImage image = null;

	public Entity(Point2D.Float pos) {
		this.pos = pos;
		layer = 0;
		image = Level.images.get("pointer");
	}

	public Entity(Point2D.Float pos, boolean solid) {
		this.pos = pos;
		layer = 0;
		this.solid = solid;
		image = Level.images.get("pointer");
	}

	public Entity(Point2D.Float pos, int layer) {
		this.pos = pos;
		this.layer = layer;
		image = Level.images.get("pointer");
	}

	public Entity(Point2D.Float pos, int layer, boolean solid) {
		this.pos = pos;
		this.layer = layer;
		this.solid = solid;
		image = Level.images.get("pointer");
	}

	public void update() {

	}

	public void damage(float amount) {
		damage(amount, null);
	}

	public void damage(float amount, String type) {

	}

	public void draw(Graphics g, int layer) {
		if (layer == this.layer) {
			Render.drawImage(g, image, pos, false);
			if (Game.debug) {
				g.setColor(Color.red);
				g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
			}
		}
	}

}
