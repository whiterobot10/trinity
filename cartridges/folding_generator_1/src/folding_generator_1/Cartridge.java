package folding_generator_1;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import trinity.Game;
import trinity.Key;
import trinity.Level;
import trinity.Render;
import trinity.Twin;

public class Cartridge extends Level {

	public static boolean advanced = false;

	public static final String read = "BowTie.txt";
	public static final String linetypes = "lines.txt";

	static Color selectedColor;

	static ArrayList<Stroke> strokes = new ArrayList<Stroke>();

	static ArrayList<Color> colors = new ArrayList<Color>();

	public Cartridge() {
	}

	{
		clear();
		Key.resetKeys();
		Render.fixDisplay(new Dimension(2000, 2000));
		Game.currentName = "Folding Generator";

		Render.canvasLayers = 3;

		Scanner input = null;

		try {
			input = new Scanner(new File(System.getProperty("user.dir")+"/cartridges/folding_generator_1/" + read));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Level.levels.add(new Level());
		Level l = Level.levels.get(0);
		
		new ShowToggle(new Twin(0,0), l);

		Map<String, Twin> points = new HashMap<String, Twin>();
		Map<String, Line> lines = new HashMap<String, Line>();

		while (input.hasNext()) {
			String bar = input.next();

			if (bar.equals("point")) {
				Twin foo = new Twin();
				points.put(input.next(), foo);
				foo.x = input.nextFloat();
				foo.y = input.nextFloat();
			}
			if (bar.equals("line")) {
				String baz = input.next();
				Line foo = new Line(points.get(input.next()), points.get(input.next()), input.nextInt(), l);
				lines.put(baz, foo);
			}
			if (bar.equals("pointOnLine")) {
				String name = input.next();
				PointOnLine foo = new PointOnLine(lines.get(input.next()), input.nextFloat(), input.nextBoolean(), l);
				points.put(name, foo.pos);

			}
			if (bar.equals("reflection")) {
				String name = input.next();
				Line foo = new LineOverLine(lines.get(input.next()), lines.get(input.next()), input.nextInt(), l);
				lines.put(name, foo);
			}
			if (bar.equals("intersectPoint")) {
				String name = input.next();
				PointOnTwoLines foo = new PointOnTwoLines(lines.get(input.next()), lines.get(input.next()), l);
				points.put(name, foo.pos);
			}
		}

		try {
			input = new Scanner(new File(System.getProperty("user.dir")+"/cartridges/folding_generator_1/" + linetypes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		selectedColor = new Color(input.nextInt(), input.nextInt(), input.nextInt());
		input.nextLine();

		while (input.hasNextLine()) {
			Scanner input2 = new Scanner(input.nextLine());
			colors.add(new Color(input2.nextInt(), input2.nextInt(), input2.nextInt()));

			int width = input2.nextInt();
			int cap = input2.nextInt();
			int join = input2.nextInt();
			float[] foo = null;
			if (input2.hasNextFloat()) {
				foo = new float[1];
				foo[0] = input2.nextFloat();
				while (input2.hasNextFloat()) {
					float[] bar = new float[foo.length + 1];
					for (int i = 0; i < foo.length; i++) {
						bar[i] = foo[i];
					}
					bar[bar.length - 1] = input2.nextFloat();
					foo = bar;
				}
			}
			strokes.add(new BasicStroke(width, cap, join, 0, foo, 0));

			input2.close();
		}

		input.close();

//		Line foo = new VertLine(10);
//		newEntities.add(foo);
//		PointOnLine bar = new PointOnLine(foo, 0.5f);
//		newEntities.add(bar);
//		Line baz = new Line(new Twin(50, 50), bar.pos);
//		newEntities.add(baz);
//		newEntities.add(new PointOnLine(baz, 0.25f));
//
//		Line foobar = new Line(new Twin(50, 0), new Twin(55, 50));
//		newEntities.add(foobar);
//		LineOverLine foobaz = new LineOverLine(baz, foobar);
//		newEntities.add(foobaz);
//		// currentLevel.entities.add(new Line(foobar.pos, new Twin(50,50)));
//
//		Line barbaz = new Line(new Twin(70, 0), new Twin(80, 50));
//
//		newEntities.add(barbaz);
//
//		newEntities.add(new PointOnTwoLines(foobaz, barbaz));

		Key.keys.add(new Key(MouseEvent.BUTTON1, "click", true));
		Key.keys.add(new Key(KeyEvent.VK_UP, "up", false));
		Key.keys.add(new Key(KeyEvent.VK_DOWN, "down", false));
		Key.keys.add(new Key(KeyEvent.VK_SHIFT, "shift", false));
		Key.keys.add(new Key(KeyEvent.VK_SPACE, "toggle", false));

	}

}
