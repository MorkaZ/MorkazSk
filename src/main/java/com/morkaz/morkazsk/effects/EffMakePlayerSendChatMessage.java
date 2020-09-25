package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Force Player To Send Chat Message")
@Description({
		"This effect will send custom message as player on chat."
})
@Examples({
		"command sudochat <player> <text>:",
		"\ttrigger:",
		"\t\tforce player-argument to send chat message \"test\""
})
@Since("1.0")

public class EffMakePlayerSendChatMessage extends Effect {

	static {
		RegisterManager.registerEffect(
				EffMakePlayerSendChatMessage.class,
				"[morkazsk] (force|make) %player% to send chat message %string%"
		);
	}

	private Expression<Player> playerExpr;
	private Expression<String> textExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		playerExpr = (Expression<Player>) expressions[0];
		textExpr = (Expression<String>) expressions[1];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk force " + playerExpr.toString(event, debug) + "to send chat message " + textExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		Player player = playerExpr.getSingle(e);
		String message = textExpr.getSingle(e);
		if (player != null && message != null) {
			player.chat(message);
		}
	}
}
