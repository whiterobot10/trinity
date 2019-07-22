package trinity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity extends Object {

	public Twin vel = new Twin(0,0);
	public Twin scale = new Twin(1, 1);
	static float moveCheckAcc = 1.5f;
	public Shape hitbox = new Rectangle(0, 0, 0, 0);
	
	public static BufferedImage image = Level.images.get("pointer");


	public Entity(Twin pos, Level level) {
		super(pos, level);
	}

	@Override
	public void update() {

	}

	public void drawSegment(Graphics2D g, BufferedImage image, Twin pos, int rotations) {

		Render.drawImage(g, image, this.pos.offset(pos), scale, rotations);

	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 0) {
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

	public boolean move(Twin amount) {
		amount = pos.offset(amount);
		// System.out.println("pN" + pos.distance(amount));
		return moveTwords(amount, pos.distance(amount));
	}

	public boolean moveTwords(Twin target, float amount) {

		float distanceToMove = target.distance(pos);

		if (distanceToMove > amount) {
			distanceToMove = amount;
		}
		if (amount <= 0.0001) {
			return true;
		}

		float mult_x = target.x - pos.x;
		float mult_y = target.y - pos.y;

		float div = Math.abs(mult_x) + Math.abs(mult_y);
		mult_x /= div;
		mult_y /= div;

		// int checks = (int) (target.distance(pos) * moveCheckAcc);
		boolean worked = true;
		while (distanceToMove >= 0.0001) {
			// System.out.println(distanceToMove+" "+pos.x+" "+pos.y);
			float bar = Math.min(distanceToMove, 1.0f / 16);
			// distanceToMove -= bar;
			pos.x += bar * mult_x;
			pos.y += bar * mult_y;
			if (clsnCheck()) {
				pos.x -= bar * mult_x;
				pos.y -= bar * mult_y;

				pos.x += bar * mult_x;
				if (clsnCheck() || Math.abs(mult_x) <= 0.0001) {
					pos.x -= bar * mult_x;
					pos.y += bar * mult_y;
					if (clsnCheck() || Math.abs(mult_y) <= 0.0001) {
						pos.y -= bar * mult_y;
						return false;
					} else {
						distanceToMove -= Math.abs(bar * mult_y);
						worked = false;
					}

				} else {
					distanceToMove -= Math.abs(bar * mult_x);
					worked = false;
				}

			} else {
				distanceToMove -= Math.abs(bar);
			}

		}
//		for (int i = 0; i < checks; i++) {
//			worked = true;
//			pos.x += (step / checks) * mult_x;
//			pos.y += (step / checks) * mult_y;
//			if (clsnCheck()) {
//
//				pos.y -= (step / checks) * mult_y;
//				pos.x += (step / checks) * mult_x;
//
//				if (clsnCheck()) {
//					pos.x -= (step / checks) * mult_x;
//					if (Math.abs((step / checks) * mult_y) < 0.001) {
//						worked = false;
//					}
//				}
//				pos.y += (step / checks) * mult_y;
//				if (Math.abs((step / checks) * mult_y) > 0.001 && clsnCheck()) {
//					pos.y -= (step / checks) * mult_y;
//					if (Math.abs((step / checks) * mult_x) < 0.001) {
//						worked = false;
//					}
//
//				}
//
//			}
//
//		}
		return worked;

	}

	protected ArrayList<Object> clsnObjects() {
		ArrayList<Object> bar = new ArrayList<Object>();
		for (Object o : level.objects) {
			if (o.hitbox()!=null && o != this && hitbox().getBounds2D().intersects(o.hitbox().getBounds2D())) {
				Area foo = new Area(hitbox());
				foo.intersect(new Area(o.hitbox()));
				if (!foo.isEmpty()) {
					bar.add(o);
				}
			}
		}
		return bar;
	}

	protected boolean clsnCheck() {
		for (Object o : level.objects) {
			if (o.hitbox()!=null && o != this && hitbox().getBounds2D().intersects(o.hitbox().getBounds2D())) {
				Area foo = new Area(hitbox());
				foo.intersect(new Area(o.hitbox()));
				if (!foo.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public Shape hitbox() {
		return new Rectangle(pos.ix() - 5, pos.iy() - 5, 10, 10);
	}

}
