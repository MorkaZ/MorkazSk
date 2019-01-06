package com.morkaz.morkazsk.expressions;


import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprLastLoginOfPlayer extends SimpleExpression<Date> {

	Expression<?> player;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event arg0, boolean arg1) {
		return "last[ ]login of %player%";
	}

	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.player = expressions[0];
		return true;
	}

	protected Date[] get(Event event) {
		Long lastPlayed = ((Player)this.player.getSingle(event)).getLastPlayed();
		if (!lastPlayed.equals(0L)){
			return new Date[]{new Date(lastPlayed)};
		}
		return new Date[]{};
	}


}
