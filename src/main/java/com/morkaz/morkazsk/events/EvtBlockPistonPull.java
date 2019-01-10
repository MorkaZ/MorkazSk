package com.morkaz.morkazsk.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBlockPistonPull extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Block block;
	private String direction;

	public EvtBlockPistonPull(boolean isCancelled, Block blok, BlockFace direction){
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
