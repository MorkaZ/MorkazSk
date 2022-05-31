package com.morkaz.morkazsk.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Version;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFertilizeEvent;

public class EvtBlockFertilizeEvent {

	static void load(){
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

	static {
		try {
			if (Skript.getVersion().isSmallerThan(new Version("2.5"))) {
				load();
			}
		} catch (Exception e){
			Bukkit.getLogger().warning("[MorkazSk] ------ Exception catched. MorkazSk will try to load this feature without Skript getVersion() method. Details of first error are below.");
			e.printStackTrace();
			Bukkit.getLogger().warning("[MorkazSk] ------ Exception catched. MorkazSk will try to load this feature without Skript getVersion() method. Details of first error are above.");
			try {
				load();
				Bukkit.getLogger().info("[MorkazSk] ------ Second load was successful! Ignore previous error. ----------");
			} catch (Exception e2){
				Bukkit.getLogger().warning("[MorkazSk] ------  Another Exception catched on same feature. Seems you have very outdated server which can not handle even basic stuff. MorkazSk will try to load all rest features ignoring this one. Details are below");
				e2.printStackTrace();
				Bukkit.getLogger().warning("[MorkazSk] ------  Another Exception catched on same feature. Seems you have very outdated server which can not handle even basic stuff. MorkazSk will try to load all rest features ignoring this one. Details are above");
			}

		}
	}
}
