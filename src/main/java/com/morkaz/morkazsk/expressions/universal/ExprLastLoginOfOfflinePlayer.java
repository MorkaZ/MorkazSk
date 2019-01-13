package com.morkaz.morkazsk.expressions.universal;


import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprLastLoginOfOfflinePlayer extends SimpleExpression<Date> {

	Expression<?> offlinePlayer;

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
		this.offlinePlayer = expressions[0];
		return true;
	}

	protected Date[] get(Event event) {
		Long lastPlayed = ((OfflinePlayer)this.offlinePlayer.getSingle(event)).getLastPlayed();
		if (!lastPlayed.equals(0L)){
			return new Date[]{new Date(lastPlayed)};
		}
		return new Date[]{};
	}

}
