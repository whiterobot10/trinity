package trinity;

import java.awt.Graphics2D;
import java.net.MalformedURLException;

public class CartridgeMenuItem extends MenuItem {

	static Segment cartridgeImg = new Segment(Level.images.get("cartridge"), 1);
	static Segment cartridgeSelected = new Segment(Level.images.get("pointer"), 1);
	Segment iconImg;
	String cartridge;

	public CartridgeMenuItem(Twin pos, Level level, String text) {
		super(pos, level, text);
		cartridge = text;
		iconImg = new Segment(Level.images.get(text), 1);
	}

	@Override
	public void draw(Graphics2D g, int layer) {
		if (layer == 0) {

			cartridgeImg.Draw(g, pos, null, null, 0);
			iconImg.Draw(g, pos, null, null, 0);

		}

	}
	
	@Override
	public void drawSelected(Graphics2D g, int layer) {
		if (layer == 0) {

			cartridgeSelected.Draw(g, pos.offset(0, -40), null, null, 1);

		}

	}

	@Override
	public void onClick() {
		try {
			Game.getThing(cartridge + ".Cartridge",
					System.getProperty("user.dir") + "/cartridges/" + cartridge + "/bin/");
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
