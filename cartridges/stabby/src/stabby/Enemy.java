package stabby;

import java.awt.Graphics2D;

import trinity.Entity;
import trinity.Level;
import trinity.Segment;
import trinity.Twin;

public class Enemy extends Battler {

	static Segment arms = new Segment(Level.images.get("arms"), 1);
	static Segment slash = new Segment(Level.images.get("slash"), 1);
	static Segment blades = new Segment(Level.images.get("blade_1"), 2);
	{
		blades.setRotatedImage(1, Level.images.get("blade_2"), new Twin(0, 0));
	}

	static Segment body = new Segment(Level.images.get("enemy"), 2);

	public Enemy(Twin pos, Level level) {
		super(pos, level);
	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 0 && active != this) {
			body.Draw(g, pos, new Twin(0, 1), new Twin(-1, 1), 0);
			blades.Draw(g, pos, new Twin(-6, 2), new Twin(-1, 1), 0);
			arms.Draw(g, pos, null, new Twin(-1, 1), 0);
		}
		if (layer == 1 && active == this) {
			body.Draw(g, pos, new Twin(0, 1), new Twin(-1, 1), 0);
			if (animFrame < 15) {
				blades.Draw(g, pos, new Twin(-6, -6), new Twin(-1, 1), 0);
				arms.Draw(g, pos, new Twin(0, 4), new Twin(-1, -1), 0);
			} else if (animFrame < 30) {
				if (animFrame < 20) {
					slash.Draw(g, pos, new Twin(10, -10), new Twin(-1, 1), 0);
				}
				blades.Draw(g, pos, new Twin(7, 2), new Twin(-1, 1), 1);
				arms.Draw(g, pos, new Twin(-1, 0), null, 0);
			} else {
				blades.Draw(g, pos, new Twin(-6, 2), new Twin(-1, 1), 0);
				arms.Draw(g, pos, null, new Twin(-1, 1), 0);
			}
		}

	}

}
