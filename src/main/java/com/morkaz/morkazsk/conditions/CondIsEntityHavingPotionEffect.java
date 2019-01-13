package com.morkaz.morkazsk.conditions;

import java.util.Collection;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CondIsEntityHavingPotionEffect extends Condition{
	
	private Expression<PotionEffectType> potionEffectTypeExpr;
	private Expression<LivingEntity> entityExpr;

	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean arg2, ParseResult arg3) {
		entityExpr = (Expression<LivingEntity>) e[0];
		potionEffectTypeExpr = (Expression<PotionEffectType>) e[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	public boolean check(Event e) {
		LivingEntity[] entities = entityExpr.getArray(e);
		PotionEffectType potionEffectType = potionEffectTypeExpr.getSingle(e);
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