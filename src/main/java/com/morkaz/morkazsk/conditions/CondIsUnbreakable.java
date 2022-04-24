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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;


@Name("Item Is Unbreakable")
@Description({
		"Checks if item is unbreakable. "
})
@Examples({
		"if player's tool is unbreakable:",
		"\tdamage player by 0.5"
})
@Since("1.3-beta1")

public class CondIsUnbreakable extends Condition {


	static {
		RegisterManager.registerCondition(
				CondIsUnbreakable.class,
				"[morkazsk] %itemstack% is unbreakable",
				"[morkazsk] %itemstack% is(n't| not) unbreakable"
		);
	}

	private Expression<ItemStack> itemStackExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		itemStackExpr = (Expression<ItemStack>) expressions[0];
		setNegated(pattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (!isNegated()){
			return itemStackExpr.toString(event, debug) + " is unbreakable";
		}
		return itemStackExpr.toString(event, debug) + " is not unbreakable";
	}

	@Override
	public boolean check(Event event) {
		ItemStack itemStack = itemStackExpr.getSingle(event);
		if (itemStack != null){ // avoid nullexceptions
			ItemMeta itemMeta = itemStack.getItemMeta();
			if (itemMeta != null){ // avoid nullexceptions
				if (itemStack.getItemMeta().isUnbreakable()){
					return isNegated() ? false : true;
				}
			}
		}
		return isNegated() ? true : false;
	}


}

