package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxData;
import com.morkaz.moxlibrary.other.moxdata.MoxPair;
import org.bukkit.event.Event;

@Name("Get Mox Pair of Mox Data by Key")
@Description({
		"Returns Mox Pair from Mox Data by given key. If pair will not be found, it will be <none>."
})
@Examples({
		"set {_value} to value of pair of {_mox.data} keyed by {_key} #First syntax example",
		"set {_value} to value of pair keyed by {_key} from {_mox.data} #Second syntax example",
		"#Please note that if mox data will be parsed, values will be strings and you will have to parse them by self.",
		"It is because this type of data is created for string-based databases like sql or for NBT tags."
})
@Since("1.1-beta3")
@RequiredPlugins("MoxCore")

public class ExprPairOfMoxDataByKey extends SimpleExpression<MoxPair> {

	static {
		RegisterManager.registerExpression(
				ExprPairOfMoxDataByKey.class,
				MoxPair.class,
				ExpressionType.SIMPLE,
				"[mox] pair of %moxdata% (by [key]|that contain[s] [key]|with key|key[[ed] by]) %string%",
				"[mox] pair (with key|key[[ed] by]|that contain[s] key) %string% (of|from) %moxdata%"
		);
	}

	Expression<MoxData> moxDataExpr;
	Expression<String> keyExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "mox pair of " + moxDataExpr.toString(event, debug) + " keyed by "+keyExpr.toString(event, debug);
	}

	public Class<? extends MoxPair> getReturnType() {
		return MoxPair.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		if (pattern == 0){
			this.moxDataExpr = (Expression<MoxData>) expressions[0];
			this.keyExpr = (Expression<String>) expressions[1];
		} else if (pattern == 1){
			this.moxDataExpr = (Expression<MoxData>) expressions[1];
			this.keyExpr = (Expression<String>) expressions[0];
		}
		return true;
	}

	protected MoxPair[] get(Event event) {
		MoxData moxData = moxDataExpr.getSingle(event);
		String key = keyExpr.getSingle(event);
		if (moxData != null && key != null){
			return new MoxPair[]{moxData.getPair(key)};
		}
		return new MoxPair[]{};
	}

}
