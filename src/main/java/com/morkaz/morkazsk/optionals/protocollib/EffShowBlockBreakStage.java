package com.morkaz.morkazsk.optionals.protocollib;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

@Name("Show Block Break Stage to Player")
@Description({
		"This effect will show block break stage of block in given location."
})
@Examples({
		"on rightclick:",
		"\tset {_block} to event-block",
		"\tloop 6 times:",
		"\t\tshow block break stage loop-number at {_block} to all players",
		"\t\twait 2 tick",
		"\tbreak {_block}"
})
@Since("1.0")

public class EffShowBlockBreakStage extends Effect{

	static{
		RegisterManager.registerEffect(
				EffShowBlockBreakStage.class,
				"([morkazsk ]|[mor.])show block (damage|break) stage %number% at %location% (for|to) %players%"
		);
	}

	private Expression<Number> stageExpr;
	private Expression<Location> locationExpr;
	private Expression<Player> playerExpr;
	private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		stageExpr = (Expression<Number>) expressions[0];
		locationExpr = (Expression<Location>) expressions[1];
		playerExpr = (Expression<Player>) expressions[2];
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "show block break stage " + stageExpr.toString(event, debug) +
				" at " + locationExpr.toString(event, debug) +
				" to " + playerExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		Location location = locationExpr.getSingle(event);
		Number stage = stageExpr.getSingle(event);
		Player players[] = playerExpr.getArray(event);
		if (location != null && stage != null && players != null){
			PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
			packet.getBlockPositionModifier().write(0, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())); //position
			packet.getIntegers().write(0, new Random().nextInt(2000)); //entityid
			packet.getIntegers().write(1, stage.intValue()); //stage
			try {
				for (Player player : players){
					protocolManager.sendServerPacket(player, packet);
				}
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}


}