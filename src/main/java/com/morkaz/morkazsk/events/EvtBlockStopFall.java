package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBlockStopFall extends Event implements Cancellable {

	static {
		RegisterManager.registerEvent(
				"Block Stop Fall",
				SimpleEvent.class,
				new Class[]{EvtBlockStopFall.class},
				"block stop fall[ing]"
		)
				.description("Called when block stops falling.")
				.examples("on block stop falling:",
						"\tbroadcast \"BLOCK STOPPED FALLING (%event-location%)\"",
						"\tremove event-entity from {fallingblocks::*}")
				.since("1.0");
		RegisterManager.registerEventValue(
				EvtBlockStopFall.class,
				Block.class,
				new Getter<Block, EvtBlockStopFall>() {
					@Override
					public Block get(EvtBlockStopFall evt) {
						return evt.getBlock();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockStopFall.class,
				Entity.class,
				new Getter<Entity, EvtBlockStopFall>() {
					@Override
					public Entity get(EvtBlockStopFall evt) {
						return evt.getEntity();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockStopFall.class,
				Location.class,
				new Getter<Location, EvtBlockStopFall>() {
					@Override
					public Location get(EvtBlockStopFall evt) {
						return evt.getBlock().getLocation();
					}
				}
		);
	}

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Entity entity;
	private Block block;

	public EvtBlockStopFall(boolean isCancelled, Entity entity, Block block){
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
