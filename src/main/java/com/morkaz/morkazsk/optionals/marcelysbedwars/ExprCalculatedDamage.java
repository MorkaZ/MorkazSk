package com.morkaz.morkazsk.optionals.marcelysbedwars;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import de.marcely.bedwars.api.event.RoundEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprCalculatedDamage extends SimpleExpression<Player> {

	static {
		RegisterManager.registerExpression(
				ExprCalculatedDamage.class,
				Player.class,
				ExpressionType.SIMPLE,
				"[morkazsk] event-bedwars-losers"
		);
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		Class<? extends Event> eventClass = RoundEndEvent.class;
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \"" + eventClass.getName() + "\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "event-calculated-damage";
	}

	@Override
	@javax.annotation.Nullable
	protected Player[] get(Event event) {
		return ((RoundEndEvent) event).getLosers().toArray(new Player[((RoundEndEvent) event).getLosers().size()]);
	}
}


