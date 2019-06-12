package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import trinity.Render;

public class Wall {

	protected Twin pos;
	protected Twin size;

	boolean solid = true;

	public static Segment image = null;

	public Wall(Twin pos, Twin size) {
		this.pos = pos;
		this.size = size;
	}

	public Wall(Twin pos, Twin size, boolean solid) {
		this(pos, size);
		this.solid = solid;
	}

	public void draw(Graphics2D g, int Layer) {
		if (Layer == 0) {
			if (image != null) {
				image.Draw(g, pos, null, null, 0);
			} else {
				new Segment (Level.images.get("pointer"),1).Draw(g, pos, null, null, 0);
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
