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
				"Block Piston Push Event",
				SimpleEvent.class,
				new Class[] {EvtBlockPistonPush.class},
				"[morkaz[sk]] (block piston|piston block) push"
		)
				.description("Called when block is pulled by piston. Cancelling this event will prevent piston from pulling.")
				.examples("on block piston pull:",
						"\tbroadcast \"PISTON HAS PULLED BLOCK AT %event-locationb%\" by piston at %location of event-piston-block%",
						"\tadd event-entity to {fallingblocks::*}")
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
	}


	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Block block;
	private String direction;

	public EvtBlockPistonPush(boolean isCancelled, Block blok, BlockFace direction){
		this.isCancelled = isCancelled;
		this.block = blok;
		this.direction = direction != null ? direction.toString() : null;
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
