package trinity;

import java.awt.Point;
import java.awt.event.KeyEvent;


import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Key {
	
	public static List<Key> keys = Collections.synchronizedList(new ArrayList<Key>());
	public static Twin mousePos = new Twin(0, 0);
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
		for (Key k : keys) {
			if (k.id == id) {
				return k;
			}
		}
		return null;
	}

}
