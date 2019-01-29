package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxPair;
import org.bukkit.event.Event;

@Name("Value of Mox Pair")
@Description({
		"Returns Value of Mox Pair."
})
@Examples({
		"set {_mox.pair} to mox pair of key \"MySuperKey\" with value \"My Super Value\"",
		"set {_value} to value of {_mox.pair}"
})
@Since("1.1-beta2")
@RequiredPlugins("MoxCore")

public class ExprValueOfPair extends SimpleExpression<Object> {

	static {
		RegisterManager.registerExpression(
				ExprValueOfPair.class,
				Object.class,
				ExpressionType.SIMPLE,
				"value of %moxpair%"
		);
	}

	Expression<MoxPair> moxPairExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "value of " + moxPairExpr.toString(event, debug);
	}

	public Class<? extends Object> getReturnType() {
		return Object.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxPairExpr = (Expression<MoxPair>) expressions[0];
		return true;
	}

	protected Object[] get(Event event) {
		MoxPair moxPair = moxPairExpr.getSingle(event);
		if (moxPair != null){
			return new Object[]{moxPair.getValue()};
		}
		return new Object[]{};
	}

}
