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

@Name("Unix Time from Date")
@Description({
		"Returns unix time (long number) of skript's date object."
})
@Examples({
		"set {_unix.time} to unix of now"
})
@Since("1.0")

public class ExprUnixFromDate extends SimpleExpression<Number> {

	static {
		RegisterManager.registerExpression(
				ExprUnixFromDate.class,
				Number.class,
				ExpressionType.SIMPLE,
				"([morkazsk ]|[mor.])(unix|timestamp|milis) (from|of) [date] %date%"
		);
	}

	Expression<?> dateExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "unix from " + dateExpr.toString(event, debug);
	}

	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.dateExpr = expressions[0];
		return true;
	}

	protected Number[] get(Event event) {
		Long longNumber = ((Date)this.dateExpr.getSingle(event)).getTimestamp();
		return new Number[]{longNumber};
	}

}
