package trinity;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Key {
	public boolean held;
	public boolean pressed;
	public boolean released;
	public int key;
	public int mouse;
	private String id;
	public boolean isMouse;

	public Key(int key, String id, boolean isMouse) {
		if (isMouse) {
			mouse = key;
			this.id = id;
		} else {
			this.key = key;
			this.id = id;
		}
		this.isMouse=isMouse;
	}


	public static Key getKey(String id) {
		for (Key k : Game.keys) {
			if (k.id == id) {
				return k;
			}
		}
		return null;
	}

}
