package com.morkaz.morkazsk.expressions.universal;

import java.util.Collection;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.potion.PotionEffectType;

public class ExprTierOfPotionOnEntity extends SimpleExpression<Number>{
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
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		potionExpr = (Expression<PotionEffectType>) e[0];
		entityExpr = (Expression<LivingEntity>) e[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
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
