package com.morkaz.morkazsk.optionals.glowapi;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.inventivetalent.glow.GlowAPI;

import java.util.Arrays;

@Name("Make Entity Glowing with Color")
@Description({
		"This effect will make entity glow with specific color. To unglow use NONE as color.",
		"All color names: " +
				"BLACK(0, \"0\"), " +
				"DARK_BLUE(1, \"1\"), " +
				"DARK_GREEN(2, \"2\"), " +
				"DARK_AQUA(3, \"3\"), " +
				"DARK_RED(4, \"4\"), " +
				"DARK_PURPLE(5, \"5\"), " +
				"GOLD(6, \"6\"), " +
				"GRAY(7, \"7\"), " +
				"DARK_GRAY(8, \"8\"), " +
				"BLUE(9, \"9\"), " +
				"GREEN(10, \"a\"), " +
				"AQUA(11, \"b\"), " +
				"RED(12, \"c\"), " +
				"PURPLE(13, \"d\"), " +
				"YELLOW(14, \"e\"), " +
				"WHITE(15, \"f\"), " +
				"NONE(-1, \"\");"
})
@Examples({
		"on join:",
		"\tloop all players:",
		"\t\tif loop-player has permission \"admin\":",
		"\t\t\tmake loop-player glow with color \"RED\" for player",
		" ",
		"on drop:",
		"\tmake event-entity glow with color \"YELLOW\" for all players"
})
@RequiredPlugins({"GlowAPI", "PacketListenerAPI"})
@Since("1.2-beta1")

public class EffMakeEntityGlowWithColor extends Effect {

	static{
		RegisterManager.registerEffect(
				EffMakeEntityGlowWithColor.class,
				"[morkazsk] make %entities% glow with color %string% for %players%"
		);
	}

	private Expression<Entity> entitiesExpr;
	private Expression<String> colorExpr;
	private Expression<Player> playersExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		entitiesExpr = (Expression<Entity>) expressions[0];
		colorExpr = (Expression<String>) expressions[1];
		playersExpr = (Expression<Player>) expressions[2];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk make " + entitiesExpr.toString(event, debug) + " glow with color " + colorExpr.toString(event, debug) + " for " + playersExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		String color = colorExpr.getSingle(e);
		Entity[] entities = entitiesExpr.getArray(e);
		Player[] players = playersExpr.getArray(e);
		if (ToolBox.enumContains(GlowAPI.Color.class, (color+"").toUpperCase())){
			GlowAPI.setGlowing(Arrays.asList(entities), GlowAPI.Color.valueOf(color), Arrays.asList(players));
		}
	}

}
