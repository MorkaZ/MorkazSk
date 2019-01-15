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

public class EvtBlockStartFall extends Event implements Cancellable {

	static {
		RegisterManager.registerEvent(
				"Block Start Fall",
				SimpleEvent.class,
				new Class[] {EvtBlockStartFall.class},
				"([morkazsk ]|[mor.])block start fall[ing]"
		)
				.description("Called when block starts falling.")
				.examples("on block start falling:",
						"\tbroadcast \"BLOCK START FALLING %event-location%\"",
						"\tadd event-entity to {fallingblocks::*}")
				.since("1.0");
		RegisterManager.registerEventValue(
				EvtBlockStartFall.class,
				Block.class,
				new Getter<Block, EvtBlockStartFall>() {
					@Override
					public Block get(EvtBlockStartFall evt) {
						return evt.getBlock();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockStartFall.class,
				Entity.class,
				new Getter<Entity, EvtBlockStartFall>() {
					@Override
					public Entity get(EvtBlockStartFall evt) {
						return evt.getEntity();
					}
				}
		);
		RegisterManager.registerEventValue(
				EvtBlockStartFall.class,
				Location.class,
				new Getter<Location, EvtBlockStartFall>() {
					@Override
					public Location get(EvtBlockStartFall evt) {
						return evt.getBlock().getLocation();
					}
				}
		);

	}

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Entity entity;
	private Block block;

	public EvtBlockStartFall(boolean isCancelled, Entity entity, Block block){
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
