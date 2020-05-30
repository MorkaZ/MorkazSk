package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class EvtHangingPlace {

	static {
		RegisterManager.registerEvent(
				"Entity Hanging Place",
				SimpleEvent.class,
				new Class[] {HangingPlaceEvent.class},
				"[entity] hang[ing] (place|create)")
				.description("Called when hanging entity is created in world.")
				.examples("on hanging place:",
						"\tbroadcast \"HANGING ENTITY %hanging-entity% HAS BEEN CREATED BY %player% AT %event-location% ON BLOCK %event-block%\"")
				.since("1.2-beta3");
		RegisterManager.registerEventValue(
				HangingPlaceEvent.class,
				Block.class,
				new Getter<Block, HangingPlaceEvent>() {
					@Override
					public Block get(HangingPlaceEvent evt) {
						return evt.getBlock();
					}
				}
		);
		RegisterManager.registerEventValue(
				HangingPlaceEvent.class,
				Location.class,
				new Getter<Location, HangingPlaceEvent>() {
					@Override
					public Location get(HangingPlaceEvent evt) {
						return evt.getEntity().getLocation();
					}
				}
		);
		RegisterManager.registerEventValue(
				HangingPlaceEvent.class,
				Player.class,
				new Getter<Player, HangingPlaceEvent>() {
					@Override
					public Player get(HangingPlaceEvent evt) {
						return evt.getPlayer();
					}
				}
		);
		RegisterManager.registerEventValue(
				HangingPlaceEvent.class,
				Entity.class,
				new Getter<Entity, HangingPlaceEvent>() {
					@Override
					public Entity get(HangingPlaceEvent evt) {
						return evt.getEntity();
					}
				}
		);
	}

}
