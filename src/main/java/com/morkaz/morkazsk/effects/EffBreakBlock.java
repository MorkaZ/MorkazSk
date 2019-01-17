package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.inventory.ItemStack;

@Name("Naturally Break Block")
@Description({
		"This effect will naturally break given block.",
		"It is possible to define specific tool which block will be broken (in this situation drop can be modified by this tool)."
})
@Examples({
		"command break:",
		"\ttrigger:",
		"\t\tbreak target-block with player's tool",
})
@Since("1.0")

public class EffBreakBlock extends Effect{

	static{
		RegisterManager.registerEffect(
				EffBreakBlock.class,
				"([morkazsk ]|[mor.])[naturally] break %block%]",
				"([morkazsk ]|[mor.])[naturally] break %block% [(using|with) %itemstack%]"
		);
	}

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