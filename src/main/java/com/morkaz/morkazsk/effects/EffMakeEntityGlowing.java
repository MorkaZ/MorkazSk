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
import org.bukkit.block.Block;
import org.bukkit.event.Event;
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

public class EffMakeEntityGlowing extends Effect {

	static{
		RegisterManager.registerEffect(
				EffBreakBlock.class,
				"[morkazsk] [naturally] break %block%"
		);
	}

	private Expression<Block> blockExpr;
	private Expression<ItemStack> itemExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		blockExpr = (Expression<Block>) expressions[0];
		if (pattern == 1){
			itemExpr = (Expression<ItemStack>) expressions[1];
		}
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk break "+blockExpr.toString(event, debug);
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
