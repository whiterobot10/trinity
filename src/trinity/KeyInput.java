package trinity;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyInput implements KeyListener{
	

	public static void reset() {
		for (Key k : Game.keys) {
			k.pressed = false;
			k.released = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("KeyPress");
		for (Key k : Game.keys) {
			if (k.key == e) {
				k.held = true;
				k.pressed = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Key k : Game.keys) {
			if (k.key == e) {
				k.held = false;
				k.released = true;
			}
		}
	}

}
