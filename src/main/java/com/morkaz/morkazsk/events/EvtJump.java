package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtJump extends Event implements Cancellable {

	static {
		MorkazSk main = MorkazSk.getInstance();
		Boolean load = true;
		if (main != null){
			load = main.getConfig().getBoolean("elements.jump-event");
		}
		if (load){
			RegisterManager.registerEvent(
					"Player Jump",
					SimpleEvent.class,
					new Class[]{EvtJump.class},
					"[morkazsk] [player] jump[ing]"
			)
					.description("Called when player jumps.")
					.examples("on player jump:",
							"\tsend \"You jumped!\" to player")
					.since("1.0");
			RegisterManager.registerEventValue(
					EvtJump.class,
					Player.class,
					new Getter<Player, EvtJump>() {
						@Override
						public Player get(EvtJump evt) {
							return evt.getPlayer();
						}
					}
			);
		}
	}

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private Player player;

	public EvtJump(boolean isCancelled, Player player){
		this.isCancelled = isCancelled;
		this.player = player;
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
