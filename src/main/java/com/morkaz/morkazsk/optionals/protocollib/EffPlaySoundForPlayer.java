package com.morkaz.morkazsk.optionals.protocollib;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.SoundCategory;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;

@Name("Play Sound for Player")
@Description({
		"Plays sound only to single player."
})
@Examples({
		"play sound \"ENTITY_BAT_DEATH\" specifically to player with pitch 2.0 and volume 1.0 #Easier syntax for faster usage",
		"play sound \"ENTITY_BAT_DEATH\" at location of player with pitch 2.0 and volume 1.0 for player #You can define from where sound will be played"
})
@RequiredPlugins("ProtocolLib")
@Since("1.0")

public class EffPlaySoundForPlayer extends Effect{

	static{
		RegisterManager.registerEffect(
				EffPlaySoundForPlayer.class,
				"play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number% (to|for) %player%",
				"play %string% specifically (to|for) %player% [with ]pitch %number%[ and] volume %number%"
		);
	}

	private Expression<String> sound;
	private Expression<Number> pitch;
	private Expression<Number> volume;
	private Expression<Location> location;
	private Expression<Player> player;
	private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	int pattern = 0;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean arg2, ParseResult arg3) {
		this.pattern = pattern;
		if (pattern == 0){
			sound = (Expression<String>) expressions[0];
			location = (Expression<Location>) expressions[1];
			pitch = (Expression<Number>) expressions[2];
			volume = (Expression<Number>) expressions[3];
			player = (Expression<Player>) expressions[4];
		} else {
			sound = (Expression<String>) expressions[0];
			player = (Expression<Player>) expressions[1];
			pitch = (Expression<Number>) expressions[2];
			volume = (Expression<Number>) expressions[3];
		}
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		if (pattern == 0){
			return "play sound " + sound.toString(event, debug) +
					" at location " + location.toString(event, debug) +
					" with pitch " + pitch.toString(event, debug) +
					" and volume " + volume.toString(event, debug) +
					" for " + player.toString(event, debug);
		}
		return "play sound " + sound.toString(event, debug) +
				" specifically to " + player.toString(event, debug) +
				" with pitch " + pitch.toString(event, debug) +
				" and volume " + volume.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);
		if (sound.getSingle(e) != null && pitch.getSingle(e) != null && volume.getSingle(e) != null && player.getSingle(e) != null) {
			if (!ToolBox.enumContains(Sound.class, sound.getSingle(e))){
				Skript.warning("[MorkazSk] Given sound name: \""+sound.getSingle(e)+"\" does not exist in bukkit sound list! Use Bukkit enum names!");
				return;
			}
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