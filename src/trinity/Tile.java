package trinity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

abstract public class Tile {

	// protected byte[] offset = new byte[4];

	public static BufferedImage mergeTiles(BufferedImage tileset, boolean[][] grid) {
		BufferedImage foo = new BufferedImage(tileset.getWidth() / 5 * grid.length,
				tileset.getHeight() * grid[0].length, tileset.getTransparency());
		Graphics2D g = foo.createGraphics();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y]) {
					drawSection(g, tileset, grid, x, y, 0);
					drawSection(g, tileset, grid, x, y, 1);
					drawSection(g, tileset, grid, x, y, 2);
					drawSection(g, tileset, grid, x, y, 3);
				}

			}
		}
		return foo;
	}

//	protected Tile() {
//	}

	private static void drawSection(Graphics2D g, BufferedImage tileset, boolean[][] grid, int x, int y, int corner) {

		int xoff = -1;
		int yoff = -1;

		int sizeX = tileset.getWidth() / 10;
		int sizeY = tileset.getHeight() / 2;

		int xcorner = 0;
		int ycorner = 0;

		BufferedImage subimage = null;

		if (corner % 4 == 1) {
			xoff = 1;
			xcorner = sizeX;
		} else if (corner % 4 == 2) {
			xoff = 1;
			xcorner = sizeX;
			yoff = 1;
			ycorner = sizeY;
		} else if (corner % 4 == 3) {
			yoff = 1;
			ycorner = sizeY;
		}

		if (getTile(x + xoff, y, grid)) {
			if (getTile(x, y + yoff, grid)) {
				if (getTile(x + xoff, y + yoff, grid)) {
					subimage = tileset.getSubimage(sizeX * 8 + xcorner, ycorner, sizeX, sizeY);
				} else {
					subimage = tileset.getSubimage(sizeX * 6 + xcorner, ycorner, sizeX, sizeY);
				}
			} else {
				subimage = tileset.getSubimage(sizeX * 4 + xcorner, ycorner, sizeX, sizeY);
			}
		} else {
			if (getTile(x, y + yoff, grid)) {
				subimage = tileset.getSubimage(sizeX * 2 + xcorner, ycorner, sizeX, sizeY);
			} else {
				subimage = tileset.getSubimage(xcorner, ycorner, sizeX, sizeY);
			}

		}
		g.drawImage(subimage, x * sizeX * 2 + xcorner, y * sizeY * 2 + ycorner, null);

	}

//
	private static boolean getTile(int x, int y, boolean[][] tiles) {
		if (x < 0 || x > tiles.length - 1 || y < 0 || y > tiles[x].length - 1) {
			return false;
		} else {
			return tiles[x][y];
		}

	}
