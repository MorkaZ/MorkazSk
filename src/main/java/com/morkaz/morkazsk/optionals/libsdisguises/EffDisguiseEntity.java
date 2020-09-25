package com.morkaz.morkazsk.optionals.libsdisguises;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

@Name("Disguise Entity to Player With Skin")
@Description({
		"This effect will disguise given entities as player with custom name and skin.",
		"Requires: LibDisguises & ProtocolLib plugins"
})
@Examples({
		"on join:"
})
@RequiredPlugins({"LibDisguises", "ProtocolLib"})
@Since("1.3-beta1")

public class EffDisguiseEntity extends Effect {

	static {
		RegisterManager.registerEffect(
				EffDisguiseEntity.class,
				"[morkazsk] disguise %entities% as player (with name|named) %string% and [with] skin [of player named] %string%"
				// "[morkazsk] disguise %entities% as %string% with skin %string%"
		);
	}

	private Expression<Entity> entitiesExpr;
	private Expression<String> nameExpr, skinExpr;


	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		entitiesExpr = (Expression<Entity>) expressions[0];
		nameExpr = (Expression<String>) expressions[1];
		skinExpr = (Expression<String>) expressions[2];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk disguise "+entitiesExpr.toString(event, debug)+" as player with name "+nameExpr.toString(event, debug)+" and with skin of player named "+skinExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		Entity[] entities = entitiesExpr.getArray(e);
		String name = nameExpr.getSingle(e);
		String skinName = skinExpr.getSingle(e);
		if (entities == null || name == null || skinName == null){
			return;
		}
		for (Entity entity : entities){
			PlayerDisguise playerDisguise = new PlayerDisguise(name, skinName);
			DisguiseAPI.disguiseToAll(entity, playerDisguise);
		}
	}
}