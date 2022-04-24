package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
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
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFertilizeEvent;

import java.util.ArrayList;
import java.util.List;

@Name("Fertilized Blocks")
@Description({
		"Returns blocks fertilized by use of bonemeal in \"on block fertilize\" event."
})
@Examples({
		"on block fertilize:",
		"\tbroadcast \"%fertilized-blocks%\""
})
@Since("1.3")

public class ExprFertilizedBlocks extends SimpleExpression<Block> {

	static {
		RegisterManager.registerExpression(
				ExprFertilizedBlocks.class,
				Block.class,
				ExpressionType.SIMPLE,
				"[event(-| )]fertilized(-| )blocks"
		);
	}

	@Override
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		Class<? extends Event>[] eventClasses = new Class[] {BlockFertilizeEvent.class};
		if (!ScriptLoader.isCurrentEvent(eventClasses)) {
			Skript.error("[MorkazSk] This expression can be used only in: block fertilize event!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "event-piston-block";
	}

	@Override
	protected Block[] get(Event event) {
		if (event instanceof BlockFertilizeEvent) {
			List<BlockState> blockStateList = ((BlockFertilizeEvent) event).getBlocks();
			List<Block> blockList = new ArrayList<>();
			for (BlockState blockState : blockStateList){
				blockList.add(blockState.getBlock());
			}
			return blockList.toArray(new Block[blockList.size()]);
		}
		return new Block[]{};
	}
}
