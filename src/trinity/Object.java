package trinity;

import java.awt.Graphics2D;
import java.awt.Shape;

public class Object {
	
	public Twin pos;
	public boolean remove = false;
	public Level level;
	
	public Object(Twin pos, Level level) {
		this.pos=pos;
		this.level=level;
		for(Level l: Level.levels) {
			if(l==level) {
				l.newObjects.add(this);
			}
		}
	}
	
	public void draw(Graphics2D g, int layer) {
		
	}
	
	public void update(Level l) {

	}
	
	public Shape hitbox() {
		return null;
	}

}
