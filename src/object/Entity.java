package object;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.Game;
import render.Render;

public class Entity {

	int layer;
	Point2D.Float pos;
	Point2D.Float vel;
	boolean solid = true;
	
	public static BufferedImage image = null;

	public Entity(Point2D.Float pos, int layer) {
		this.pos = pos;
		this.layer = layer;
		try {
			image = Render.loadImage("dummy.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Entity(Point2D.Float pos, int layer, boolean solid) {
		this.pos = pos;
		this.layer = layer;
		this.solid = solid;
		try {
			image = Render.loadImage("dummy.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

	}
	public void damage(float amount) {
		damage(amount, null);	
	}
	
	public void damage(float amount, String type) {
		
	}

	public void draw(Graphics g, int Layer) {
		Render.drawImage(g, image, pos, true);
		g.fillRect((int)pos.x-1, (int)pos.y-1, 2, 2);
	}

}
