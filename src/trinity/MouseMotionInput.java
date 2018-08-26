package trinity;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionInput implements MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		Game.mouse=new Point(e.getX()/Render.sizeFactor, e.getY()/Render.sizeFactor);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Game.mouse=new Point(e.getX()/Render.sizeFactor, e.getY()/Render.sizeFactor);

	}

}
