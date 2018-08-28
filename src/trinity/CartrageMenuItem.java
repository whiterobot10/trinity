package trinity;

import java.awt.geom.Point2D.Float;
import java.net.MalformedURLException;

public class CartrageMenuItem extends MenuItem {

	String cartrage;

	public CartrageMenuItem(Twin pos, String text) {
		super(pos, text);
		cartrage = text;
	}

	public CartrageMenuItem(Twin twin, String text, String cartrage) {
		super(twin, text);
		this.cartrage = cartrage;
	}

	@Override
	public void onClick() {
		try {
			Game.getThing("Cartridge", "/Users/josh/eclipse-workspace/" + cartrage + "/bin/" );
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
