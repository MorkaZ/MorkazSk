package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.lang.Effect;

public class EffectData {

	private Class<? extends Effect> effectClass;
	String[] patterns;

	public EffectData(Class<? extends Effect> effectClass, String... patterns) {
		this.effectClass = effectClass;
		this.patterns = patterns;
	}

	public Class<? extends Effect> getEffectClass() {
		return effectClass;
	}

	public String[] getPatterns() {
		return patterns;
	}

}
