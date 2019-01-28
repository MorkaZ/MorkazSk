package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxData;
import org.bukkit.event.Event;

@Name("Remove Mox Pair from Mox Data")
@Description({
		"This effect will remove mox pair from chain of mox data"
})
@Examples({
		"remove mox pair with key \"key\" from {_mox.data}"
})
@Since("1.1-beta2")

public class EffRemovePairFromDataByKey extends Effect {

	static{
		RegisterManager.registerEffect(
				EffRemovePairFromDataByKey.class,
				"remove [[mox] pair (with|by) key] %strings% from %moxdata%"
		);
	}

	private Expression<MoxData> moxDataExpr;
	private Expression<String> keysExpr;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.keysExpr = (Expression<String>) expressions[0];
		this.moxDataExpr = (Expression<MoxData>) expressions[1];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "remove " + keysExpr.toString(event, debug) + " from " + moxDataExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		MoxData moxData = moxDataExpr.getSingle(e);
		String[] keys = keysExpr.getArray(e);
		if (moxData != null && keys != null){
			for (String key : keys){
				moxData.remove(key);
			}
		}
	}

}
