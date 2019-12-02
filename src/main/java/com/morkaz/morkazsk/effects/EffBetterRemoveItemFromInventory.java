package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


@Name("Better Remove Item From Inventory")
@Description({
		"This effect is created because Skript does not always want to remove given item from specific inventory.",
		"This feature will compare values of itemstack instad of comparing java object ID. It means that it will search using .equals() instead of '=='",
		"It will make it much more precise."
})
@Examples({
		"morkazsk remove item {mmo::gem.item::%player%} from player's inventory"
})
@Since("1.2-beta3")

public class EffBetterRemoveItemFromInventory extends Effect {

	static {
		RegisterManager.registerEffect(
				EffBetterRemoveItemFromInventory.class,
				"[morkazsk] remove item %itemstack% from %inventory%"
		);
	}

	private Expression<Inventory> inventoryExpr;
	private Expression<ItemStack> itemExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		inventoryExpr = (Expression<Inventory>) expressions[0];
		itemExpr = (Expression<ItemStack>) expressions[1];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk remove item " + itemExpr.toString(event, debug) + " from " + inventoryExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		ItemStack itemStack = itemExpr.getSingle(e);
		Inventory inventory = inventoryExpr.getSingle(e);
		if (itemStack != null && inventory != null) {
			inventory.remove(itemStack);
		}
	}

}