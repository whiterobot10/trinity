package trinity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

public class Wall extends Object {

	protected Twin size;


	public Segment image = null;

	public Wall(Twin pos, Twin size, Level level) {
		super(pos, level);
		this.size = size;
		image=new Segment (Level.images.get("pointer"),1);
	}
	public Wall(Twin pos, Twin size, Segment image,Level level) {
		super(pos, level);
		this.size = size;
		this.image=image;
		
	}


	public void draw(Graphics2D g, int Layer) {
		if (Layer == 0) {
			if (image != null) {
				image.Draw(g, pos, null, null, 0);
			}

		}
		if (Game.debug) {
//			g.setColor(Color.black);
//			g.draw(hitbox());
//			g.setColor(Color.RED);
//			g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
		}

	}

	public Shape hitbox() {
		return new Rectangle(pos.ix() - (size.ix() / 2), pos.iy() - (size.iy() / 2), size.ix(), size.iy());
	}

}
