package folding_generator_1;
import trinity.Level;
import trinity.Render;
import trinity.Twin;

public class VertLine extends Line {
	
	public VertLine(float x, Level l) {
		super(new Twin(x, 0), new Twin(x, Render.getGameSize().iy()), l);
	}
	public VertLine(float x, int draw, Level l) {
		super(new Twin(x, 0), new Twin(x, Render.getGameSize().iy()), draw, l);
	}

}
