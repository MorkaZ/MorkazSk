package com.morkaz.morkazsk.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Version;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFertilizeEvent;

public class EvtBlockFertilizeEvent {

	static {
		if (Skript.getVersion().isSmallerThan(new Version("2.5"))){
			RegisterManager.registerEvent(
					"On Block Fertilize",
					SimpleEvent.class,
					new Class[]{BlockFertilizeEvent.class},
					"(block|crop) fertilize")
					.description("Called with the block changes resulting from a player fertilizing a given block with bonemeal.")
					.examples("on block fertilize:")
					.since("1.3");
			RegisterManager.registerEventValue(
					BlockFertilizeEvent.class,
					Player.class,
					new Getter<Player, BlockFertilizeEvent>() {
						@Override
						public Player get(BlockFertilizeEvent evt) {
							return evt.getPlayer();
						}
					}
			);
			RegisterManager.registerEventValue(
					BlockFertilizeEvent.class,
					Block.class,
					new Getter<Block, BlockFertilizeEvent>() {
						@Override
						public Block get(BlockFertilizeEvent evt) {
							return evt.getBlock();
						}
					}
			);
		}
	}
}
