package trinity;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Key {
	public boolean held;
	public boolean pressed;
	public boolean released;
	public KeyEvent key;
	public MouseEvent mouse;

	public Key(KeyEvent key) {
		this.key = key;
	}

	public Key(MouseEvent mouse) {
		this.mouse = mouse;
	}

}
