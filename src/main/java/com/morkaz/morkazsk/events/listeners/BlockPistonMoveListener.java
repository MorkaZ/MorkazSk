package com.morkaz.morkazsk.events.listeners;

import com.morkaz.morkazsk.events.EvtBlockPistonPull;
import com.morkaz.morkazsk.events.EvtBlockPistonPush;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

/**
 * Listen to events where block is moved by piston.
 * */
public class BlockPistonMoveListener implements Listener{
	
	@EventHandler(priority=EventPriority.HIGH)
	public void pistonExtendListener(BlockPistonExtendEvent event){
		for (Block block : event.getBlocks().toArray(new Block[0])) {
			EvtBlockPistonPush evt = new EvtBlockPistonPush(
					event.isCancelled(),
					block,
					event.getBlock(),
					event.getDirection());
			Bukkit.getPluginManager().callEvent(evt);
			if (evt.isCancelled() == true){
				event.setCancelled(true);
			}
		}
		return;
	}

	@EventHandler(priority=EventPriority.HIGH)
	public void pistonRetractListener(BlockPistonRetractEvent event){
		for (Block block : event.getBlocks().toArray(new Block[0])) {
			EvtBlockPistonPull evt = new EvtBlockPistonPull(
					event.isCancelled(),
					block,
					event.getBlock(),
					event.getDirection());
			Bukkit.getPluginManager().callEvent(evt);
			if (evt.isCancelled() == true){
				event.setCancelled(true);
			}
		}
		return;
	}

}
