package com.morkaz.morkazsk.optionals.moxperms;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.Morkaz.MoxPerms.MoxPerms;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Player Has Global Permission")
@Description({
		"Checks if player has global permission.",
		"Works only with plugin \"MoxPerms\"."
})
@Examples({
		"on chat:",
		"\tif player does not have global permission \"permission.chat\":",
		"\t\tcancel event",
		"\t\tsend \"&cYou have to purcharse chat access to type something on chat on any our server!\" to player"
})
@Since("1.2")

public class CondPlayerHasGlobalPermission extends Condition {

	static {
		RegisterManager.registerCondition(
				CondPlayerHasGlobalPermission.class,
				"[morkazsk] [the] %player% (has|is having) global permission %string%",
				"[morkazsk] [the] %player% (hasn(t|'t)|is((nt|n't)| not) having) global permission %string%",
				"[morkazsk] [the] %string% (has|is having) global permission %string%",
				"[morkazsk] [the] %string% (hasn(t|'t)|is((nt|n't)| not) having) global permission %string%"
		);
	}

	private Expression<String> permissionExpr;
	private Expression<?> playerExpr;
	int pattern = 0;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.pattern = pattern;
		playerExpr = expressions[0];
		permissionExpr = (Expression<String>) expressions[1];
		setNegated(pattern == 1 || pattern == 3);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (pattern == 0 || pattern == 2){
			return playerExpr.toString(event, debug) +
					" has global permission " + permissionExpr.toString(event, debug);
		}
		return playerExpr.toString(event, debug) +
				" has not global permission " + permissionExpr.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		Object playerObject = playerExpr.getSingle(event);
		String permission = permissionExpr.getSingle(event);
		if (playerObject != null && permission != null){
			if (playerObject instanceof Player){
				Boolean hasPerm = MoxPerms.getIntance().getPermissionManager().getGlobalPermissions(((Player) playerObject).getName().toLowerCase()).contains(permission);
				return isNegated() ? !hasPerm : hasPerm;
			} else if (playerObject instanceof String){
				Boolean hasPerm = MoxPerms.getIntance().getPermissionManager().getGlobalPermissions(((String)playerObject).toLowerCase()).contains(permission);
				return isNegated() ? !hasPerm : hasPerm;
			}
		}
		return isNegated() ? true : false;
	}


}
