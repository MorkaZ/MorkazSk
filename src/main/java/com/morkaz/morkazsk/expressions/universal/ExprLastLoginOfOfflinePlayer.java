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
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

@Name("Last Login Date of Offline Player")
@Description({
		"Returns date of last login of offline player. ",
		"If offline player never logged, it will be <none>."
})
@Examples({
		"set {_date} to last login of \"Morkazoid\" parsed as offline player"
})
@Since("1.0")

public class ExprLastLoginOfOfflinePlayer extends SimpleExpression<Date> {

	static {
		RegisterManager.registerExpression(
				ExprLastLoginOfOfflinePlayer.class,
				Date.class,
				ExpressionType.SIMPLE,
				"last (login|played [date]) of [the] %offlineplayer%"
		);
	}

	Expression<OfflinePlayer> offlinePlayerExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "[morkazsk] last [login of " + offlinePlayerExpr.toString(event, debug);
	}

	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.offlinePlayerExpr = (Expression<OfflinePlayer>) expressions[0];
		return true;
	}

	protected Date[] get(Event event) {
		Long lastPlayed = (this.offlinePlayerExpr.getSingle(event)).getLastPlayed();
		if (!lastPlayed.equals(0L)){
			return new Date[]{new Date(lastPlayed)};
		}
		return new Date[]{};
	}

}
