package low_battery;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import trinity.Game;
import trinity.Key;
import trinity.Level;
import trinity.Render;
import trinity.Segment;
import trinity.Twin;

import low_battery.Bullet;

public class Player extends Pawn {

	int test = 0;

	float walkCycle = 0;

	static Segment head = new Segment(Level.images.get("pawn.player.head"), 1);
	static Segment body = new Segment(Level.images.get("pawn.player.body"), 1);

	static Segment crawlergun = new Segment(Level.images.get("crawler.weapon.0"), 4);
	{
		crawlergun.setRotatedImage(0, Level.images.get("crawler.weapon.0"), new Twin(0, 1));
		crawlergun.setRotatedImage(1, Level.images.get("crawler.weapon.1"), new Twin(-1, 1));
		crawlergun.setRotatedImage(2, Level.images.get("crawler.weapon.2"), new Twin(-1, 1));
		crawlergun.setRotatedImage(3, Level.images.get("crawler.weapon.3"), new Twin(-2, 0));
	}
	static Segment crawlerleg = new Segment(Level.images.get("crawler.leg"), 1);
	static Segment crawler = new Segment(Level.images.get("crawler.crawler"), 1);
	static Segment crawlerhover = new Segment(Level.images.get("crawler.hover"), 1);

	static Segment loud_stick = new Segment(Level.images.get("loud_stick.0"), 4);
	{

		loud_stick.setRotatedImage(0, Level.images.get("loud_stick.0"), new Twin(-1, -3));
		loud_stick.setRotatedImage(1, Level.images.get("loud_stick.0"), new Twin(0, -3));
		loud_stick.setRotatedImage(2, Level.images.get("loud_stick.1"), new Twin(1, -3));
		loud_stick.setRotatedImage(3, Level.images.get("loud_stick.2"), new Twin(3, -2));

	}
	static Segment front_arm = new Segment(Level.images.get("pawn.player.arm.0"), 4);
	{

		front_arm.setRotatedImage(0, Level.images.get("pawn.player.arm.0"), new Twin(0, -1));
		front_arm.setRotatedImage(1, Level.images.get("pawn.player.arm.1"), new Twin(0, -1));
		front_arm.setRotatedImage(2, Level.images.get("pawn.player.arm.2"), new Twin(0, -1));
		front_arm.setRotatedImage(3, Level.images.get("pawn.player.arm.3"), new Twin(1, -1));
	}
	static Segment back_arm = new Segment(Level.images.get("pawn.player.arm.4"), 4);
	{
		back_arm.setRotatedImage(0, Level.images.get("pawn.player.arm.4"), new Twin(0, -1));
		back_arm.setRotatedImage(1, Level.images.get("pawn.player.arm.5"), new Twin(0, -1));
		back_arm.setRotatedImage(2, Level.images.get("pawn.player.arm.6"), new Twin(0, -1));
		back_arm.setRotatedImage(3, Level.images.get("pawn.player.arm.7"), new Twin(1, -1));
	}
	static Segment front_leg = new Segment(Level.images.get("pawn.player.leg.0"), 1);
	static Segment back_leg = new Segment(Level.images.get("pawn.player.leg.1"), 1);

	// static BufferedImage[] leg2 = new BufferedImage[2];

	int i = 0;

	public Player(Twin pos, Level level) {
		super(pos, level, 0, 0, 1f, 0);

	}

	@Override
	public void update() {

		if (pos.x + Render.scroll.getTranslateX() > (Render.getGameSize().x - Render.scrollOffset.x)) {
			Render.scroll.translate(
					(Render.getGameSize().x - Render.scrollOffset.x) - (pos.x + Render.scroll.getTranslateX()), 0);
		}
		if (pos.x + Render.scroll.getTranslateX() < Render.scrollOffset.x) {
			Render.scroll.translate((Render.scrollOffset.x) - (pos.x + Render.scroll.getTranslateX()), 0);
		}

		if (pos.y + Render.scroll.getTranslateY() > (Render.getGameSize().y - Render.scrollOffset.y)) {
			Render.scroll.translate(0,
					(Render.getGameSize().y - Render.scrollOffset.y) - (pos.y + Render.scroll.getTranslateY()));
		}
		if (pos.y + Render.scroll.getTranslateY() < Render.scrollOffset.y) {
			Render.scroll.translate(0, (Render.scrollOffset.y) - (pos.y + Render.scroll.getTranslateY()));
		}

		if (Key.getKey("up").held || Key.getKey("up_2").held) {
			target.y -= 30;
		}
		if (Key.getKey("down").held || Key.getKey("down_2").held) {
			target.y += 30;
		}
		if (Key.getKey("left").held || Key.getKey("left_2").held) {
			target.x -= 30;
		}
		if (Key.getKey("right").held || Key.getKey("right_2").held) {
			target.x += 30;
		}

		vel = pos.getTwordsAmount(target, Math.min(speed.value, 3f));
		move(vel);

		if (Key.getKey("click").pressed) {
//			System.out.println("f");
//			System.out.println();
//			Twin foo = pos.getTwordsAmount(Key.mousePos, 1f);
//			System.out.println(foo);
//			System.out.println(foo.offset(vel));
			// System.out.println(vel.fix());
			new Bullet(pos.getTwords(Key.mousePos, 10), pos.getTwordsAmount(Key.mousePos, 3.0f), level);
			// System.out.println(pos.getTwords(Key.mousePos, 2).x + " " +
			// pos.getTwords(Key.mousePos, 2).y);
			// System.out.println(pos.x + " " + pos.y);
			// System.out.println(pos.getTwordsAmount(Key.mousePos, 1));
		}

		if (pos.distance(target) > 1) {

			walkCycle += speed.value;
		} else {
			walkCycle = 0;
		}
		while (walkCycle > 14) {
			walkCycle -= 14;
		}

		target.x = pos.x;
		target.y = pos.y;

		speed.update();
		maxHealth.update();
		maxPower.update();
		loot.update();

	}

