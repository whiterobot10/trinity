package trinity;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionInput implements MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		Key.mousePos=new Point(e.getX()/Render.sizeFactor, e.getY()/Render.sizeFactor);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Key.mousePos=new Point(e.getX()/Render.sizeFactor, e.getY()/Render.sizeFactor);

	}

}
