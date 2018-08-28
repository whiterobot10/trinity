package trinity;

public class Twin {
	public float x = 0;
	public float y = 0;

	public Twin() {

	}

	public Twin(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Twin move(float x, float y) {
		return move(x, y, true);
	}

	public Twin move(float x, float y, boolean shift) {
		if (shift) {
			this.x += x;
			this.y += y;
			return this;
		} else {
			return new Twin(this.x + x, this.y + y);
		}
	}

	public float distance(Twin pos) {
		return (float) Math.sqrt(Math.pow(pos.x + this.x, 2) + Math.pow(pos.y + this.y, 2));
	}
}
