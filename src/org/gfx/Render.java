package org.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.engine.Game;

public class Render {

	private static Frame frame;
	private static Canvas canvas;

	private static Dimension canvasSize;

	public static void init() {

		canvasSize = new Dimension(100, 100);

		frame = new Frame();
		canvas = new Canvas();
		
		canvas.setPreferredSize(canvasSize);
		
		frame.add(canvas);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				Game.quit();
				System.exit(0);
			}
		});
		
		frame.setVisible(true);

	}

}
