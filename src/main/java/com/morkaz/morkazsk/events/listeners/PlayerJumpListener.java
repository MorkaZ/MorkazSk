package com.morkaz.morkazsk.events.listeners;

import com.morkaz.morkazsk.events.EvtJump;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerJumpListener implements Listener {

	public Map<Player,Long> cooldown = new HashMap<>();

	@EventHandler(priority= EventPriority.HIGH)
	public void onPlayerMove(PlayerMoveEvent event){
		if(!event.getPlayer().isFlying() && event.getPlayer().getGameMode() != GameMode.CREATIVE){
			if(event.getFrom().getY() + 0.5 != event.getTo().getY() && event.getFrom().getY() + 0.419 < event.getTo().getY()) {
				Location loc = event.getFrom();
				loc.setY(event.getFrom().getY()-1);
				if (loc.getBlock().getType() != Material.AIR){
					if (!cooldown.containsKey(event.getPlayer()) || cooldown.get(event.getPlayer()).longValue() <= System.currentTimeMillis()){
						cooldown.put(event.getPlayer(), Long.valueOf(System.currentTimeMillis()+350));
						EvtJump evt = new EvtJump(
								event.isCancelled(),
								event.getPlayer());
						Bukkit.getPluginManager().callEvent(evt);
						if (evt.isCancelled()){
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		cooldown.remove(event.getPlayer());
	}

}
