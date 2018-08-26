package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

public class MenuItem extends Entity{

	String text;
	public MenuItem(Point2D.Float pos, String text) {
		super(pos);
		this.text = text;
	}
	
	public void draw(Graphics g, int layer) {
		if (layer == this.layer) {
			Render.drawString(g, pos, text);
			
		}
		
	}


}