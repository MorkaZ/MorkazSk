package com.morkaz.morkazsk.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;

public class EvtArmorStandInteract {

	static {
		RegisterManager.registerEvent(
				"Armor Stand Interact",
				SimpleEvent.class,
				new Class[] {PlayerArmorStandManipulateEvent.class},
				"[morkazsk] armor[ ]stand (manipulate|interact)")
				.description("Called when a player interacts with an armor stand and will either swap, retrieve or place an item.",
						"Care! If you are using paper-spigot and armor stand ticking is disabled or somehow limited, event will not work correctly!")
				.examples("on armor stand interact:",
						"\tsend \"%event-armor-stand%, %player%, %event-item%\"")
				.since("1.3-beta1");
		RegisterManager.registerEventValue(
				PlayerArmorStandManipulateEvent.class,
				Player.class,
				new Getter<Player, PlayerArmorStandManipulateEvent>() {
					@Override
					public Player get(PlayerArmorStandManipulateEvent evt) {
						return evt.getPlayer();
					}
				}
		);
		RegisterManager.registerEventValue(
				PlayerArmorStandManipulateEvent.class,
				ItemStack.class,
				new Getter<ItemStack, PlayerArmorStandManipulateEvent>() {
					@Override
					public ItemStack get(PlayerArmorStandManipulateEvent evt) {
						return evt.getArmorStandItem();
					}
				}
		);

	}
}
