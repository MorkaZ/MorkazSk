package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class EvtHangingBreakByEntity {

	static {
		RegisterManager.registerEvent(
				"Entity Hanging Destroy",
				SimpleEvent.class,
				new Class[] {HangingBreakByEntityEvent.class},
				"[entity] hang[ing] (break|destroy) by [other] entity"
		)
				.description("Called when hanging entity is removed in world by other entity. If player is set, then it is removed by player.")
				.examples("on hanging break by other entity:",
						"\tif player is set:",
						"\t\tbroadcast \"HANGING ENTITY %hanging-entity% HAS BEEN REMOVED BY %player% AT %event-location%\"",
						"\telse:",
						"\t\tbroadcast \"HANGING ENTITY %hanging-entity% HAS BEEN REMOVED NOT BY A PLAYER!")
				.since("1.2-beta3");
		RegisterManager.registerEventValue(
				HangingBreakByEntityEvent.class,
				String.class,
				new Getter<String, HangingBreakByEntityEvent>() {
					@Override
					public String get(HangingBreakByEntityEvent evt) {
						return evt.getCause().toString();
					}
				}
		);
		RegisterManager.registerEventValue(
				HangingBreakByEntityEvent.class,
				Location.class,
				new Getter<Location, HangingBreakByEntityEvent>() {
					@Override
					public Location get(HangingBreakByEntityEvent evt) {
						return evt.getEntity().getLocation();
					}
				}
		);
		RegisterManager.registerEventValue(
				HangingBreakByEntityEvent.class,
				Player.class,
				new Getter<Player, HangingBreakByEntityEvent>() {
					@Override
					public Player get(HangingBreakByEntityEvent evt) {
						if (evt.getRemover() instanceof Player){
							return (Player) evt.getRemover();
						}
						return null;
					}
				}
		);
		RegisterManager.registerEventValue(
				HangingBreakByEntityEvent.class,
				Entity.class,
				new Getter<Entity, HangingBreakByEntityEvent>() {
					@Override
					public Entity get(HangingBreakByEntityEvent evt) {
						return evt.getEntity();
					}
				}
		);
	}

}
