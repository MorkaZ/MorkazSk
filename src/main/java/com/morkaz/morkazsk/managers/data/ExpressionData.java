package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.lang.ExpressionType;

public class ExpressionData<T> {

	private Class<T> expressionClass;
	private Class<?> returnValueClass;
	private ExpressionType expressionType;
	private String[] patterns;

	public ExpressionData(Class<T> expressionClass, Class<?> returnValueClass, ExpressionType expressionType, String... patterns) {
		this.expressionClass = expressionClass;
		this.returnValueClass = returnValueClass;
		this.expressionType = expressionType;
		this.patterns = patterns;
	}

	public Class<T> getExpressionClass() {
		return expressionClass;
	}

	public Class<?> getReturnValueClass() {
		return returnValueClass;
	}

	public ExpressionType getExpressionType() {
		return expressionType;
	}

	public String[] getPatterns() {
		return patterns;
	}

}
