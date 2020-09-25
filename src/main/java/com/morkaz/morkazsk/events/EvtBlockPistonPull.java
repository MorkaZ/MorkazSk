package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBlockPistonPull extends Event implements Cancellable{

	static {
		RegisterManager.registerEvent(
				"Piston Block Pull",
				SimpleEvent.class,
				new Class[] {EvtBlockPistonPull.class},
				"(block piston|piston block) pull"
		)
				.description("Called when block is pulled by piston. Cancelling this event will prevent piston from pulling.")
				.examples("on piston block pull:",
						"\tbroadcast \"PISTON HAS PULLED BLOCK AT %event-locationb%\"")
				.since("1.0");
		RegisterManager.registerEventValue(
				EvtBlockPistonPull.class,
				Block.class,
				new Getter<Block, EvtBlockPistonPull>() {
					@Override
					public Block get(EvtBlockPistonPull evt) {
						return evt.getBlock();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockPistonPull.class,
				Location.class,
				new Getter<Location, EvtBlockPistonPull>() {
					@Override
					public Location get(EvtBlockPistonPull evt) {
						return evt.getBlock().getLocation();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockPistonPull.class,
				String.class,
				new Getter<String, EvtBlockPistonPull>() {
					@Override
					public String get(EvtBlockPistonPull evt) {
						return evt.getBlockDirection();
					}
				}
		);
	}

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Block block, pistonBlock;
	private String direction;

	public EvtBlockPistonPull(boolean isCancelled, Block block, Block pistonBlock, BlockFace direction){
		this.isCancelled = isCancelled;
		this.block = block;
		this.pistonBlock = this.block;
		this.direction = direction != null ? direction.toString() : null;
	}

	public Block getPistonBlock() {
		return pistonBlock;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	public Block getBlock() {
		return this.block;
	}

	public String getBlockDirection() {
		return this.direction;
	}

	@Override
	public void setCancelled(boolean e) {
		this.isCancelled = e;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}


}
