package com.morkaz.morkazsk.expressions.universal;

import java.util.Collection;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import org.bukkit.potion.PotionEffectType;

public class ExprDurationOfPotionOnEntity extends SimpleExpression<Timespan>{
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
				boolean found = false;
				for (PotionEffectType effectType : effects){
					if (p.getType().equals(effectType)){
						times[counter] = Timespan.fromTicks_i(p.getDuration());
						found = true;
						counter++;
						break;
					}
				}
				if (!found){
					times[counter] = Timespan.fromTicks_i(0);
					counter++;
				}
			}
		}
		return null;
	}
}
