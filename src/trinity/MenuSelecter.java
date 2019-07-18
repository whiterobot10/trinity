package trinity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuSelecter extends Entity {
	public static BufferedImage image = Level.images.get("pointer");
	static int selectedItem = 0;
	static int lRJump = 4;
	static ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

	public MenuSelecter(Twin pos, Level l) {
		super(pos, l);
		menuItems.clear();
		for (Object o : l.objects) {
			if (o instanceof MenuItem) {
				menuItems.add((MenuItem) o);
			}
		}
	}

	@Override
	public void update(Level l) {
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
		if (layer == 0) {
			drawSegment(g, image, menuItems.get(selectedItem).pos, 0);
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
