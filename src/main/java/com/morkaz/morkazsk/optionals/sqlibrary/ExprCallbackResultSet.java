package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExprCallbackResultSet extends SimpleExpression<ResultSet> {

	static {
		RegisterManager.registerExpression(
				ExprCallbackResultSet.class,
				ResultSet.class,
				ExpressionType.SIMPLE,
				"[mor.]callback(-| )result[set]", "event[-]resultset"
		);
	}
	
	@Override
	public Class<? extends ResultSet> getReturnType() {
		return ResultSet.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(EvtSoftResultCallback.class)) {
			Skript.error("[SkMorkaz] This type of expression (event-resultset) can be used only in event on query callback.");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected ResultSet[] get(Event e) {
		ResultSet set = ((EvtSoftResultCallback)e).getResultSet();
		try {
			set.beforeFirst();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return new ResultSet[]{set};
	}
	
}
