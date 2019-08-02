package low_battery;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import trinity.Entity;
import trinity.Game;
import trinity.Level;
import trinity.Object;
import trinity.Segment;
import trinity.Twin;

public class Bullet extends trinity.Entity {
	static Segment laser = new Segment(Level.images.get("bullet.0"), 4);
	static Segment bolt = new Segment(Level.images.get("bolt"), 4);

	{
		laser.setRotatedImage(1, Level.images.get("bullet.1"), Twin.zero);
		laser.setRotatedImage(2, Level.images.get("bullet.2"), Twin.zero);
		laser.setRotatedImage(3, Level.images.get("bullet.3"), Twin.zero);
	}

	float damage = 1;
	float shock = 0.05f;
	int maxTimer = 100;
	int timer = 0;
	double rotation = 0;

	@Override
	public void update() {
		if (++timer > maxTimer) {
			remove = true;
		}
		if (!move(vel)) {
			pos.move(vel.unit());
			for (Object e : clsnObjects()) {
				if (e instanceof Pawn) {
					((Pawn) e).damage(damage, shock);

				}
			}
			// System.out.println("foo");
			remove = true;
		}

	}

	public Shape hitbox() {
		return new Rectangle(pos.ix() - 2, pos.iy() + 2, 4, 4);
	}

	public Bullet(Twin pos, Twin vel, Level level) {
		super(pos, level);
		this.vel = vel;
		rotation = vel.getRot();
		if (rotation < 0) {
			rotation += 360;
		}
	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 2) {

			if (Game.debug) {
				g.setColor(Color.red);
				g.draw(hitbox());
				g.setColor(Color.red);
				g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
			}

			// System.out.println(Math.round(rotation/laser.degreesPerRot));
			laser.Draw(g, pos.offset(0, -2), null, null, (int) Math.round(rotation / laser.degreesPerRot));
		}
	}
}
