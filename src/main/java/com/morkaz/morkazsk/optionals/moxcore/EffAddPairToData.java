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
import com.morkaz.moxlibrary.other.moxdata.MoxPair;
import org.bukkit.event.Event;

@Name("Add Mox Pair to Mox Data")
@Description({
		"This effect will add mox pair to chain of mox data.",
})
@Examples({
		"add value \"text value\" with key \"key\" to {_mox.data}"
})
@Since("1.1-beta2")

public class EffAddPairToData extends Effect {

	static{
		RegisterManager.registerEffect(
				EffAddPairToData.class,
				"add %moxpairs% to %moxdata%"
		);
	}

	private Expression<MoxData> moxDataExpr;
	private Expression<MoxPair> moxPairExpr;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxPairExpr = (Expression<MoxPair>) expressions[0];
		this.moxDataExpr = (Expression<MoxData>) expressions[1];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "add " + moxPairExpr.toString(event, debug) + " to " + moxDataExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		MoxData moxData = moxDataExpr.getSingle(e);
		MoxPair[] moxPairs = moxPairExpr.getArray(e);
		if (moxData != null && moxPairs != null){
			for (MoxPair moxPair : moxPairs){
				moxData.addPair(moxPair);
			}
		}
	}

}
