package com.morkaz.morkazsk.optionals.sqlibrary;

import java.sql.ResultSet;

import javax.annotation.Nullable;

import ch.njol.skript.lang.ExpressionType;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprResultOfQuery extends SimpleExpression<ResultSet>{

	static {
		RegisterManager.registerExpression(
				ExprResultOfQuery.class,
				ResultSet.class,
				ExpressionType.SIMPLE,
				"[mor.]mysql id %string% result of query %string%"
		);
	}
	
	private Expression<String> id;
	private Expression<String> query;
	
	@Override
	public Class<? extends ResultSet> getReturnType() {
		return ResultSet.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) e[0];
		query = (Expression<String>) e[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected ResultSet[] get(Event e) {
		if (query.getSingle(e) != null && id.getSingle(e) != null){
			return new ResultSet[] {MysqlStaticManager.getResult(id.getSingle(e), query.getSingle(e))};
		}
		return new ResultSet[] {null};
	}
}
