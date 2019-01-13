package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ExprDropOfBlock extends SimpleExpression<ItemStack> {

	Expression<Block> blockExpr;
	Expression<ItemStack> itemExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event arg0, boolean arg1) {
		return null;
	}

	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.blockExpr = (Expression<Block>)expressions[0];
		if (pattern == 1){
			this.itemExpr = (Expression<ItemStack>)expressions[1];
		}
		return true;
	}

	protected ItemStack[] get(Event event) {
		Block block = blockExpr.getSingle(event);
		if (block != null){
			if (itemExpr == null){
				return block.getDrops().toArray(new ItemStack[block.getDrops().size()]);
			} else {
				ItemStack item = itemExpr.getSingle(event);
				if (item != null){
					return block.getDrops(item).toArray(new ItemStack[block.getDrops().size()]);
				} else {
					return block.getDrops().toArray(new ItemStack[block.getDrops().size()]);
				}
			}
		}
		return new ItemStack[]{};
	}

}
