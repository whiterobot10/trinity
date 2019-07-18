package trinity;

import java.net.MalformedURLException;

public class CartridgeMenuItem extends MenuItem {

	String cartridge;

	public CartridgeMenuItem(Twin pos, String text) {
		super(pos, text);
		cartridge = text;
	}

	public CartridgeMenuItem(Twin twin, String text, String cartrage) {
		super(twin, text);
		this.cartridge = cartrage;
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
