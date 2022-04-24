package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class ExprShulkerBoxInventory extends SimpleExpression<Inventory> {

	static {
		RegisterManager.registerExpression(
				ExprShulkerBoxInventory.class,
				Inventory.class,
				ExpressionType.SIMPLE,
				"[morkazsk] shulker[ box] inventory of %itemstack%"
		);
	}

	Expression<ItemStack> itemExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "morkazsk shulkerbox inventory of " + itemExpr.toString(event, debug);
	}

	public Class<? extends Inventory> getReturnType() {
		return Inventory.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.itemExpr = (Expression<ItemStack>)expressions[0];
		return true;
	}

	protected Inventory[] get(Event event) {
		ItemStack itemStack = itemExpr.getSingle(event);
		if (itemStack != null){
			if(itemStack.getItemMeta() instanceof BlockStateMeta){
				BlockStateMeta im = (BlockStateMeta)itemStack.getItemMeta();
				if(im.getBlockState() instanceof ShulkerBox){
					ShulkerBox shulker = (ShulkerBox) im.getBlockState();
					return new Inventory[]{shulker.getInventory()};
				}
			}
		}
		return new Inventory[]{ };
	}
}

