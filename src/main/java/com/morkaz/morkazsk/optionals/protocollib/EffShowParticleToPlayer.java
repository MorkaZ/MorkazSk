package com.morkaz.morkazsk.optionals.protocollib;
 
import java.lang.reflect.InvocationTargetException;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Spawn Particle for Player")
@Description({
		"Spawn particle for single player at location."
})
@Examples({
		"spawn 20 of particle \"FLAME\" for player at player offset by 0.2, 0.2, 0.2 with speed 0.05",
})
@RequiredPlugins("ProtocolLib")
@Since("1.0")

public class EffShowParticleToPlayer extends Effect{

	static{
		RegisterManager.registerEffect(
				EffShowParticleToPlayer.class,
				"([morkazsk ]|[mor.])(spawn|show) %number%[ of] particle[s] %string% (to|for) %player% at %location% offset[ by] %number%(, | and )%number%(, | and )%number% with speed %number%"
		);
	}

	private Expression<String> particle;
	private Expression<Player> player;
	private Expression<Location> location;
	private Expression<Number> offsetX;
	private Expression<Number> offsetY;
	private Expression<Number> offsetZ;
	private Expression<Number> speed;
	private Expression<Number> amount;
	private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		amount = (Expression<Number>) expressions[0];
		particle = (Expression<String>) expressions[1];
		player = (Expression<Player>) expressions[2];
		location = (Expression<Location>) expressions[3];
		offsetX = (Expression<Number>) expressions[4];
		offsetY = (Expression<Number>) expressions[5];
		offsetZ = (Expression<Number>) expressions[6];
		speed = (Expression<Number>) expressions[7];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
			return "spawn " + amount.toString(event, debug) +
					" of particles " + particle.toString(event, debug) +
					" for " + player.toString(event, debug) +
					" at " + location.toString(event, debug) +
					" offset by " + offsetX.toString(event, debug) +
					", " + offsetY.toString(event, debug) +
					", " + offsetZ.toString(event, debug) +
					" with speed " + speed.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		if (particle.getSingle(e) != null && player.getSingle(e) != null && location.getSingle(e) != null && offsetX.getSingle(e) != null && offsetY.getSingle(e) != null && offsetZ.getSingle(e) != null){
			if (!ToolBox.enumContains(EnumWrappers.Particle.class, particle.getSingle(e))){
				Skript.warning("[MorkazSk] Given particle name: \""+particle.getSingle(e)+"\" does not exist in ProtocolLib particle names list!");
				return;
			}
			PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
			packet.getFloat().write(0, (float)location.getSingle(e).getX())
				.write(1, (float)location.getSingle(e).getY())
				.write(2, (float)location.getSingle(e).getZ())
				.write(3, offsetX.getSingle(e).floatValue())
				.write(4, offsetY.getSingle(e).floatValue())
				.write(5, offsetZ.getSingle(e).floatValue())
				.write(6, speed.getSingle(e).floatValue());
			packet.getIntegers().write(0, amount.getSingle(e).intValue());
			packet.getParticles().write(0, EnumWrappers.Particle.getByName(particle.getSingle(e)));
			try {
				protocolManager.sendServerPacket(player.getSingle(e), packet);
			} catch (InvocationTargetException e1) {
				throw new RuntimeException("[MorkazSk] ERROR EffShowParticleToPlayer - Unnable to send this packet: " + packet, e1);
			}
		}
	}
}