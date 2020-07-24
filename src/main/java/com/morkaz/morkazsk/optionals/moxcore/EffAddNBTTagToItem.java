package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.api.ItemUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Add/Set NBT Tag to Item")
@Description({
		"Adds or Sets any NBT tag to item.",
		"Tag key and Tag value must be text.",
		"If tag will exist, it will be replaced with new value."
})
@Examples({
		"command addtag <text> <text>:",
		"\ttrigger:",
		"\t\tadd nbt tag arg 1 with value arg 2 to player's tool",
})
@Since("1.3-beta1")

public class EffAddNBTTagToItem extends Effect {

	static {
		RegisterManager.registerEffect(
				EffAddNBTTagToItem.class,
				"[morkazsk] (add|set) nbt tag [with key] %text% (with value|valued) %text% (to|in) %itemstack%"
		);
	}

	private Expression<String> tagKeyExpr;
	private Expression<String> tagValueExpr;
	private Expression<ItemStack> itemStackExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		tagKeyExpr = (Expression<String>) expressions[0];
		tagValueExpr = (Expression<String>) expressions[1];
		itemStackExpr = (Expression<ItemStack>) expressions[2];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk set/add tag "+tagKeyExpr.toString(event, debug)+" with value "+tagValueExpr.toString(event, debug)+" to "+itemStackExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		String tagKey = tagKeyExpr.getSingle(event);
		String tagValue = tagValueExpr.getSingle(event);
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (tagKey != null && tagValue != null && itemStack != null){
			ItemUtils.setCustomTag(itemStack, tagKey, tagValue);
		}
	}


}

