package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxData;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Mox Data contains Mox Key")
@Description({
		"Checks if Mox Data contains Mox Pair with given key."
})
@Examples({
		"if {_mox.data} doesn't contain pair with key \"my-key\":",
		"\tsend \"This mox data does not contain this key!\""
})
@Since("1.1-beta2")

public class CondIsPairInMoxData extends Condition {

	static {
		RegisterManager.registerCondition(
				CondIsPairInMoxData.class,
				"%moxdata% contain[s] [mox] pair with key[s] %strings%",
				"%moxdata% do(esn't|es not| not) contain[s] [mox] pair with key[s] %strings%"
		);
	}

	private Expression<MoxData> moxDataExpr;
	private Expression<String> keyExpr;
	int pattern = 0;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.pattern = pattern;
		moxDataExpr = (Expression<MoxData>) expressions[0];
		keyExpr = (Expression<String>) expressions[1];
		setNegated(pattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (pattern == 0) {
			return moxDataExpr.toString(event, debug) + " contains pair with key " + keyExpr.toString(event, debug);
		}
		return moxDataExpr.toString(event, debug) + " does not contain pair with key " + keyExpr.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		String[] keys = keyExpr.getArray(event);
		MoxData moxData = moxDataExpr.getSingle(event);
		if (keys != null && moxData != null) {
			for (String key : keys) {
				if (moxData.getKeys().contains(key)) {
					return isNegated() ? false : true;
				}
			}
		}
		return isNegated() ? true : false;
	}
}
