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

@Name("Keys of Mox Data")
@Description({
		"Returns all Keys of Mox Data object."
})
@Examples({
		"set {_keys::*} to keys of {_mox.data}",
		"loop {_keys::*}:",
		"\t..."
})
@Since("1.1-beta2")
@RequiredPlugins("MoxCore")

public class ExprKeysOfMoxData extends SimpleExpression<String> {

	static {
		RegisterManager.registerExpression(
				ExprKeysOfMoxData.class,
				String.class,
				ExpressionType.SIMPLE,
				"[all] keys of %moxdata%"
		);
	}

	Expression<MoxData> moxDataExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "keys of " + moxDataExpr.toString(event, debug);
	}

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxDataExpr = (Expression<MoxData>) expressions[0];
		return true;
	}

	protected String[] get(Event event) {
		MoxData moxData = moxDataExpr.getSingle(event);
		if (moxData != null){
			return moxData.getChain().getKeys().toArray(new String[moxData.getChain().getKeys().size()]);
		}
		return new String[]{};
	}

}
