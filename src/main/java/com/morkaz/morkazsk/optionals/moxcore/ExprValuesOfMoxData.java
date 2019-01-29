package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxData;
import org.bukkit.event.Event;

@Name("Values of Mox Data")
@Description({
		"Returns all Values of Mox Data object."
})
@Examples({
		"set {_values::*} to values of {_mox.data}",
		"loop {_values::*}:",
		"\t..."
})
@Since("1.1-beta2")
@RequiredPlugins("MoxCore")

public class ExprValuesOfMoxData extends SimpleExpression<Object> {

	static {
		RegisterManager.registerExpression(
				ExprValuesOfMoxData.class,
				Object.class,
				ExpressionType.SIMPLE,
				"[all] values of %moxdata%"
		);
	}

	Expression<MoxData> moxDataExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "values of " + moxDataExpr.toString(event, debug);
	}

	public Class<? extends Object> getReturnType() {
		return Object.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxDataExpr = (Expression<MoxData>) expressions[0];
		return true;
	}

	protected Object[] get(Event event) {
		MoxData moxData = moxDataExpr.getSingle(event);
		if (moxData != null){
			return moxData.getChain().getValues().toArray(new Object[moxData.getChain().getValues().size()]);
		}
		return new Object[]{};
	}

}