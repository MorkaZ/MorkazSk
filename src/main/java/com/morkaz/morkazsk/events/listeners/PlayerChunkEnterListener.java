package com.morkaz.morkazsk.events.listeners;

import com.morkaz.morkazsk.events.EvtChunkEnter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerChunkEnterListener implements Listener {

	@EventHandler(priority= EventPriority.LOWEST)
	public void onChunkEnter(PlayerMoveEvent event) {
		if (event.getFrom().getChunk() != event.getTo().getChunk()) {
			EvtChunkEnter evt = new EvtChunkEnter(event.isCancelled(), event.getPlayer(), event.getTo().getChunk(), event.getFrom().getChunk());
			Bukkit.getPluginManager().callEvent(evt);
			if (evt.isCancelled() == true) {
				event.setCancelled(true);
			}
		}
	}

}
