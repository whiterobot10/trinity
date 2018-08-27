package trinity;

public class StatMod {
	public float mod;
	public boolean mult = false;
	int decayTime = -1;
	
	public StatMod() {
		mod=0;
	}
	public StatMod(float mod) {
		this.mod=mod;
	}
	
	public StatMod(float mod, boolean mult) {
		this.mod=mod;
		this.mult=mult;
	}
	
	public void remove() {
		mod=0;
	}
	
	public boolean decay() {
		if (decayTime < 0) {
			decayTime--;
		}
		return !(decayTime < 0);
	}

}
