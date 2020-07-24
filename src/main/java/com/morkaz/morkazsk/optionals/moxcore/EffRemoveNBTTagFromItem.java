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

@Name("Remove NBT Tag from Item")
@Description({
		"Removes given NBT tag from item.",
		"Tag key must be text."
})
@Examples({
		"command removetag <text>:",
		"\ttrigger:",
		"\t\tremove nbt tag arg 1 from player's tool",
})
@Since("1.3-beta1")

public class EffRemoveNBTTagFromItem extends Effect {

	static {
		RegisterManager.registerEffect(
				EffAddNBTTagToItem.class,
				"[morkazsk] (add|set) nbt tag [with key] %text% (with value|valued) %text% (to|in) %itemstack%"
		);
	}

	private Expression<String> tagKeyExpr;
	private Expression<ItemStack> itemStackExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		tagKeyExpr = (Expression<String>) expressions[0];
		itemStackExpr = (Expression<ItemStack>) expressions[1];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk remove tag "+tagKeyExpr.toString(event, debug)+" from "+itemStackExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		String tagKey = tagKeyExpr.getSingle(event);
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (tagKey != null && itemStack != null){
			ItemUtils.removeCustomTag(itemStack, tagKey);
		}
	}


}

