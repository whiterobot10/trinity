package trinity;

import java.net.MalformedURLException;

public class CartridgeMenuItem extends MenuItem {

	String cartridge;

	public CartridgeMenuItem(Twin pos, Level level, String text) {
		super(pos, level, text);
		cartridge = text;
	}

	public CartridgeMenuItem(Twin twin, Level level, String text, String cartrage) {
		super(twin, level, text);
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
