package com.morkaz.morkazsk.expressions;


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

	// Typ zwracanego obiektu (T).
	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	// Inicjacja. Wywołuje się przed innymi metodami. Używana do wczytania wartości z expression.
	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.offlinePlayer = expressions[0];
		return true;
	}

	// Oddaje wartości. Wartość zwrotna to wynik SAMEGO expression.
	protected Date[] get(Event event) {
		Long lastPlayed = ((OfflinePlayer)this.offlinePlayer.getSingle(event)).getLastPlayed();
		if (!lastPlayed.equals(0L)){
			return new Date[]{new Date(lastPlayed)};
		}
		return new Date[]{};
	}

//	public void change(Event e, Object[] values, Changer.ChangeMode mode) {
//
//	}

//	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
//		return null;
//	}

}
