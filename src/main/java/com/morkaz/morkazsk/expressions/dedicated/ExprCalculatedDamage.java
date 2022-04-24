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
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

@Name("Final / Calculated Damage in Damage Event")
@Description({
		"Returns real final damage value in damage event. It includes all reductions."
})
@Examples({
		"on damage:",
		"\tsend \"You got hit and lost: %event-calculated-damage% hp\" to victim"
})
@Since("1.3")

public class ExprCalculatedDamage extends SimpleExpression<Double> {

	static {
		RegisterManager.registerExpression(
				ExprCalculatedDamage.class,
				Double.class,
				ExpressionType.SIMPLE,
				"[morkazsk] [event(-| )](ultimate|calculated|real)(-| )damage"
		);
	}

	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		Class<? extends Event> eventClass = EntityDamageEvent.class;
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
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
	protected Double[] get(Event event) {
		return new Double[] {((EntityDamageEvent)event).getFinalDamage()};
	}

}
