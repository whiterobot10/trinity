package trinity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{
	

	public static void reset() {
		for (Key k : Game.keys) {
			k.pressed = false;
			k.released = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Key k : Game.keys) {
			if (k.key == e.getKeyCode()&&!k.isMouse) {
				k.held = true;
				k.pressed = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Key k : Game.keys) {
			if (k.key == e.getKeyCode()&&!k.isMouse) {
				k.held = false;
				k.released = true;
			}
		}
	}

}
