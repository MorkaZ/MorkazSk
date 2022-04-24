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

@Name("Item Without NBT Tag")
@Description({
		"Creates new ItemStack from old ItemStack without given NBT Tag.",
		"Item must be reforged due to several changes that may happen with item due to new tag. Because of this, new tag can not be set in effect.",
})
@Examples({
		"set {_item} to [new] {_item} without nbt tag \"my.custom.tag\""
})
@Since("1.3-beta1")
@RequiredPlugins("MoxCore")

public class ExprItemWithoutTag extends SimpleExpression<ItemStack> {

	static {
		RegisterManager.registerExpression(
				ExprItemWithoutTag.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"[morkazsk] [reforged] [new] %itemstack% without nbt tag %string%"
		);
	}

	Expression<String> tagKeyExpr;
	Expression<ItemStack> itemStackExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "new " + itemStackExpr.toString(event, debug) + " without nbt tag " + tagKeyExpr.toString(event, debug);
	}

	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.itemStackExpr = (Expression<ItemStack>) expressions[0];
		this.tagKeyExpr = (Expression<String>) expressions[1];
		return true;
	}

	protected ItemStack[] get(Event event) {
		String tagKey = tagKeyExpr.getSingle(event);
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (tagKey != null && itemStack != null){
			return new ItemStack[]{ItemUtils.removeCustomTag(itemStack, tagKey)};
		}
		return new ItemStack[]{};
	}

}
