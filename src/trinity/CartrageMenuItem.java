package trinity;

import java.awt.geom.Point2D.Float;
import java.net.MalformedURLException;

public class CartrageMenuItem extends MenuItem {
	
	String cartrage;

	public CartrageMenuItem(Float pos, String text) {
		super(pos, text);
		cartrage=text;
	}
	public CartrageMenuItem(Float pos, String text, String cartrage) {
		super(pos, text);
		this.cartrage=cartrage;
	}
	@Override
	public void onClick() {
		try {
			Game.getThing("Cartrage", "/Users/josh/eclipse-workspace/"+cartrage+"/bin");
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
