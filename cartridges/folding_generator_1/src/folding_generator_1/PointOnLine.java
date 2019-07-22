package folding_generator_1;
import java.awt.Graphics2D;

import trinity.Entity;
import trinity.Key;
import trinity.Level;

public class PointOnLine extends Entity {

	private Line parent;
	private float decimal;
	public boolean selected = false;

	boolean canGoOverLine;

	public PointOnLine(Line line, float decimal, boolean canGoOverLine, Level level) {
		super(line.findPointGivenOffset(decimal), level);
		parent = line;
		this.decimal = decimal;
		this.canGoOverLine = canGoOverLine;

	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 1 && !Cartridge.advanced) {
			g.setColor(selected ? Cartridge.selectedColor : Cartridge.colors.get(1));
			g.setStroke(Cartridge.strokes.get(1));
			g.drawOval(pos.ix() - 20, pos.iy() - 20, 40, 40);
		}
	}

	@Override
	public void update() {
		if (Key.getKey("click").pressed) {
			if (pos.distance(Key.mousePosNoScroll) <= 30) {
				selected = true;
			} else if (!Key.getKey("shift").held) {
				selected = false;
			}

		}
		if (selected && Key.getKey("up").held) {
			decimal -= 0.01;
		}

		if (selected && Key.getKey("down").held) {
			decimal += 0.01;
		}

		if (!canGoOverLine) {
			decimal = Math.max(decimal, 0);
			decimal = Math.min(decimal, 1);
		}
		pos.x = parent.findPointGivenOffset(decimal).x;
		pos.y = parent.findPointGivenOffset(decimal).y;
	}

}
