package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

@Name("Block Break Dropping Items")
@Description({
		"Returns boolean if block in \"on break\" event is dropping items.",
		"To set dropping in this event just set this expression to true or false."
})
@Examples({
		"on break:",
		"\tif event-block-dropping is true: #Condition example",
		"\t\tif event-block is blue wool:",
		"\t\t\tset event-block-dropping to false #Setting example",
		"\t\t\tdrop 1 lapis lazuli at location of event-block",
		"\t\t\t#Block will be destroyed but it will not drop default items.",
		"\t\t\t#Use it if you want make your script compatible with other scripts/plugins.",
		"\t\t\t#Also, do it this way if you want to manage block dropping without event canceling."
})
@Since("1.1-beta1")

public class ExprBlockBreakBlockDropping extends SimpleExpression<Boolean>{

	static {
		RegisterManager.registerExpression(
				ExprBlockBreakBlockDropping.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"event(-| )block(-| )dropping"
		);
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}


	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		Class<? extends Event>[] eventClasses = new Class[] {BlockBreakEvent.class};
		if (!ScriptLoader.isCurrentEvent(eventClasses)) {
			Skript.error("[MorkazSk] This expression can be used only in block break event!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "event-block-dropping";
	}

	@Override
	protected Boolean[] get(Event event) {
		return new Boolean[] {((BlockBreakEvent)event).isDropItems()};
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			Boolean bool = (Boolean)delta[0];
			if (bool != null){
				((BlockBreakEvent) event).setDropItems(bool);
			}
		} else if (mode == Changer.ChangeMode.DELETE){
			((BlockBreakEvent)event).setDropItems(false);
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
			return (Class[]) CollectionUtils.array(new Class[] { Boolean.class });
		}
		return null;
	}

}