package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxData;
import com.morkaz.moxlibrary.other.moxdata.MoxPair;
import org.bukkit.event.Event;

@Name("Mox Pairs of Mox Data")
@Description({
		"Returns all Mox Pairs of Mox Data object."
})
@Examples({
		"set {_mox.pairs::*} to mox pairs of {_mox.data}",
		"loop {_mox.pairs::*}:",
		"\tset {_key} to key of loop-value",
		"\tset {_value} to value of loop-value",
		"\t..."
})
@Since("1.1-beta2")

public class ExprPairsOfMoxData extends SimpleExpression<MoxPair> {

	static {
		RegisterManager.registerExpression(
				ExprPairsOfMoxData.class,
				MoxPair.class,
				ExpressionType.SIMPLE,
				"[all] mox pairs of %moxdata%"
		);
	}

	Expression<MoxData> moxDataExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "mox pairs of " + moxDataExpr.toString(event, debug);
	}

	public Class<? extends MoxPair> getReturnType() {
		return MoxPair.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxDataExpr = (Expression<MoxData>) expressions[0];
		return true;
	}

	protected MoxPair[] get(Event event) {
		MoxData moxData = moxDataExpr.getSingle(event);
		if (moxData != null){
			return moxData.getChain().getPairs().toArray(new MoxPair[moxData.getChain().getPairs().size()]);
		}
		return new MoxPair[]{};
	}

}
