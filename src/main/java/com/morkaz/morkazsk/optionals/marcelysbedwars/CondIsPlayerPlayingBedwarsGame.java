package com.morkaz.morkazsk.optionals.marcelysbedwars;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import de.marcely.bedwars.api.Arena;
import de.marcely.bedwars.api.ArenaStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Player Is In Bedwars Game")
@Description({
		"Checks if player is in bedwars game which has \"running\" status "
})
@Examples({
		"if player is playing bedwars game:",
		"\tsend \"you are in running bedwars game!\""
})
@Since("1.3-beta2")

public class CondIsPlayerPlayingBedwarsGame extends Condition {

	static {
		RegisterManager.registerCondition(
				CondIsPlayerPlayingBedwarsGame.class,
				"[morkazsk] %player% is playing bedwars game",
				"[morkazsk] %player% is(n't| not) playing bedwars game"
		);
	}

	private Expression<Player> playerExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		playerExpr = (Expression<Player>) expressions[0];
		setNegated(pattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (!isNegated()) {
			return playerExpr.toString(event, debug) + " is playing bedwars game";
		}
		return playerExpr.toString(event, debug) + " is not playing bedwars game";
	}

	@Override
	public boolean check(Event event) {
		Player player = playerExpr.getSingle(event);
		if (player != null) { // avoid nullexceptions
			Arena arena = de.marcely.bedwars.api.BedwarsAPI.getArena(player);
			if (arena != null) {
				if (arena.GetStatus() == ArenaStatus.Running) {
					return isNegated() ? false : true;
				}
			}
		}
		return isNegated() ? true : false;
	}
}