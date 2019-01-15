package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.player.PlayerFishEvent;

@Name("Fishing Hook")
@Description({"Return fishing hook as Entity in fishing event."})
@Examples({
		"distance between location of fishing hook and player",
		"block at location of fishing hook"
})
@Since("1.0")

public class ExprFishingHook extends SimpleExpression<Entity> {

	static {
		RegisterManager.registerExpression(
				ExprFishingHook.class,
				Entity.class,
				ExpressionType.SIMPLE,
				"([morkazsk ]|[mor.])fishing(-| )hook"
		);
	}

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
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return "fishing hook";
	}

	@Override
	@javax.annotation.Nullable
	protected Entity[] get(Event e) {
		return new Entity[] {((PlayerFishEvent)e).getHook()};
	}

}
