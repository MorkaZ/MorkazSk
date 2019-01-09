package com.morkaz.morkazsk.conditions;

import java.util.Collection;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsPlayerHavingPotionEffect extends Condition{
	
	private Expression<String> potion;
	private Expression<LivingEntity> entity;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean arg2,
			ParseResult arg3) {
		entity = (Expression<LivingEntity>) e[0];
		potion = (Expression<String>) e[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	public boolean check(Event e) {
		if (entity.getSingle(e) != null){
			LivingEntity l = entity.getSingle(e);
			Collection<PotionEffect> C = l.getActivePotionEffects();
			for (PotionEffect p : C.toArray(new PotionEffect[0])){
				if (p.getType().getName().equalsIgnoreCase(potion.getSingle(e))){
					if (isNegated()) {return false;} else {return true;}
				} 
			} if (isNegated()) {return true;} else {return false;}
		}if (isNegated()) {return true;} else {return false;}
	}


}