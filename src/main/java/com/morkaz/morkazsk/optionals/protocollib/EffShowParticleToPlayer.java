package com.morkaz.morkazsk.optionals.protocollib;
 
import java.lang.reflect.InvocationTargetException;

import ch.njol.skript.Skript;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.Location;
import org.bukkit.Sound;
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
 
//[mor.]show %number%[ of] particle[s] %string% (to|for) %player% at %location% offset[ by] %number%(, | and )%number%(, | and )%number% with speed %number%
public class EffShowParticleToPlayer extends Effect{

	private Expression<String> particle;
	private Expression<Player> player;
	private Expression<Location> location;
	private Expression<Number> offsetX;
	private Expression<Number> offsetY;
	private Expression<Number> offsetZ;
	private Expression<Number> speed;
	private Expression<Number> amount;
	private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		amount = (Expression<Number>) e[0];
		particle = (Expression<String>) e[1];
		player = (Expression<Player>) e[2];
		location = (Expression<Location>) e[3];
		offsetX = (Expression<Number>) e[4];
		offsetY = (Expression<Number>) e[5];
		offsetZ = (Expression<Number>) e[6];
		speed = (Expression<Number>) e[7];
		return true;
	}

	@Override
	public String toString(Event arg0, boolean arg1) {
			return null;
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