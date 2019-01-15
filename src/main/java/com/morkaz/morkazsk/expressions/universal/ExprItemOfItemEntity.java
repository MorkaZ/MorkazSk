package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Item of Dropped Item")
@Description({"Returns item from dropped item entity."})
@Examples({
		"set {_item} to item within caught entity #In fishing event",
		"set {_item} to item within loop-entity"
})
@Since("1.0")

public class ExprItemOfItemEntity extends SimpleExpression<ItemStack> {

	static {
		RegisterManager.registerExpression(
				ExprItemOfItemEntity.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"([morkazsk ]|[mor.])item[s] (of|within) %entity%"
		);
	}

	Expression<Entity> entityExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "item within " + entityExpr.toString(event, debug);
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
