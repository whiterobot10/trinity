package trinity;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuItem extends Entity{

	String text;
	public MenuItem(Twin pos, Level level, String text) {
		super(pos, level);
		
		this.text = text;
	}
	
	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 0) {
			g.setColor(Color.BLACK);
			Render.drawString(g, pos, text);
			
		}
		
	}
	
	public void drawSelected(Graphics2D g, int layer) {
		if (layer == 0) {
			g.setColor(Color.GREEN);
			Render.drawString(g, pos, text);
			
		}
		
	}
	
	public void onClick() {
	}
	


}
