package folding_generator_1;
import java.awt.Graphics2D;

import trinity.Entity;
import trinity.Gui;
import trinity.Key;
import trinity.Level;
import trinity.Twin;

public class ShowToggle extends Gui {


	public ShowToggle(Twin pos, Level level) {
		super(pos, level);
	}

	@Override
	public void update() {
		if (Key.getKey("toggle").pressed) {
			Cartridge.advanced = !Cartridge.advanced;
		}
	}

	public void draw(Graphics2D g, int layer) {
	}

}
