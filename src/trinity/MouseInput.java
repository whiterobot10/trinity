package trinity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Press");
		for (Key k : Game.keys) {
			if (k.mouse == e) {
				k.held = true;
				k.pressed = true;
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Release");
		for (Key k : Game.keys) {
			if (k.mouse == e) {
				k.held = false;
				k.released = true;
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Enter");
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Exit");
		// TODO Auto-generated method stub

	}

}
