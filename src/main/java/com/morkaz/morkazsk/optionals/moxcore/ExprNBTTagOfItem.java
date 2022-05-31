package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.api.ItemUtils;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Value of NBT Tag in Item")
@Description({
		"Returns value of given NBT Tag name in Item."
})
@Examples({
		"set {_tag.value} to nbt tag \"mycustomtag\" of player's tool"
})
@Since("1.3-beta1")
@RequiredPlugins("MoxCore")

public class ExprNBTTagOfItem extends SimpleExpression<String> {

	static {
		RegisterManager.registerExpression(
				ExprNBTTagOfItem.class,
				String.class,
				ExpressionType.SIMPLE,
				"[morkazsk] nbt tag [(with|of) key] %string% (in|of) %itemstack%"
		);
	}

	Expression<String> tagKeyExpr;
	Expression<ItemStack> itemStackExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "nbt tag " + tagKeyExpr.toString(event, debug) + " in " + itemStackExpr.toString(event, debug);
	}

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.tagKeyExpr = (Expression<String>) expressions[0];
		this.itemStackExpr = (Expression<ItemStack>) expressions[1];
		return true;
	}

	protected String[] get(Event event) {
		String tagKey = tagKeyExpr.getSingle(event);
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (tagKey != null && itemStack != null){
			if (itemStack.getType() != Material.AIR){
				return new String[]{ItemUtils.getCustomTag(itemStack, tagKey)};
			}
		}
		return new String[]{};
	}

}
