package stabby;

import java.util.Random;

import trinity.Entity;
import trinity.Level;
import trinity.Twin;

public class Battler extends Entity {

	float timer = 0;

	float speed = 0.1f;

	static Battler active = null;

	static Battler target = null;

	static Random r = new Random();

	static int animFrame;

	Twin home_pos;

	static Twin target_pos;

	int weight = 1;

	public Battler(Twin pos, Level level) {
		super(pos, level);
		home_pos = pos;
	}

	@Override
	public void update() {
		if (active == null) {
			timer += speed * (r.nextFloat() + 0.5f);
			if (timer >= 1) {
				timer--;
				active = this;
				animFrame = 0;
				if (this instanceof Ally) {
					target = SelectRandomBattler(Enemy.class);
					target_pos = new Twin(45, target.pos.y);
				} else {
					target = SelectRandomBattler(Ally.class);
					target_pos = new Twin(15, target.pos.y);
				}

			}
		}
		if (active == this) {
			animFrame++;
			int foo;
			if (animFrame > 30) {
				foo = Math.max(45 - animFrame, 0);
			} else {
				foo = Math.min(animFrame, 15);
			}
			pos = home_pos.getTwords(target_pos, (home_pos.distance(target_pos) / 15 * foo));
			if (animFrame > 50) {
				active = null;
			}
		}
	}

	public Battler SelectRandomBattler(Class c) {
		int foo = 0;
		for (Object o : level.objects) {
			if (o.getClass().equals(c)) {
				foo += ((Battler) o).weight;
			}
		}
		foo = r.nextInt(foo);
		for (Object o : level.objects) {
			if (o.getClass().equals(c)) {
				foo -= ((Battler) o).weight;
				if (foo <= 0) {
					return (Battler) o;
				}
			}
		}
		return null;
	}

}
