package com.morkaz.morkazsk.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBlockFall extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Entity entity;
	private Block block;

	public EvtBlockFall(boolean isCancelled, Entity entity, Block block){
		this.isCancelled = isCancelled;
		this.entity = entity;
		this.block = block;
	}

	public Entity getEntity() {
		return entity;
	}

	public Block getBlock() {
		return block;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
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
