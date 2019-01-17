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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Last Login Date of Player")
@Description({
		"Returns dateExpr of last login of player.",
		"If player never logged, it will be <none>."
})
@Examples({
		"set {_date} to last login of player"
})
@Since("1.0")

public class ExprLastLoginOfPlayer extends SimpleExpression<Date> {

	static {
		RegisterManager.registerExpression(
				ExprLastLoginOfPlayer.class,
				Date.class,
				ExpressionType.SIMPLE,
				"([morkazsk ]|[mor.])last[ ](login|played[ dateExpr]) of %player%"
		);
	}

	Expression<Player> player;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "last[ ]login of " + player.toString(event, debug);
	}

	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.player = (Expression<Player>) expressions[0];
		return true;
	}

	protected Date[] get(Event event) {
		Long lastPlayed = (this.player.getSingle(event)).getLastPlayed();
		if (!lastPlayed.equals(0L)){
			return new Date[]{new Date(lastPlayed)};
		}
		return new Date[]{};
	}


}