	@Override
	public void draw(Graphics2D g, int layer) {
		super.draw(g, layer);

		if (layer == 3) {

//			if (walkCycle == 0) {
//				drawSegment(g, leg[0], new Twin(-1, 2), 0);
//				drawSegment(g, leg[1], new Twin(2, 2), 0);
//			} else if (walkCycle > 7) {
//				drawSegment(g, leg[0], new Twin(-1, 2), 0);
//				drawSegment(g, leg[1], new Twin(2, 1), 0);
//			} else {
//				drawSegment(g, leg[0], new Twin(-1, 1), 0);
//				drawSegment(g, leg[1], new Twin(2, 2), 0);
//			}
			scale.x = Key.mousePos.x < pos.x ? -1 : 1;
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

			crawlergun.Draw(g, pos, new Twin(0, -13), new Twin(1, 1),
					360 + (int) Math.round(pos.getRot(Key.mousePos) / crawlergun.degreesPerRot));

			crawlerleg.Draw(g, pos, new Twin(0, -15), new Twin(1, 1), 1);

			crawlerhover.Draw(g, pos, new Twin(0, -15), scale, 0);

			crawler.Draw(g, pos, new Twin(0, -17), scale, 0);

			front_leg.Draw(g, pos, new Twin(-1, walkCycle > 7 ? 2 : 3), scale, 0);
			back_leg.Draw(g, pos, new Twin(2, (walkCycle < 7 && walkCycle > 0) ? 2 : 3), scale, 0);

			int foo = pos.rotBreak(Key.mousePos, 16, 0f);
			if (foo > 8) {
				foo = Math.abs(foo - 16);
			}

			g.setColor(Color.red);
			// g.drawLine(pos.ix(), pos.iy() - 10, pos.ix(), pos.iy() + 10);

			back_arm.Draw(g, pos, new Twin(3, 0), scale,
					(int) Math.round(Math.abs(pos.getRot(Key.mousePos)) / back_arm.degreesPerRot));
			// arm[(foo % 4) + 4].Draw(g, pos, new Twin(3, 0), left, (foo / 4) * 90);
			// System.out.println(pos.rotBreak(Key.mousePos, 16, 0f)*Math.PI*2);
			// drawSegment(g, arm[Math.abs(pos.rotBreak(Key.mousePos, 16, 0f)) % 4 + 4], new
			// Twin(3, -1),
			// pos.rotBreak(Key.mousePos, 16, 0f) / 4);
			body.Draw(g, pos, new Twin(0, 0), scale, 0);

			head.Draw(g, pos, new Twin(0, -4), scale, 0);
			// Math.PI/100*i++);
			// drawSegment(g, head, new Twin(0, -5), 0);
			// arm[foo % 4].Draw(g, pos, new Twin(-2, 0), left, (foo / 4) * 90);
			// (foo/4.0)*90);
			front_arm.Draw(g, pos, new Twin(-2, 0), scale,
					(int) Math.round(Math.abs(pos.getRot(Key.mousePos)) / front_arm.degreesPerRot));
			loud_stick.Draw(g, pos, new Twin(-2, 0), scale,
					(int) Math.round(Math.abs(pos.getRot(Key.mousePos)) / loud_stick.degreesPerRot));

//			crawler.Draw(g, pos, new Twin(0,15), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+1);
//			crawler.Draw(g, pos, new Twin(0,14), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+0);
//			crawler.Draw(g, pos, new Twin(0,13), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+0);
//			crawler.Draw(g, pos, new Twin(0,12), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+1);
//			crawler.Draw(g, pos, new Twin(0,11), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+2);
//			crawler.Draw(g, pos, new Twin(0,10), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+3);
//			
//			crawler.Draw(g, pos, new Twin(0,20), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+0);
//			
//			crawler.Draw(g, pos, new Twin(0,30), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+1);
//			
//			crawler.Draw(g, pos, new Twin(0,40), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+2);
//
//			crawler.Draw(g, pos, new Twin(0,50), scale, (int) Math.abs(pos.getRot(Key.mousePos)/90)*4+3);

			// System.out.println(loud_stick.degreesPerRot);

			// drawSegment(g, arm[Math.abs(pos.rotBreak(Key.mousePos, 16, 0f)) % 4], new
			// Twin(-2, -1),
			// pos.rotBreak(Key.mousePos, 16, 0f) / 4);

			// Render.DrawChain(g, laser, pos, pos.ix()/10, 0, pos.getRot(Key.mousePos));

			// drawSegment(g, Render.tile(laser.getImage(), 1, 10), new Twin(0,10), 0);

			for (int i = 0; i < 50; i++) {
				// Render.DrawChain(g, laser, pos, Key.mousePos, 1, 0);
			}

			g.setColor(Color.red);

			g.drawString(Render.scroll.getTranslateX() + "", (int) (10 - Render.scroll.getTranslateX()), 70);

			g.drawString(health + "", 10, 90);
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			g.drawString(health + "", 10, 100);
			if (Game.debug) {
				g.draw(hitbox());
			}

		}
	}

	@Override
	public Shape hitbox() {
		return new Ellipse2D.Double(pos.x - 4, pos.y + 2, 8, 4);
	}

}
