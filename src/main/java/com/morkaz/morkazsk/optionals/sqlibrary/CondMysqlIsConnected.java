package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondMysqlIsConnected extends Condition{

	static {
		RegisterManager.registerCondition(
				CondMysqlIsConnected.class,
				"[mor.]mysql id %string% is connect[ed]",
				"[mor.]mysql id %string% is((nt|n't)| not) connect[ed]"
		);
	}

	private Expression<String> id;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean arg2,
			ParseResult arg3) {
		id = (Expression<String>) e[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	public boolean check(Event e) {
		if (id.getSingle(e) != null){
			if (MysqlStaticManager.isConnected(id.getSingle(e))){
				if (isNegated()) {return false;} else {return true;}
			}
		}
		if (isNegated()) {return true;} else {return false;}
	}
}
