package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Collection;

@Name("Duration of Potion Effect")
@Description({
		"Returns duration of applied potion effect of entity."
})
@Examples({
		"set {_duration} to duration of potion effect slow of player",
		"\tif {_duration} < 10 seconds:",
		"\t\tsend \"Slow will disappear in %{_duration}%!\""
})
@Since("1.0")

public class ExprDurationOfPotionOnEntity extends SimpleExpression<Timespan>{

	static {
		RegisterManager.registerExpression(
				ExprDurationOfPotionOnEntity.class,
				Timespan.class,
				ExpressionType.SIMPLE,
				"duration[s] of [potion [effect [type]]] %potioneffecttypes% of %livingentity%"
		);
	}

	private Expression<PotionEffectType> potionExpr;
	private Expression<LivingEntity> entityExpr;
	
	
	@Override
	public Class<? extends Timespan> getReturnType() {
		return Timespan.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		potionExpr = (Expression<PotionEffectType>) expressions[0];
		entityExpr = (Expression<LivingEntity>) expressions[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "duration of potion effect type " + potionExpr.toString(event, debug) +
				" of " + entityExpr.toString(event, debug);
	}

	@Override
	protected Timespan[] get(Event e) {
		PotionEffectType[] effects = potionExpr.getArray(e);
		LivingEntity entity = entityExpr.getSingle(e);
		if (effects != null && entity != null) {
			if (effects.length == 0){
				return null;
			}
			Collection<PotionEffect> activePotionEffects = entityExpr.getSingle(e).getActivePotionEffects();
			Timespan[] times = new Timespan[activePotionEffects.size()];
			int counter = 0;
			for (PotionEffect p : activePotionEffects){
				for (PotionEffectType effectType : effects){
					if (p.getType().equals(effectType)){
						times[counter] = Timespan.fromTicks_i(p.getDuration());
						counter++;
						break;
					}
				}
			}
			return times;
		}
		return null;
	}
}
