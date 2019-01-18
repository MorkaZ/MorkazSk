package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

@Name("Fishing State")
@Description({
		"Return fishing state as string in fishing event."
})
@Examples({
		"if fishing state is \"CAUGHT_FISH\"",
		"send \"your fishing state: %fishing state%\" to player"
})
@Since("1.0")

public class ExprFishingState extends SimpleExpression<String> {

	static {
		RegisterManager.registerExpression(
				ExprFishingState.class,
				String.class,
				ExpressionType.SIMPLE,
				"fishing(-| )state"
		);
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		Class<? extends Event> eventClass = PlayerFishEvent.class;
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return "fishing state";
	}

	@Override
	@javax.annotation.Nullable
	protected String[] get(Event e) {
		return new String[] {((PlayerFishEvent)e).getState().toString()};
	}

}
