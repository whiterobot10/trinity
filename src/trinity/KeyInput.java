package trinity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{
	

	

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Key k : Key.keys) {
			if (k.key == e.getKeyCode()&&!k.isMouse) {
				k.held = true;
				k.pressed = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Key k : Key.keys) {
			if (k.key == e.getKeyCode()&&!k.isMouse) {
				k.held = false;
				k.released = true;
			}
		}
	}

}
