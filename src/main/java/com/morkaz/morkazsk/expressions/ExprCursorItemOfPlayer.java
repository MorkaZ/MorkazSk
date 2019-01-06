package com.morkaz.morkazsk.expressions;

import javax.annotation.Nullable;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprCursorItemOfPlayer extends SimpleExpression<ItemStack>{

	private Expression<Block> blockExpr;
	private Expression<ItemStack> toolExpr;

	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}


	@Override
	public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
		blockExpr = (Expression<Block>) e[0];
		if (pattern == 1){
			toolExpr = (Expression<ItemStack>) e[1];
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected ItemStack[] get(Event e) {
		ItemStack tool = null;
		if (toolExpr != null) {
			tool = toolExpr.getSingle(e);
		}
		Block block = blockExpr.getSingle(e);
		if (block != null) {
			if (tool != null){
				return (ItemStack[])block.getDrops(tool).toArray();
			}
			return (ItemStack[])block.getDrops().toArray();
		}
		return new ItemStack[] {};
	}

}
