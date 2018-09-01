package trinity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class MenuItem extends Entity{

	String text;
	public MenuItem(Twin pos, String text) {
		super(pos);
		this.text = text;
	}
	
	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 0) {
			Render.drawString(g, pos, text);
			
		}
		
	}
	
	public void onClick() {
	}
	


}
