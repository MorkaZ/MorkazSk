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

public class EvtBlockFall extends Event implements Cancellable {

	static {
		RegisterManager.registerEvent(
				"Block Fall",
				SimpleEvent.class,
				new Class[] {EvtBlockFall.class},
				"block fall[ing]"
		)
		.description("Called when block starts or stops falling.")
		.examples("on block falling:",
				"\tif event-block is not air:",
				"\t\tbroadcast \"BLOCK START FALLING %event-location%\"",
				"\t\tadd event-entity to {fallingblocks::*}",
  				"\telse:",
				"\t\tbroadcast \"BLOCK STOPED FALLING %event-location%\"",
				"\t\tremove event-entity from {fallingblocks::*}) # Make sure to purge it every some time.")
		.since("1.0");
		RegisterManager.registerEventValue(
				EvtBlockFall.class,
				Block.class,
				new Getter<Block, EvtBlockFall>() {
					@Override
					public Block get(EvtBlockFall evt) {
						return evt.getBlock();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockFall.class,
				Entity.class,
				new Getter<Entity, EvtBlockFall>() {
					@Override
					public Entity get(EvtBlockFall evt) {
						return evt.getEntity();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockFall.class,
				Location.class,
				new Getter<Location, EvtBlockFall>() {
					@Override
					public Location get(EvtBlockFall evt) {
						return evt.getBlock().getLocation();
					}
				}
		);

	}

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
