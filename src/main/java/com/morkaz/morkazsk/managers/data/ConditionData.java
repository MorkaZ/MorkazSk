package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.lang.Condition;

public class ConditionData {

	private Class<? extends Condition> conditionClass;
	private String[] patterns;

	public ConditionData(Class<? extends Condition> conditionClass, String... patterns) {
		this.conditionClass = conditionClass;
		this.patterns = patterns;
	}

	public Class<? extends Condition> getConditionClass() {
		return conditionClass;
	}

	public String[] getPatterns() {
		return patterns;
	}
}
