package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

public class ExprReplacedBlockOnPlaceEvent  extends SimpleExpression<Material> {
	static {
		RegisterManager.registerExpression(
				ExprReplacedBlockOnPlaceEvent.class,
				Material.class,
				ExpressionType.SIMPLE,
				"[morkazsk] replaced block type"
		);
	}

	@Override
	public Class<? extends Material> getReturnType() {
		return Material.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		Class<? extends Event> eventClass = BlockPlaceEvent.class;
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return "replaced block in on place event";
	}

	@Override
	@javax.annotation.Nullable
	protected Material[] get(Event event) {
		return new Material[] {((BlockPlaceEvent)event).getBlockReplacedState().getType()};
	}
}


