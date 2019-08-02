package low_battery;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import trinity.Entity;
import trinity.Gui;
import trinity.Level;
import trinity.Segment;
import trinity.Twin;
import trinity.Object;

public class Foo extends Gui {

	public Foo(Twin pos, Level level) {
		super(pos, level);
	}

	static Segment icon = new Segment(Level.images.get("icons.slot.0"), 2);
	static Segment image = new Segment(Level.images.get("icons.battery"), 3);
	{
		icon.setRotatedImage(1, Level.images.get("icons.slot.1"), new Twin(0, 0));
		image.setRotatedImage(1, Level.images.get("icons.health"), new Twin(0, 0));
		image.setRotatedImage(2, Level.images.get("icons.thorium"), new Twin(0, 0));

	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 7) {
			image.Draw(g, new Twin(10, 10), new Twin(0, 0), scale, 0);
			icon.Draw(g, new Twin(10, 10), new Twin(0, 0), scale, 1);
		}
	}

	@Override
	protected boolean clsnCheck() {
		return false;
	}

	@Override
	protected ArrayList<Object> clsnObjects() {
		return null;
	}

	@Override
	public Shape hitbox() {
		return new Rectangle(0, 0, 0, 0);
	}

}
