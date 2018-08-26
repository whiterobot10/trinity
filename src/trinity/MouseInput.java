package trinity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Key k : Game.keys) {
			if (k.mouse == e) {
				k.held = true;
				k.pressed = true;
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (Key k : Game.keys) {
			if (k.mouse == e) {
				k.held = false;
				k.released = true;
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
