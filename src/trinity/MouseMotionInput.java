package trinity;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionInput implements MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		Key.mousePos=new Twin(e.getX()/Render.sizeFactor-(float)Render.scroll.getTranslateX(), e.getY()/Render.sizeFactor-(float)Render.scroll.getTranslateY());

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Key.mousePos=new Twin(e.getX()/Render.sizeFactor-(float)Render.scroll.getTranslateX(), e.getY()/Render.sizeFactor-(float)Render.scroll.getTranslateY());

	}

}
