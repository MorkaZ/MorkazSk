package com.morkaz.morkazsk.optionals.protocollib;
 
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.SoundCategory;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
 
//[mor.]play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number% for %player%
public class EffPlaySoundForPlayer extends Effect{

		private Expression<String> sound;
		private Expression<Number> pitch;
		private Expression<Number> volume;
		private Expression<Location> location;
		private Expression<Player> player;
		private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


		@SuppressWarnings("unchecked")
		@Override
		public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
			if (pattern == 0){
				sound = (Expression<String>) e[0];
				location = (Expression<Location>) e[1];
				pitch = (Expression<Number>) e[2];
				volume = (Expression<Number>) e[3];
				player = (Expression<Player>) e[4];
			} else {
				sound = (Expression<String>) e[0];
				player = (Expression<Player>) e[1];
				pitch = (Expression<Number>) e[2];
				volume = (Expression<Number>) e[3];
			}
			return true;
		}
 
		@Override
		public String toString(Event arg0, boolean arg1) {
			return null;
		}
 
		@Override
		protected void execute(Event e) {
			PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);
			if (sound.getSingle(e) != null && pitch.getSingle(e) != null && volume.getSingle(e) != null && player.getSingle(e) != null) {
				if (location != null){
					Location soundLocation;
					if (location.getSingle(e) == null){
						soundLocation = location.getSingle(e);
					} else {
						soundLocation = player.getSingle(e).getLocation();
					}
					packet.getSoundCategories().write(0, SoundCategory.PLAYERS);
					packet.getSoundEffects().write(0, Sound.valueOf(sound.getSingle(e)));
					packet.getIntegers().write(0, (int)(soundLocation.getX() * 8.0));
					packet.getIntegers().write(1, (int)(soundLocation.getY() * 8.0));
					packet.getIntegers().write(2, (int)(soundLocation.getZ() * 8.0));
					packet.getFloat().write(0, volume.getSingle(e).floatValue());
					packet.getFloat().write(1, pitch.getSingle(e).floatValue());
				} else {
					Location soundLocation = player.getSingle(e).getLocation();
					packet.getSoundCategories().write(0, SoundCategory.PLAYERS);
					packet.getSoundEffects().write(0, Sound.valueOf(sound.getSingle(e)));
					packet.getIntegers().write(0, (int) (soundLocation.getX() * 8.0));
					packet.getIntegers().write(1, (int) (soundLocation.getY() * 8.0));
					packet.getIntegers().write(2, (int) (soundLocation.getZ() * 8.0));
					packet.getFloat().write(0, volume.getSingle(e).floatValue());
					packet.getFloat().write(1, pitch.getSingle(e).floatValue());
				}
			}
			try {
				protocolManager.sendServerPacket(player.getSingle(e), packet);
			} catch (InvocationTargetException e1) {
				throw new RuntimeException("[SkMorkaz ERROR] Can not send this packet: " + packet, e1);
			}
		}

 
}