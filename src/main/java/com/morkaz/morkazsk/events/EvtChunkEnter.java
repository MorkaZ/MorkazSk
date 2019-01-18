package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtChunkEnter extends Event implements Cancellable {

	static {
		if (MorkazSk.getInstance().getConfig().getBoolean("elements.chunk-enter-event")) {
			RegisterManager.registerEvent(
					"Chunk Enter/Leave",
					SimpleEvent.class,
					new Class[]{EvtChunkEnter.class},
					"[morkazsk] chunk (enter[ing]|leav[(e|ing)]|exit)"
			)
					.description(
							"Called when player moves between chunks (16x16 world areas).",
							"Very good for protecting areas scripts like factions."
					)
					.examples(
							"on chunk enter:",
							"\tsend \"You entered %event-new-chunk% and leaved %event-previous-chunk%\" to player"
					)
					.since("1.0");
		}
	}

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Chunk newChunk, oldChunk;
	private Player player;

	public EvtChunkEnter(Boolean isCancelled, Player player, Chunk newChunk, Chunk oldChunk){
		this.isCancelled = isCancelled;
		this.player = player;
		this.newChunk = newChunk;
		this.oldChunk = oldChunk;
	}

	public Chunk getNewChunk() {
		return newChunk;
	}

	public Chunk getOldChunk() {
		return oldChunk;
	}

	public Player getPlayer() {
		return player;
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
