package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Date from Unix Time")
@Description({
		"Return skript's date object from unix time number."
})
@Examples({
		"date from unix {_long.number}"
})
@Since("1.0")

public class ExprDateFromUnix extends SimpleExpression<Date> {

	static {
		RegisterManager.registerExpression(
				ExprDateFromUnix.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"date (from|of) unix [(timestamp|milis)] %number%"
		);
	}

	Expression<?> numberExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "date from unix " + numberExpr.toString(event, debug);
	}

	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.numberExpr = expressions[0];
		return true;
	}

	protected Date[] get(Event event) {
		Long longNumber = ((Number)this.numberExpr.getSingle(event)).longValue() * 1000;
		return new Date[]{new Date(longNumber)};
	}

}
