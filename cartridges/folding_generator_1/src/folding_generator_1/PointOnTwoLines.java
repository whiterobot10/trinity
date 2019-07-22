package folding_generator_1;
import java.awt.Graphics2D;

import trinity.Level;
import trinity.Object;
import trinity.Twin;

public class PointOnTwoLines extends Object {

	Line line1;
	Line line2;

	public PointOnTwoLines(Line line1, Line line2, Level l) {
		super(new Twin(0,0), l);
		if (line1.pos.x == line1.endPoint.x) {
			this.line2 = line1;
			this.line1 = line2;
		} else {
			this.line1 = line1;
			this.line2 = line2;
		}
	}

	public void draw(Graphics2D g, int layer) {
		if (layer == 1&&!Cartridge.advanced) {
			g.setColor(Cartridge.colors.get(0));
			g.setStroke(Cartridge.strokes.get(0));
			g.drawOval(pos.ix() - 20, pos.iy() - 20, 40, 40);
		}
	}

	@Override
	public void update() {
		if (line2.pos.x == line2.endPoint.x) {
			pos.x=line2.pos.x;
			pos.y=line1.intercept()+line1.slope()*pos.x;
		} else {
			pos.x = (line1.intercept() - line2.intercept()) / (line2.slope() - line1.slope());
			pos.y = (line1.slope() * pos.x + line1.intercept());
		}
	}

}
