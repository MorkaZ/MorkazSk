package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Drops Of Block")
@Description({
		"Returns generated drop list of block. You can specify tool using second syntax."
})
@Examples({
		"set {_drops::*} to drops of block",
		"set {_with.tool.drops::*} to drops of block with player's tool"
})
@Since("1.0")

public class ExprDropOfBlock extends SimpleExpression<ItemStack> {

	static {
		RegisterManager.registerExpression(
				ExprDropOfBlock.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"([morkazsk ]|[mor.])drops of %block%",
				"([morkazsk ]|[mor.])drops of %block% (with|using) [tool] %itemstack%"
		);
	}

	Expression<Block> blockExpr;
	Expression<ItemStack> itemExpr;
	int pattern = 0;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		if (pattern == 0){
			return "drops of " + blockExpr.toString(event, debug);
		}
		return "drops of " + blockExpr.toString(event, debug) +
				" with " + itemExpr.toString(event, debug);
	}

	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.blockExpr = (Expression<Block>)expressions[0];
		this.pattern = pattern;
		if (pattern == 1){
			this.itemExpr = (Expression<ItemStack>)expressions[1];
		}
		return true;
	}

	protected ItemStack[] get(Event event) {
		Block block = blockExpr.getSingle(event);
		if (block != null){
			if (itemExpr == null){
				return block.getDrops().toArray(new ItemStack[block.getDrops().size()]);
			} else {
				ItemStack item = itemExpr.getSingle(event);
				if (item != null){
					return block.getDrops(item).toArray(new ItemStack[block.getDrops().size()]);
				} else {
					return block.getDrops().toArray(new ItemStack[block.getDrops().size()]);
				}
			}
		}
		return new ItemStack[]{};
	}

}
