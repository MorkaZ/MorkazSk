package com.morkaz.morkazsk.optionals.moxperms;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.Morkaz.MoxPerms.DataTypes.GroupData;
import com.Morkaz.MoxPerms.DataTypes.PlayerData;
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
		"\tif player does not have group permission \"permission.chat\":",
		"\t\tcancel event",
		"\t\tsend \"&cYou have to purcharse chat access to type something on chat on any our server!\" to player"
})
@Since("1.2")

public class CondPlayerHasGroupPermission extends Condition {

	static {
		RegisterManager.registerCondition(
				CondPlayerHasGroupPermission.class,
				"[morkazsk] [the] %player% (has|is having) group permission %string%",
				"[morkazsk] [the] %player% (hasn(t|'t)|is((nt|n't)| not) having) group permission %string%",
				"[morkazsk] [the] %string% (has|is having) group permission %string%",
				"[morkazsk] [the] %string% (hasn(t|'t)|is((nt|n't)| not) having) group permission %string%"
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
					" has group permission " + permissionExpr.toString(event, debug);
		}
		return playerExpr.toString(event, debug) +
				" has not group permission " + permissionExpr.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		Object playerObject = playerExpr.getSingle(event);
		String permission = permissionExpr.getSingle(event);
		if (playerObject != null && permission != null){
			if (playerObject instanceof Player){
				PlayerData playerData = MoxPerms.getIntance().getDataManager().playerDataMap.get((Player)playerObject);
				if (playerData != null){
					GroupData groupData = playerData.getGroupData();
					if (groupData != null){
						Boolean hasPerm = groupData.getPermissions().contains(permission);
						return isNegated() ? !hasPerm : hasPerm;
					}
				}
			} else if (playerObject instanceof String){
				String groupName = MoxPerms.getIntance().getPermissionManager().getGroup((String)playerObject);
				GroupData groupData = MoxPerms.getIntance().getDataManager().groupDataMap.get(groupName);
				if (groupData != null){
					if (groupData != null){
						Boolean hasPerm = groupData.getPermissions().contains(permission);
						return isNegated() ? !hasPerm : hasPerm;
					}
				}
			}
		}
		return isNegated() ? true : false;
	}


}
