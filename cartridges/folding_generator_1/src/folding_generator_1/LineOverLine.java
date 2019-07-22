package folding_generator_1;

import trinity.Level;

public class LineOverLine extends Line {

	private Line toReflect;
	private Line reflectOver;

	public LineOverLine(Line toReflect, Line reflectOver, Level l) {

		super(reflectOver.reflectPoint(toReflect.pos), reflectOver.reflectPoint(toReflect.endPoint), l);
		this.toReflect = toReflect;
		this.reflectOver = reflectOver;
	}

	public LineOverLine(Line toReflect, Line reflectOver, int draw, Level l) {

		super(reflectOver.reflectPoint(toReflect.pos), reflectOver.reflectPoint(toReflect.endPoint), draw, l);
		this.toReflect = toReflect;
		this.reflectOver = reflectOver;
	}

	@Override
	public void update() {
		pos = reflectOver.reflectPoint(toReflect.pos);
		endPoint = reflectOver.reflectPoint(toReflect.endPoint);
	}

}
