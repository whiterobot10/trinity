package folding_generator_1;
import java.awt.Graphics2D;

import trinity.Object;
import trinity.Level;
import trinity.Render;
import trinity.Twin;

public class Line extends Object {

	public Twin endPoint;
	public int draw;

	public Line(Twin start, Twin end, Level l) {
		super(start, l);
		endPoint = end;
		draw = 0;
	}

	public Line(Twin start, Twin end, int draw, Level l) {
		super(start, l);
		endPoint = end;
		this.draw = draw;
	}

	public Twin findPointGivenOffset(float offset) {
		return pos.getTwords(endPoint, offset * pos.distance(endPoint));
	}

	public float lenght() {
		return pos.distance(endPoint);
	}

	public float slope() {
		return (pos.y - endPoint.y) / (pos.x - endPoint.x);
	}

	public float intercept() {
		return pos.y - (slope() * pos.x);
	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 1 && !Cartridge.advanced) {
			g.setColor(Cartridge.colors.get(1));
			g.setStroke(Cartridge.strokes.get(1));
			g.drawLine(pos.ix(), pos.iy(), endPoint.ix(), endPoint.iy());
		}
		if (layer == 0 && (slope() > 0 || slope() <= 0) && !Cartridge.advanced) {
			g.setColor(Cartridge.colors.get(0));
			g.setStroke(Cartridge.strokes.get(0));
			g.drawLine(0, (int) intercept(), Render.getGameSize().ix(),
					(int) (Render.getGameSize().ix() * slope() + intercept()));

		}
		if (layer == 2 && Cartridge.advanced && draw > 0) {
			int foo = (draw/3)%(Cartridge.colors.size()-1)+2;
			g.setColor(Cartridge.colors.get(foo));
			g.setStroke(Cartridge.strokes.get(foo));

			if (draw % 3 == 1) {
				g.drawLine(pos.ix(), pos.iy(), endPoint.ix(), endPoint.iy());
			} else if (draw % 3 == 2) {
				if (pos.x == endPoint.x) {
					g.drawLine(pos.ix(), 0, pos.ix(), Render.getGameSize().iy());
				} else {
					g.drawLine(0, (int) intercept(), Render.getGameSize().ix(),
							(int) (Render.getGameSize().ix() * slope() + intercept()));
				}
			}

		}
	}

	public Twin reflectPoint(Twin point) {

		if (pos.x == endPoint.x) {
			return new Twin(pos.x + (pos.x - point.x), point.y);
		} else if (pos.y == endPoint.y) {
			return new Twin(point.x, pos.y + (pos.y - point.y));
		} else {
			
			float slope2 = -1/slope();
			float intercept2 = point.y-(point.x*slope2);
			
			float foox = (intercept() - intercept2) / (slope2 - slope());
			float fooy = (slope2 * foox + intercept2);
			
			
			return new Twin(foox-(point.x-foox), fooy-(point.y-fooy));
		}
	}

}
