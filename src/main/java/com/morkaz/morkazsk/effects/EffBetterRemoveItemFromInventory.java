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
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


@Name("Better Remove Item From Inventory")
@Description({
		"This effect is created because Skript does not always want to remove given item from specific inventory or sometimes it removes wrong item.",
		"Also, Skript's build in effect is very often bugged so this one is just always-working mirror to protect Skript users from troubles."
})
@Examples({
		"morkazsk remove item {mmo::gem.item::%player%} from player's inventory",
		"remove item 7 of diamonds named \"My Diamond\" from player's inventory"
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
		itemExpr = (Expression<ItemStack>) expressions[0];
		inventoryExpr = (Expression<Inventory>) expressions[1];
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
			inventory.removeItem(itemStack);
		}
//		if (itemStack != null && inventory != null) {
//			int amount = itemStack.getAmount();
//			for (ItemStack inventoryItem : inventory.getContents()){
//				if (inventoryItem == null || inventoryItem.getType() == Material.AIR){
//					continue;
//				}
//				if (inventoryItem.getItemMeta() == null || itemStack.getItemMeta() == null){
//					if (inventoryItem.getType() == itemStack.getType()){
//						if (inventoryItem.getAmount() > amount){
//							inventoryItem.setAmount(inventoryItem.getAmount() - amount);
//							return;
//						} else if (inventoryItem.getAmount() <= amount){
//							amount -= inventoryItem.getAmount();
//							inventory.remove(inventoryItem);
//							if (amount <= 0){
//								return;
//							}
//						}
//					}
//				} else if (inventoryItem.getItemMeta().equals(itemStack.getItemMeta())){
//					if (inventoryItem.getAmount() > amount){
//						inventoryItem.setAmount(inventoryItem.getAmount() - amount);
//						return;
//					} else if (inventoryItem.getAmount() <= amount){
//						amount -= inventoryItem.getAmount();
//						inventory.remove(inventoryItem);
//						if (amount <= 0){
//							return;
//						}
//					}
//				}
//			}
//		}
	}

}