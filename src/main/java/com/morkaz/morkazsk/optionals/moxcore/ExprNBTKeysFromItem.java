package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.api.ItemUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class ExprNBTKeysFromItem  extends SimpleExpression<String> {
	static {
		RegisterManager.registerExpression(
				ExprNBTKeysFromItem.class,
				String.class,
				ExpressionType.SIMPLE,
				"[morkazsk] nbt keys of %itemstack%"
		);
	}

	Expression<ItemStack> itemStackExpr;

	public boolean isSingle() {
		return false;
	}

	public String toString(Event event, boolean debug) {
		return "morkazsk nbt keys of " + itemStackExpr.toString(event, debug);
	}

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.itemStackExpr = (Expression<ItemStack>) expressions[0];
		return true;
	}

	protected String[] get(Event event) {
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (itemStack != null){
			Set<String> keys = ItemUtils.getNBTItem(itemStack).getKeys();
			return ItemUtils.getNBTItem(itemStack).getKeys().toArray(new String[keys.size()]);
		}
		return new String[]{};
	}
}
