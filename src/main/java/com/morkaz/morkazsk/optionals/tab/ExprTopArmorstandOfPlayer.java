package com.morkaz.morkazsk.optionals.tab;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprTopArmorstandOfPlayer extends SimpleExpression<Entity> {

	static {
		RegisterManager.registerExpression(
				ExprTopArmorstandOfPlayer.class,
				Entity.class,
				ExpressionType.SIMPLE,
				"[morkazsk] [tab] top armor[ ]stand of name[ ]tag of %player%"
		);
	}

	Expression<Player> playerExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "morkazsk tab top armorstand of nametag of " + playerExpr.toString(event, debug);
	}

	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.playerExpr = (Expression<Player>)expressions[0];
		return true;
	}

	protected Entity[] get(Event event) {
		Player player = playerExpr.getSingle(event);
		TABAPI.getPlayer(player.getUniqueId());
		return null;
		// TODO
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		// TODO
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return (Class[]) CollectionUtils.array(new Class[] { Entity.class });
		}
		return null;
	}

}
