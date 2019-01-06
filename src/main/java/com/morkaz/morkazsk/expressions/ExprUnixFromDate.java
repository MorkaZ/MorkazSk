package com.morkaz.morkazsk.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class ExprUnixFromDate extends SimpleExpression<Number> {

	Expression<?> date;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event arg0, boolean arg1) {
		return "";
	}

	// Typ zwracanego obiektu (T).
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	// Inicjacja. Wywołuje się przed innymi metodami. Używana do wczytania wartości z expression.
	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.date = expressions[0];
		return true;
	}

	// Oddaje wartości. Wartość zwrotna to wynik SAMEGO expression.
	protected Number[] get(Event event) {
		Long longNumber = ((Date)this.date.getSingle(event)).getTimestamp();
		return new Number[]{longNumber};
	}

}
