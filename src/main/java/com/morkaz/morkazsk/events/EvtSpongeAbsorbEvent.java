package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.block.SpongeAbsorbEvent;

public class EvtSpongeAbsorbEvent {

	static {
		try {
			RegisterManager.registerEvent(
					"Sponge Absorb Event",
					SimpleEvent.class,
					new Class[]{SpongeAbsorbEvent.class},
					"sponge absorb[ing]")
					.description("Called when sponge absorbs water.")
					.examples("on sponge absorb:",
							"\tbroadcast \"Sponge absorbed water at %location of event-block%\"")
					.since("1.3");
			RegisterManager.registerEventValue(
					SpongeAbsorbEvent.class,
					Block.class,
					new Getter<Block, SpongeAbsorbEvent>() {
						@Override
						public Block get(SpongeAbsorbEvent evt) {
							return evt.getBlock();
						}
					}
			);
		} catch (NoClassDefFoundError e){
			Bukkit.getLogger().warning("[MorkazSk] Sponge Absorb Event has not been registered. Event not found in bukkit api. Version too low?");
		} catch (Exception e){
			Bukkit.getLogger().warning("[MorkazSk] Sponge Absorb Event has not been registered due to error below (it will not affect addon itself)");
			e.printStackTrace();
			Bukkit.getLogger().warning("[MorkazSk] Sponge Absorb Event has not been registered due to error above (it will not affect addon itself)");
		}
	}
}
