package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.lang.util.SimpleExpression;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.player.PlayerFishEvent;

public class ExprFishingHook extends SimpleExpression<Entity> {

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		Class<? extends Event> eventClass = PlayerFishEvent.class;
		if (ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Entity[] get(Event e) {
		return new Entity[] {((PlayerFishEvent)e).getHook()};
	}

}
