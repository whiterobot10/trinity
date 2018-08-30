package trinity;

import java.util.ArrayList;
import java.util.Map;

public class Stat {

	float base = 0;
	float max = -1;
	public float value = 0;

	ArrayList<StatMod> mods = new ArrayList<StatMod>();

	public Stat() {
	}

	public Stat(float base) {
		this.base = base;
		value = base;
		System.out.println(value + " test");
	}

	public Stat(float base, float max) {
		this.base = base;
		value = base;
		this.max = max;
	}

	public void addMod(StatMod m) {
		mods.add(m);
	}

	public void update() {
		for (int i = 0; i < mods.size(); i++) {
			if (mods.get(i).decay()) {
				mods.remove(i);
				i--;
			}
		}
		value = base;
		float valueMult = 1;
		for (StatMod m : mods) {
			if (m.mult) {
				valueMult += m.mod;
			} else {
				value += m.mod;
			}

		}
		// value *= valueMult;
		value = base;

	}

//	public float getValue() {
//		float returnValue = base;
//		float returnValueMult = 0;
//		for (StatMod m : mods) {
//			if (m.mult) {
//				returnValueMult += m.mod;
//			} else {
//				returnValue += m.mod;
//			}
//
//		}
//		return returnValue * returnValueMult;
//
//	}

}
