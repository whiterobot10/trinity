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
	public Twin pos;
	public Twin vel;
	boolean solid = true;
	public boolean left = false;
	static float moveCheckAcc = 1.5f;
	public Shape hitbox = new Rectangle(0, 0, 0, 0);

	public static BufferedImage image = Level.images.get("pointer");

	public Entity() {
		pos = new Twin(0, 0);
		layer = 0;
	}

	public Entity(Twin pos) {
		this.pos = pos;
		layer = 0;

	}

	public Entity(Twin pos, boolean solid) {
		this(pos);
		this.solid = solid;
	}

	public Entity(Twin pos, int layer) {
		this(pos);
		this.layer = layer;
	}

	public Entity(Twin pos, int layer, boolean solid) {
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

	public void drawSegment(Graphics2D g, BufferedImage image, Twin pos, int rotations) {
		if (left) {
			pos.x *= -1;
		}
		Render.drawImage(g, image, this.pos.move(pos, false), left, false, rotations);

	}

	public void draw(Graphics2D g, int layer) {
		if (layer == this.layer) {
			drawSegment(g, image);
			if (Game.debug) {
				g.setColor(Color.red);
				Rectangle foo = hitbox.getBounds();
				g.drawRect(foo.x, foo.y, foo.width, foo.height);
				g.setColor(Color.red);
				g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
			}
		}
	}

	public void drawSegment(Graphics2D g, BufferedImage image) {
		drawSegment(g, image, new Twin(), 0);

	}

	public boolean move(Twin target, float step) {

		if (target.distance(pos) < step) {
			step = (float) target.distance(pos);
		}
		if (step <= 0.0001) {
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
