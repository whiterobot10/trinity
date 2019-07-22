package stabby;

import java.awt.Graphics2D;

import trinity.Entity;
import trinity.Level;
import trinity.Segment;
import trinity.Twin;

public class Enemy extends Battler {
	
	static Segment base = new Segment(Level.images.get("enemy"), 1);

	public Enemy(Twin pos, Level level) {
		super(pos, level);
	}
	
	@Override
	public void draw(Graphics2D g, int layer) {
		base.Draw(g, pos, null, new Twin(-1, 1), 0);
		
	}
	


}