//
//	public static void update(Tile[][] tiles) {
//		boolean[] foo = new boolean[8];
//		for (int x = 0; x < tiles.length; x++) {
//			for (int y = 0; y < tiles[x].length; y++) {
//
//				Tile tile = getTile(x, y, tiles);
//				if (tile != null) {
//
//					String[] currentIds = tile.getConnectableIds();
//
//					for (int i = 0; i < currentIds.length; i++) {
//
//						if (currentIds[i] == null) {
//							for (int i2 = 0; i2 < 8; i2++) {
//								foo[i2] = true;
//							}
//						}
//					}
//
//					if (getTile(x - 1, y - 1, tiles) != null) {
//						foo[0] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x - 1, y - 1, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[0] = true;
//							}
//						}
//					}
//					if (getTile(x, y - 1, tiles) != null) {
//						foo[1] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x, y - 1, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[1] = true;
//							}
//						}
//					}
//					if (getTile(x + 1, y - 1, tiles) != null) {
//						foo[2] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x + 1, y - 1, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[2] = true;
//							}
//						}
//					}
//					if (getTile(x - 1, y, tiles) != null) {
//						foo[3] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x - 1, y, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[3] = true;
//							}
//						}
//					}
//					if (getTile(x + 1, y, tiles) != null) {
//						foo[4] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x + 1, y, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[4] = true;
//							}
//						}
//					}
//
//					if (getTile(x - 1, y + 1, tiles) != null) {
//						foo[5] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x - 1, y + 1, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[5] = true;
//							}
//						}
//					}
//					if (getTile(x, y + 1, tiles) != null) {
//						foo[6] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x, y + 1, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[6] = true;
//							}
//						}
//					}
//					if (getTile(x + 1, y + 1, tiles) != null) {
//						foo[7] = false;
//						for (int i = 0; i < tile.getConnectableIds().length; i++) {
//							if (getTile(x + 1, y + 1, tiles).getId().equals(tile.getConnectableIds()[i])) {
//								foo[7] = true;
//							}
//						}
//					}
//					System.out.println(x + " " + y);
//					for (int i = 0; i < 8; i++) {
//						System.out.println(i + " " + foo[i]);
//					}
//					System.out.println();
//					tiles[x][y].bar(foo, 1, 3, 0, 0);
//					tiles[x][y].bar(foo, 4, 1, 2, 1);
//					tiles[x][y].bar(foo, 6, 4, 7, 2);
//					tiles[x][y].bar(foo, 3, 6, 5, 3);
//				}
//			}
//		}
//
//	}
//
//	public abstract BufferedImage getImage();
//
//	public abstract String[] getConnectableIds();
//
//	public abstract String getId();
//
//	public abstract int getLayer();
//
//	public static void draw(Graphics2D g, int size, int layer, Tile[][] tiles) {
//		for (int x = 0; x < tiles.length; x++) {
//			for (int y = 0; y < tiles[x].length; y++) {
//				Tile tile = getTile(x, y, tiles);
//				if (tile != null && layer == tile.getLayer()) {
//					draw(g, new Twin(x, y), tile.offset[0], tile.offset[1], tile.offset[2], tile.offset[3],
//							tile.getImage(), size, layer);
//				}
//			}
//		}
//	}
//
//	public static boolean addTile(Tile t, Twin pos, Tile[][] tiles) {
//		if (pos.ix() >= 0 && pos.ix() < tiles.length && pos.iy() >= 0 || pos.iy() < tiles[pos.ix()].length) {
//			if (getTile(pos.ix(), pos.iy(), tiles) == null
//					|| getTile(pos.ix(), pos.iy(), tiles).getId().equals(t.getId())) {
//				tiles[pos.ix()][pos.iy()] = t;
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public static void draw(Graphics2D g, Twin pos, int offset0, int offset1, int offset2, int offset3,
//			BufferedImage image, int size, int layer) {
//		if (image == null) {
//			return;
//		}
//		int foo = (size + 1) / 2;
//		
//		g.drawImage(image.getSubimage(size * offset0, 0, foo, foo), pos.ix() * size, pos.iy() * size, null);
//		g.drawImage(image.getSubimage(size * offset1 + foo, 0, foo, foo), pos.ix() * size + size / 2, pos.iy() * size,
//				null);
//		g.drawImage(image.getSubimage(size * offset3, foo, foo, foo), pos.ix() * size, pos.iy() * size + size / 2,
//				null);
//		g.drawImage(image.getSubimage(size * offset2 + foo, foo, foo, foo), pos.ix() * size + size / 2,
//				pos.iy() * size + size / 2, null);
//
//	}
//
//	private void bar(boolean[] foo, int i, int j, int k, int l) {
//		if (!foo[i] && !foo[j]) {
//			offset[l] = 0;
//		}
//		if (foo[i] && !foo[j]) {
//			offset[l] = 1;
//		}
//		if (!foo[i] && foo[j]) {
//			offset[l] = 2;
//		}
//
//		if (foo[i] && foo[j]) {
//			if (foo[k]) {
//				// System.out.println(foo[k]+" "+k);
//				offset[l] = 4;
//			} else {
//				offset[l] = 3;
//			}
//		}
//	}

}
