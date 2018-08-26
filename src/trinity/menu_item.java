package trinity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Float;

public class menu_item extends Entity{

	String text;
	public menu_item(Float pos, String text) {
		super(pos);
		this.text = text;
	}
	
	public void draw(Graphics g, int layer) {
		if (layer == this.layer) {
			Render.drawString(g, pos, text);
			
		}
		
	}


}
