package trinity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {

	protected Twin pos;
	protected byte[] offset = new byte[4];
	protected int size;
	protected int layer;
	protected BufferedImage tileset;

	protected String[] connectableIds;
	protected String id;

	public void Update() {
		boolean[] foo = new boolean[8];

		for (int i = 0; i < connectableIds.length; i++) {
			System.out.println("foo " + connectableIds[i]);
			if (connectableIds[i] == null) {

				for (int i2 = 0; i2 < 8; i2++) {
					foo[i2] = true;
				}
			}
		}

		for (Tile t : Level.currentLevel.tiles) {

			if (t.pos.ix() == pos.ix() - 1 && t.pos.iy() == pos.iy() - 1) {
				foo[0] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[0] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() && t.pos.iy() == pos.iy() - 1) {
				foo[1] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[1] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() + 1 && t.pos.iy() == pos.iy() - 1) {
				foo[2] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[2] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() - 1 && t.pos.iy() == pos.iy()) {
				foo[3] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[3] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() + 1 && t.pos.iy() == pos.iy()) {
				foo[4] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[4] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() - 1 && t.pos.iy() == pos.iy() + 1) {
				foo[5] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[5] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() && t.pos.iy() == pos.iy() + 1) {
				System.out.println("hi");
				foo[6] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[6] = true;
					}
				}
			}
			if (t.pos.ix() == pos.ix() + 1 && t.pos.iy() == pos.iy() + 1) {
				foo[7] = false;
				for (int i = 0; i < connectableIds.length; i++) {
					if (t.id.equals(connectableIds[i])) {
						foo[7] = true;
					}
				}
			}

		}
		for (int i = 0; i < 8; i++) {
			System.out.println(i + " " + foo[i]);
		}
		System.out.println(id);

		bar(foo, 1, 3, 0, 0);
		bar(foo, 4, 1, 2, 1);
		bar(foo, 6, 4, 7, 2);
		bar(foo, 3, 6, 5, 3);

	}

	public Tile() {
		this(0, 0);
	}

	public Tile(int x, int y) {
		this.pos = new Twin(x, y);
		size = 8;
		layer = 0;
		tileset = Level.images.get("tileset.null");
		connectableIds = new String[1];
		connectableIds[0] = "null";
		id = "null";

	}

	public void draw(Graphics2D g, int layer) {

		if (layer == this.layer) {
			draw(g, pos, offset[0], offset[1], offset[2], offset[3], tileset, size, layer);
		}
	}

	public static void draw(Graphics2D g, Twin pos, int offset0, int offset1, int offset2, int offset3,
			BufferedImage tileset, int size, int layer) {
		int foo = (size + 1) / 2;
		g.drawImage(tileset.getSubimage(size * offset0, 0, foo, foo), pos.ix() * size, pos.iy() * size, null);
		g.drawImage(tileset.getSubimage(size * offset1 + foo, 0, foo, foo), pos.ix() * size + size / 2, pos.iy() * size,
				null);
		g.drawImage(tileset.getSubimage(size * offset3, foo, foo, foo), pos.ix() * size, pos.iy() * size + size / 2,
				null);
		g.drawImage(tileset.getSubimage(size * offset2 + foo, foo, foo, foo), pos.ix() * size + size / 2,
				pos.iy() * size + size / 2, null);

	}

	private void bar(boolean[] foo, int i, int j, int k, int l) {
		if (!foo[i] && !foo[j]) {
			offset[l] = 0;
		}
		if (foo[i] && !foo[j]) {
			offset[l] = 1;
		}
		if (!foo[i] && foo[j]) {
			offset[l] = 2;
		}

		if (foo[i] && foo[j]) {
			if (foo[k]) {
				// System.out.println(foo[k]+" "+k);
				offset[l] = 4;
			} else {
				offset[l] = 3;
			}
		}
	}

}
