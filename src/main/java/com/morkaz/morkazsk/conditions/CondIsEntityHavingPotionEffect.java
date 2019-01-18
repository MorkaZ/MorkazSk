package com.morkaz.morkazsk.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Collection;

@Name("Entity Having Potion Effect")
@Description({
		"Checks if living entity has applied given potion effect(s). ",
		"If one of this effects will exist on entity, return will be \"true\"."
})
@Examples({
		"on damage:",
		"\tif attacker has potion effect slow:",
		"\t\tadd 1 to damage"
})
@Since("1.0")

public class CondIsEntityHavingPotionEffect extends Condition{

	static {
		RegisterManager.registerCondition(
				CondIsEntityHavingPotionEffect.class,
				"[morkazsk] [the] %livingentity% (has|is having) potion effect %potioneffecttypes%",
				"[morkazsk] [the] %livingentity% (hasn(t|'t)|is((nt|n't)| not) having) potion effect %potioneffecttypes%"
		);
	}
	
	private Expression<PotionEffectType> potionEffectTypeExpr;
	private Expression<LivingEntity> entityExpr;
	int pattern = 0;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		this.pattern = pattern;
		entityExpr = (Expression<LivingEntity>) expressions[0];
		potionEffectTypeExpr = (Expression<PotionEffectType>) expressions[1];
		setNegated(pattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (pattern == 0){
			return entityExpr.toString(event, debug) +
					" has potion effect " + potionEffectTypeExpr.toString(event, debug);
		}
		return entityExpr.toString(event, debug) +
				" has not potion effect " + potionEffectTypeExpr.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		LivingEntity[] entities = entityExpr.getArray(event);
		PotionEffectType potionEffectType = potionEffectTypeExpr.getSingle(event);
		if (entities != null && potionEffectType != null){
			for (LivingEntity entity : entities){
				Collection<PotionEffect> potionEffects = entity.getActivePotionEffects();
				if (potionEffects.contains(potionEffectType)){
					return isNegated() ? false : true;
				}
			}
			return isNegated() ? true : false;
		}
		return isNegated() ? true : false;
	}


}