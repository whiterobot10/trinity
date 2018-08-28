package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import trinity.Render;

public class Wall {

	int layer;
	Twin pos;
	Twin size;

	boolean solid = true;

	public static BufferedImage image = null;

	public Wall(Twin pos, Twin size) {
		this.pos = pos;
		this.size = size;
		layer = 0;
	}

	public Wall(Twin pos, Twin size, boolean solid) {
		this.pos = pos;
		this.size = size;
		this.solid = solid;
		layer = 0;
	}

	public Wall(Twin pos, Twin size, int layer) {
		this.pos = pos;
		this.size = size;
		this.layer = layer;
	}

	public Wall(Twin pos, Twin size, boolean solid, int layer) {
		this.pos = pos;
		this.size = size;
		this.solid = solid;
		this.layer = layer;
	}

	public void draw(Graphics2D g, int Layer) {
		if (Game.debug) {
			g.setColor(Color.red);
			g.fillRect((int) (pos.x - (size.x / 2)), (int) (pos.y - (size.y / 2)), (int) size.x, (int) size.y);
		}
		Render.drawImage(g, image, pos, true);
		g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
	}

}
