package com.morkaz.morkazsk.events.listeners;

import com.morkaz.morkazsk.events.EvtBlockFall;
import com.morkaz.morkazsk.events.EvtBlockStartFall;
import com.morkaz.morkazsk.events.EvtBlockStopFall;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class BlockFallListener implements Listener {


	@EventHandler(priority= EventPriority.HIGH)
	public void onBlockFall(EntityChangeBlockEvent event){
		Entity entity = event.getEntity();
		if (!(entity instanceof FallingBlock)){
			return;
		}
		EvtBlockFall blockFallEvent = new EvtBlockFall(event.isCancelled(), event.getEntity(), event.getBlock());
		Bukkit.getPluginManager().callEvent(blockFallEvent);
		if (blockFallEvent.isCancelled()){
			event.setCancelled(true);
		}
		if (!event.getBlock().getType().equals(Material.AIR)){
			EvtBlockStartFall blockStartFallEvent = new EvtBlockStartFall(event.isCancelled(), event.getEntity(), event.getBlock());
			Bukkit.getPluginManager().callEvent(blockStartFallEvent);
			if (blockStartFallEvent.isCancelled()){
				event.setCancelled(true);
			}
		} else {
			EvtBlockStopFall blockStopFallEvent = new EvtBlockStopFall(event.isCancelled(), event.getEntity(), event.getBlock());
			Bukkit.getPluginManager().callEvent(blockStopFallEvent);
			if (blockStopFallEvent.isCancelled()){
				event.setCancelled(true);
			}
		}
	}

}
