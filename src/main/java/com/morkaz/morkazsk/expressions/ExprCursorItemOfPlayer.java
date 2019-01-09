package com.morkaz.morkazsk.expressions;

import javax.annotation.Nullable;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprCursorItemOfPlayer extends SimpleExpression<ItemStack>{

	private Expression<Player> playerExpr;

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
		playerExpr = (Expression<Player>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	protected ItemStack[] get(Event e) {
		Player player = playerExpr.getSingle(e);
		if (player != null){
			return new ItemStack[] {player.getItemOnCursor()};
		}
		return new ItemStack[] {};
	}

	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Player player = playerExpr.getSingle(e);
		if (player != null){
			if (mode == Changer.ChangeMode.SET){
				player.setItemOnCursor((ItemStack)delta[0]);
			} else if (mode == Changer.ChangeMode.DELETE){
				player.setItemOnCursor(new ItemStack(Material.AIR));
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if ((mode == Changer.ChangeMode.SET)) {
			return (Class[]) CollectionUtils.array(new Class[] { ItemStack.class });
		}
		return null;
	}

}
