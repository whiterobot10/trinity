package stabby;

import java.awt.Graphics2D;

import trinity.Entity;
import trinity.Level;
import trinity.Segment;
import trinity.Twin;

public class Ally extends Battler {

	static Segment arms = new Segment(Level.images.get("arms"), 1);
	static Segment slash = new Segment(Level.images.get("slash"), 1);
	static Segment blades = new Segment(Level.images.get("blade_1"), 2);
	{
		blades.setRotatedImage(1, Level.images.get("blade_2"), new Twin(0, 0));
	}

	public Ally(Twin pos, Level level) {
		super(pos, level);
	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (active != this) {
			blades.Draw(g, pos, new Twin(-6, 2), null, 0);
			arms.Draw(g, pos, null, null, 0);

		} else if (animFrame < 15) {
			blades.Draw(g, pos, new Twin(-6, -2), null, 0);
			arms.Draw(g, pos, null, new Twin(1, -1), 0);
		} else if (animFrame < 30) {
			if (animFrame < 20) {
				slash.Draw(g, pos, new Twin(10, -10), null, 0);
			}
			blades.Draw(g, pos, new Twin(6, 2), null, 1);
			arms.Draw(g, pos, null, new Twin(-1, 1), 0);
		} else {
			blades.Draw(g, pos, new Twin(-6, 2), null, 0);
			arms.Draw(g, pos, null, null, 0);
		}

	}

}
