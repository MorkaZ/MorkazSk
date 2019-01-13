package com.morkaz.morkazsk.effects;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.inventory.ItemStack;

//[mor.]break %block% [using %item%]
public class EffBreakBlock extends Effect{

	private Expression<Block> blockExpr;
	private Expression<ItemStack> itemExpr;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
		blockExpr = (Expression<Block>) e[0];
		if (pattern == 1){
			itemExpr = (Expression<ItemStack>) e[1];
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	protected void execute(Event e) {
		Block block = blockExpr.getSingle(e);
		if (block != null){
			if (itemExpr != null){
				ItemStack item = itemExpr.getSingle(e);
				if (item != null){
					block.breakNaturally(item);
				}
			} else {
				block.breakNaturally();
			}
		}
	}


}