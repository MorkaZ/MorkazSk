package com.morkaz.morkazsk.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

@Name("Entity Having Potion Effect")
@Description({
		"Checks if living entity has applied given potion effect(s). ",
		"If one of this effects will exist on entity, return will be \"true\"."
})
@Examples({
		"on damage:",
		"\tif attacker has potion effect slow:",
		"\t\tadd 1 to damage"
})
@Since("1.2-beta3")

public class CondInventoryContainItem extends Condition {


		static {
			RegisterManager.registerCondition(
					CondInventoryContainItem.class,
					"[morkazsk] %inventory% (has|have|contain[s]) at least %itemstack%",
					"[morkazsk] %inventory% (hasn(t|'t)|is((nt|n't)| not) having)|(does not|doesn[']t) contain at least %itemstack%"
			);
		}

		private Expression<Inventory> inventoryExpr;
		private Expression<ItemStack> itemStackExpr;
		int pattern = 0;

		@Override
		public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
			this.pattern = pattern;
			inventoryExpr = (Expression<Inventory>) expressions[0];
			itemStackExpr = (Expression<ItemStack>) expressions[1];
			setNegated(pattern == 1);
			return true;
		}

		@Override
		public String toString(@Nullable Event event, boolean debug) {
			if (pattern == 0){
				return inventoryExpr.toString(event, debug) +
						" contains at least " + itemStackExpr.toString(event, debug);
			}
			return inventoryExpr.toString(event, debug) +
					" does not contain at least " + itemStackExpr.toString(event, debug);
		}

		@Override
		public boolean check(Event event) {
			Inventory inventory = inventoryExpr.getSingle(event);
			ItemStack itemStack = itemStackExpr.getSingle(event);
			if (inventory != null && itemStack != null){
				for (ItemStack checkItem : inventory.getContents()){
					if (checkItem.equals(itemStack)){
						return isNegated() ? false : true;
					}
				}
			}
			return isNegated() ? true : false;
		}


}
