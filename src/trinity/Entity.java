package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	static float moveCheckAcc = 1.5f;
	public Shape hitbox = new Rectangle(0, 0, 0, 0);

	public static BufferedImage image = Level.images.get("pointer");

	public Entity() {
		pos = new Point2D.Float(0, 0);
		layer = 0;
	}

	public Entity(Point2D.Float pos) {
		this.pos = pos;
		layer = 0;

	}

	public Entity(Point2D.Float pos, boolean solid) {
		this(pos);
		this.solid = solid;
	}

	public Entity(Point2D.Float pos, int layer) {
		this(pos);
		this.layer = layer;
	}

	public Entity(Point2D.Float pos, int layer, boolean solid) {
		this(pos, layer);
		this.solid = solid;
	}

	public void update() {

	}

	public void damage(float amount) {
		damage(amount, null);
	}

	public void damage(float amount, String type) {

	}

	public void drawSegment(Graphics2D g, BufferedImage image, Point2D.Float pos) {
		if (left) {
			pos.x *= -1;
		}
		Render.drawImage(g, image, pos, left);
		if (left) {
			pos.x *= -1;
		}

	}

	public void draw(Graphics2D g, int layer) {
		if (layer == this.layer) {
			drawSegment(g, image, pos);
			if (Game.debug) {
				g.setColor(Color.red);
				Rectangle foo = hitbox.getBounds();
				g.drawRect(foo.x, foo.y, foo.width, foo.height);
				g.setColor(Color.red);
				g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
			}
		}
	}

	public boolean move(Point2D.Float target, float step) {

		if (target.distance(pos) < step) {
			step = (float) target.distance(pos);
		}
		if (step <= 0.02) {
			return true;
		}
		float mult_x = target.x - pos.x;
		float mult_y = target.y - pos.y;

		float div = Math.abs(mult_x) + Math.abs(mult_y);
		mult_x /= div;
		mult_y /= div;

		int checks = (int) (target.distance(pos) * moveCheckAcc);

		for (int i = 0; i < checks; i++) {
			pos.x += (step / checks) * mult_x;
			pos.y += (step / checks) * mult_y;
			if (clsnCheck()) {
				pos.x -= (step / checks) * mult_x;
				pos.y -= (step / checks) * mult_y;
				return false;
			}

		}
		return true;
	}

	private boolean clsnCheck() {
		return false;
	}

}
