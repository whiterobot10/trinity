package trinity;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

import javafx.scene.shape.Line;

public class Twin {
	public float x = 0;
	public float y = 0;

	public Twin() {

	}

	public Twin(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Twin(Point2D.Float point) {
		x = point.x;
		y = point.y;
	}

	public Twin(Point2D.Double point) {
		x = (float) point.x;
		y = (float) point.y;
	}

	public Twin(Dimension point) {
		x = (float) point.width;
		y = (float) point.height;
	}

	public Twin(Point point) {
		x = point.x;
		y = point.y;
	}

	public Twin(Twin point) {
		x = point.x;
		y = point.y;
	}

	public int ix() {
		return (int) x;
	}

	public int iy() {
		return (int) y;
	}

	public Twin move(float x, float y) {
		return move(x, y, true);
	}

	public Twin move(Twin move) {
		return move(move.x, move.y, true);
	}

	public Twin move(Twin move, boolean shift) {
		return move(move.x, move.y, shift);
	}

	public Twin move(float x, float y, boolean shift) {
		if (shift) {
			this.x += x;
			this.y += y;
			return new Twin(this);
		} else {
			return new Twin(this.x + x, this.y + y);
		}
	}

	public int rotBreak(Twin target, int sections, float offsetPercent) {
		double targetRot = Math.toDegrees(Math.atan2(target.x - x, y - target.y));
		targetRot += offsetPercent * (360.0 / sections);
		int foo = (int) (Math.round(targetRot / (360.0 / sections)));
		if ((sections / 2) * 2 == sections && foo == sections / -2) {
			foo *= -1;
		}
		return foo;
	}

	public float distance(Twin pos) {
		float px = pos.x - x;
		float py = pos.y - y;
		return (float) Math.sqrt(px * px + py * py);
	}

	public Twin getTwords(Twin shift, float i) {
		float foo = distance(shift) / i;
		return new Twin(x + ((shift.x - x) / foo), y + ((shift.y - y) / foo));
	}
	public Twin getTwordsAmount(Twin shift, float i) {
		float foo = distance(shift) / i;
		return new Twin((shift.x - x) / foo, y + (shift.y - y) / foo);
	}
}
