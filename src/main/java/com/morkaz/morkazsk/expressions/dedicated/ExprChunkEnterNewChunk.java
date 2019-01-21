package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.events.EvtChunkEnter;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Chunk;
import org.bukkit.event.Event;

@Name("Actual Chunk of Chunk Enter Event")
@Description({
		"Returns actual chunk of player's location in event \"on chunk enter\"."
})
@Examples({
		"on chunk enter:",
		"\tsend \"Your actual chunk is: %event-actual-chunk%\" to player"
})
@Since("1.1")

public class ExprChunkEnterNewChunk extends SimpleExpression<Chunk> {

	static {
		RegisterManager.registerExpression(
				ExprChunkEnterNewChunk.class,
				Chunk.class,
				ExpressionType.SIMPLE,
				"[morkazsk] event(-| )(new|actual)(-| )chunk"
		);
	}

	@Override
	public Class<? extends Chunk> getReturnType() {
		return Chunk.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		Class<? extends Event> eventClass = EvtChunkEnter.class;
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "event-actual-chunk";
	}

	@Override
	@javax.annotation.Nullable
	protected Chunk[] get(Event event) {
		return new Chunk[] {((EvtChunkEnter)event).getNewChunk()};
	}

}
