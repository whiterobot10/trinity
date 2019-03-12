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
		this.x+=x;
		this.y+=y;
		return new Twin(this);
	}

	public Twin move(Twin move) {
		return move(move.x, move.y);
	}

	public Twin offset(Twin move) {
		return offset(move.x, move.y);
	}

	public Twin offset(float x, float y) {
			return new Twin(this.x + x, this.y + y);
		
	}
	
	public double getRot(Twin target) {
		return Math.toDegrees(Math.atan2(target.x - x, y - target.y));
	}

	public int rotBreak(Twin target, int sections, float offsetPercent) {
		double targetRot = this.getRot(target);
		if (targetRot<0) {
			targetRot+=360;
		}
		targetRot += offsetPercent * (360.0 / sections);
		int foo = (int) (Math.round(targetRot / (360.0 / sections)));
		//System.out.println((360.0 / sections)+" "+targetRot+" "+(targetRot / (360.0 / sections)));
	
//		if ((sections / 2) * 2 == sections && foo == sections / -2) {
//			foo *= -1;
//		}
		return foo;
	}

	public float distance(Twin pos) {
		float px = pos.x - x;
		float py = pos.y - y;
		return (float) Math.sqrt(px * px + py * py);
	}

	public Twin getTwords(Twin shift, float i) {
		float foo = distance(shift);
		return new Twin(x + ((shift.x - x) / foo)*i, y + ((shift.y - y) / foo)*i);
	}
	public Twin getTwordsAmount(Twin shift, float i) {
		float foo = distance(shift) ;
		return new Twin( ((shift.x - x) / foo)*i,  ((shift.y - y) / foo)*i);
	}
	
	@Override
	public String toString() {
		return x+" "+y;
	}

	public Twin unit() {
		float foo = (float)(Math.pow(x, 2)+Math.pow(y, 2));
		
		return new Twin(x/foo, y/foo);
	}

	public double getRot() {
	
		return Math.toDegrees(Math.atan2(x, y));
	}

	public Twin fix() {
		if(!(x>0)&&!(x==0)&&!(x<0)) {
			x=0;
		}
		if(!(y>0)&&!(y==0)&&!(y<0)) {
			y=0;
		}
		return this;
	}
}
