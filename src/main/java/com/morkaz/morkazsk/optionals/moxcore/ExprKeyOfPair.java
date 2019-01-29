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

@Name("Key Name of Mox Pair")
@Description({
		"Returns Key Name of Mox Pair."
})
@Examples({
		"set {_mox.pair} to mox pair of key \"MySuperKey\" with value \"My Super Value\"",
		"set {_key.name} to key of {_mox.pair}"
})
@Since("1.1-beta2")
@RequiredPlugins("MoxCore")

public class ExprKeyOfPair extends SimpleExpression<String> {

	static {
		RegisterManager.registerExpression(
				ExprKeyOfPair.class,
				String.class,
				ExpressionType.SIMPLE,
				"key of %moxpair%"
		);
	}

	Expression<MoxPair> moxPairExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "key of " + moxPairExpr.toString(event, debug);
	}

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxPairExpr = (Expression<MoxPair>) expressions[0];
		return true;
	}

	protected String[] get(Event event) {
		MoxPair moxPair = moxPairExpr.getSingle(event);
		if (moxPair != null){
			return new String[]{moxPair.getKey()};
		}
		return new String[]{};
	}

}
