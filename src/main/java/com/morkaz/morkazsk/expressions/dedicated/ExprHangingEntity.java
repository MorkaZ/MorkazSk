package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

@Name("Hanging entity")
@Description({
		"Return hanging entity in hanging events."
})
@Examples({
		"if hanging entity is a item frame:",
		"\tsend \"It is item frame!\""
})
@Since("1.0")

public class ExprHangingEntity extends SimpleExpression<Entity> {

	static {
		RegisterManager.registerExpression(
				ExprHangingEntity.class,
				Entity.class,
				ExpressionType.SIMPLE,
				"hang[ing](-| )entity"
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
		if (!ScriptLoader.isCurrentEvent(HangingPlaceEvent.class) && !ScriptLoader.isCurrentEvent(HangingBreakByEntityEvent.class) && !ScriptLoader.isCurrentEvent(HangingBreakEvent.class)) {
			Skript.error("[MorkazSk] This expression can be used only in hanging events!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "hanging entity";
	}

	@Override
	@javax.annotation.Nullable
	protected Entity[] get(Event event) {
		if (event instanceof HangingPlaceEvent){
			return new Entity[] {((HangingPlaceEvent)event).getEntity()};
		} else if (event instanceof HangingBreakByEntityEvent){
			return new Entity[] {((HangingBreakByEntityEvent)event).getEntity()};
		} else if (event instanceof HangingBreakEvent){
			return new Entity[] {((HangingBreakByEntityEvent)event).getEntity()};
		}
		return new Entity[] {};
	}

}

