package com.morkaz.morkazsk.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class ExprDateFromUnix extends SimpleExpression<Date> {

	Expression<?> number;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event arg0, boolean arg1) {
		return "last[ ]login of %offlinePlayer%";
	}

	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.number = expressions[0];
		return true;
	}

	protected Date[] get(Event event) {
		Long longNumber = ((Number)this.number.getSingle(event)).longValue();
		return new Date[]{new Date(longNumber)};
	}

}
