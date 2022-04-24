package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ExprArmorStandManipulateArmorStand  extends SimpleExpression<Entity> {

	static {
		RegisterManager.registerExpression(
				ExprArmorStandManipulateArmorStand.class,
				Entity.class,
				ExpressionType.SIMPLE,
				"[morkazsk] event(-| )armor(-| )stand"
		);
	}

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		if (!ScriptLoader.isCurrentEvent(PlayerArmorStandManipulateEvent.class)) {
			Skript.error("[MorkazSk] This expression can be used only in armor stand events!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "morkazsk event-armor-stand";
	}

	@Override
	protected Entity[] get(Event event) {
		return new Entity[] {((PlayerArmorStandManipulateEvent)event).getRightClicked()};
	}

}
