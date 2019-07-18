package trinity;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Key {

	public static List<Key> keys = Collections.synchronizedList(new ArrayList<Key>());
	public static Twin mousePos = new Twin(0, 0);
	public static Twin mousePosNoScroll = new Twin(0, 0);
	public static boolean reset = false;
	public boolean held;
	public boolean pressed;
	public boolean released;
	public int key;
	public int mouse;
	private String id;
	public boolean isMouse;

	public static void resetKeys() {
		if (reset) {
			Key.keys.clear();
			Key.keys.add(new Key(KeyEvent.VK_UP, "menu_up", false));
			Key.keys.add(new Key(KeyEvent.VK_DOWN, "menu_down", false));
			Key.keys.add(new Key(KeyEvent.VK_LEFT, "menu_left", false));
			Key.keys.add(new Key(KeyEvent.VK_RIGHT, "menu_right", false));
			Key.keys.add(new Key(KeyEvent.VK_ENTER, "menu_enter", false));
			reset = false;
		}
		for (Key k : keys) {
			k.pressed = false;
			k.released = false;
		}
	}

	public Key(int key, String id, boolean isMouse) {
		if (isMouse) {
			mouse = key;
			this.id = id;
		} else {
			this.key = key;
			this.id = id;
		}
		this.isMouse = isMouse;
	}

	public static Key getKey(String id) {
		for (Key k : keys) {
			if (k.id == id) {
				return k;
			}
		}
		return null;
	}

}
