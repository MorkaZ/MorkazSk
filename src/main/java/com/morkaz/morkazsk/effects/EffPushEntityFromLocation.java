package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

@Name("Push Player or Entity from Location")
@Description({"It will push living entity from location direction with specific force (if definded).",
})
@Examples({
		"#Force above 2 is very strong",
		"push player from location 1 meter behind and 1 meter below player with force 1.5",
		"push victim from attacker",
		"#Superman example",
		"on leftclick:",
		"\tpush player from location 1.5 meter behind player with force 2"
})
@Since("1.0")

public class EffPushEntityFromLocation extends Effect{

	static {
		RegisterManager.registerEffect(
				EffPushEntityFromLocation.class,
				"push [the] %livingentity% from %location%",
				"push [the] %livingentity% from %location% with force %number%"
		);
	}

	private Expression<LivingEntity> entityExpr;
	private Expression<Location> locationExpr;
	private Expression<Number> forceExpr;
	private int pattern = 0;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		this.pattern = pattern;
		entityExpr = (Expression<LivingEntity>) expressions[0];
		locationExpr = (Expression<Location>) expressions[1];
		if (pattern == 1){
			forceExpr = (Expression<Number>)expressions[2];
		}
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		if (pattern == 0){
			return  "push " + entityExpr.toString(event, debug) +
					" from " + locationExpr.toString(event, debug);
		}
		return  "push " + entityExpr.toString(event, debug) +
				" from " + locationExpr.toString(event, debug) +
				" with force " + forceExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		LivingEntity entity = this.entityExpr.getSingle(event);
		Location location = this.locationExpr.getSingle(event);
		Number force = 1d;
		if (entity == null || location == null) {
			return;
		}
		if (forceExpr != null){
			force = forceExpr.getSingle(event);
			if (force == null){
				force = 1d;
			}
		}
		Vector direction = entity.getLocation().toVector().subtract(location.toVector()).multiply(force.doubleValue());
		entity.setVelocity(direction);
	}
 
}