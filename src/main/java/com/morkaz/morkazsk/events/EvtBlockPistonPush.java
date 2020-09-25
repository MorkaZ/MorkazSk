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

public class EvtBlockPistonPush extends Event implements Cancellable{

	static {
		RegisterManager.registerEvent(
				"Piston Push Block",
				SimpleEvent.class,
				new Class[] {EvtBlockPistonPush.class},
				"(block piston|piston block) push"
		)
				.description("Called when block is pulled by piston. Cancelling this event will prevent piston from pulling.")
				.examples("on piston block pull:",
						"\tbroadcast \"PISTON HAS PULLED BLOCK AT %event-locationb%\"")
				.since("1.0");
		RegisterManager.registerEventValue(
				EvtBlockPistonPush.class,
				Block.class,
				new Getter<Block, EvtBlockPistonPush>() {
					@Override
					public Block get(EvtBlockPistonPush evt) {
						return evt.getBlock();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockPistonPush.class,
				Location.class,
				new Getter<Location, EvtBlockPistonPush>() {
					@Override
					public Location get(EvtBlockPistonPush evt) {
						return evt.getBlock().getLocation();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockPistonPush.class,
				String.class,
				new Getter<String, EvtBlockPistonPush>() {
					@Override
					public String get(EvtBlockPistonPush evt) {
						return evt.getBlockDirection();
					}
				}
		);
	}


	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Block block, pistonBlock;
	private String direction;

	public EvtBlockPistonPush(boolean isCancelled, Block block, Block pistonBlock, BlockFace direction){
		this.isCancelled = isCancelled;
		this.block = block;
		this.pistonBlock = pistonBlock;
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
