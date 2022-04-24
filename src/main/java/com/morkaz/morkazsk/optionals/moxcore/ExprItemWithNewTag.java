package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.api.ItemUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Item With New NBT Tag")
@Description({
		"Creates new ItemStack from old ItemStack with new NBT Tag.",
		"Item must be reforged due to several changes that may happen with item due to new tag. Because of this, new tag can not be set in effect.",
})
@Examples({
		"set {_item} to [new] {_item} with new nbt tag \"my.custom.tag\" valued \"my_custom_value\""
})
@Since("1.3-beta1")
@RequiredPlugins("MoxCore")

public class ExprItemWithNewTag extends SimpleExpression<ItemStack> {

	static {
		RegisterManager.registerExpression(
				ExprItemWithNewTag.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"[morkazsk] [reforged] [new] %itemstack% with [new] nbt tag %string% (with value|valued) %string%"
		);
	}

	Expression<String> tagKeyExpr;
	Expression<String> tagValueExpr;
	Expression<ItemStack> itemStackExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "new " + itemStackExpr.toString(event, debug) + " with nbt tag " + tagKeyExpr.toString(event, debug) + " valued "+tagValueExpr.toString(event, debug);
	}

	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.itemStackExpr = (Expression<ItemStack>) expressions[0];
		this.tagKeyExpr = (Expression<String>) expressions[1];
		this.tagValueExpr = (Expression<String>) expressions[2];
		return true;
	}

	protected ItemStack[] get(Event event) {
		String tagKey = tagKeyExpr.getSingle(event);
		String tagValue = tagValueExpr.getSingle(event);
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (tagKey != null && itemStack != null && tagValue != null){
			return new ItemStack[]{ItemUtils.setCustomTag(itemStack, tagKey, tagValue)};
		}
		return new ItemStack[]{};
	}

}
