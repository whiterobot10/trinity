package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuSelecter extends Entity {
	public static BufferedImage image = Level.images.get("pointer");
	static int selectedItem = 0;
	static int lRJump = 4;
	static ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

	public MenuSelecter(Float pos) {
		super(pos);
		menuItems.clear();
		for (Entity e : Level.currentLevel.entities) {
			menuItems.add((MenuItem) e);
			if (menuItems.get(menuItems.size() - 1) == null) {

			}
		}
	}

	@Override
	public void update() {
		if (!Key.keys.isEmpty()) {
			if (Key.getKey("menu_down").pressed) {
				selectedItem++;
				if (selectedItem >= menuItems.size()) {
					selectedItem = 0;
				}
			}
			if (Key.getKey("menu_up").pressed) {
				selectedItem--;
				if (selectedItem < 0) {
					selectedItem = menuItems.size() - 1;
				}
			}
			if (Key.getKey("menu_right").pressed) {
				selectedItem += lRJump;
				if (selectedItem >= menuItems.size()) {
					selectedItem -= menuItems.size() - 1;
					if (selectedItem == lRJump) {
						selectedItem = 0;
					}
				}
			}
			if (Key.getKey("menu_left").pressed) {
				selectedItem -= lRJump;
				if (selectedItem < 0) {
					selectedItem += menuItems.size() - 1;
					if (selectedItem == lRJump) {
						selectedItem = menuItems.size() - 1;
					}
				}
			}
			if (Key.getKey("menu_enter").pressed) {
				menuItems.get(selectedItem).onClick();
			}
		}
	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == this.layer) {
			drawSegment(g, image, menuItems.get(selectedItem).pos);
			if (Game.debug) {
				g.setColor(Color.red);
				Rectangle foo = hitbox.getBounds();
				g.drawRect(foo.x, foo.y, foo.width, foo.height);
				g.setColor(Color.red);
				g.fillRect((int) pos.x - 1, (int) pos.y - 1, 2, 2);
			}
		}
	}
}
