package com.morkaz.morkazsk.expressions.universal;

import java.util.Collection;

import javax.annotation.Nullable;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.potion.PotionEffectType;

@Name("Tier/Level of Potion Effect of Entity")
@Description({
		"Returns tier level of given potion effect type applied to entity."
})
@Examples({
		"set {_tier} to tier of potion effect slow of victim"
})
@Since("1.0")

public class ExprTierOfPotionOnEntity extends SimpleExpression<Number>{

	static {
		RegisterManager.registerExpression(
				ExprTierOfPotionOnEntity.class,
				Number.class,
				ExpressionType.SIMPLE,
				"[morkaz[sk]] tier[s] of [potion [effect [type]]] %potioneffecttypes% of %livingentity%"
		);
	}

	private Expression<PotionEffectType> potionExpr;
	private Expression<LivingEntity> entityExpr;


	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
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
		return "tier of potion effect type " + potionExpr.toString(event, debug) +
				" of " + entityExpr.toString(event, debug);
	}

	@Override
	protected Number[] get(Event e) {
		PotionEffectType[] effects = potionExpr.getArray(e);
		LivingEntity entity = entityExpr.getSingle(e);
		if (effects != null && entity != null) {
			if (effects.length == 0){
				return null;
			}
			Collection<PotionEffect> activePotionEffects = entityExpr.getSingle(e).getActivePotionEffects();
			Integer[] tiers = new Integer[activePotionEffects.size()];
			int counter = 0;
			for (PotionEffect activePotionEffect : activePotionEffects){
				boolean found = false;
				for (PotionEffectType effectType : effects){
					if (activePotionEffect.getType().equals(effectType)){
						tiers[counter] = activePotionEffect.getAmplifier()+1; // +1 because amplifier is upgrade level.
						found = true;
						counter++;
						break;
					}
				}
				if (!found){
					tiers[counter] = 0;
					counter++;
				}
			}
		}
		return null;
	}
}
