package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ExprItemOfItemEntity extends SimpleExpression<ItemStack> {

	Expression<Entity> entityExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event arg0, boolean arg1) {
		return "item of entity item";
	}

	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.entityExpr = (Expression<Entity>)expressions[0];
		return true;
	}

	protected ItemStack[] get(Event event) {
		Entity entity = entityExpr.getSingle(event);
		if (entity != null){
			if (entity instanceof Item){
				return new ItemStack[] {((Item)entity).getItemStack()};
			}
		}
		return new ItemStack[]{};
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		Entity entity = entityExpr.getSingle(event);
		if (entity != null){
			if (mode == Changer.ChangeMode.SET){
				((Item)entity).setItemStack((ItemStack)delta[0]);
			} else if (mode == Changer.ChangeMode.DELETE){
				((Item)entity).setItemStack(new ItemStack(Material.AIR));
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
			return (Class[]) CollectionUtils.array(new Class[] { ItemStack.class });
		}
		return null;
	}

}
