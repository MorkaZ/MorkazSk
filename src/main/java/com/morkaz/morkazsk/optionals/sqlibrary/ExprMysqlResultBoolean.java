package com.morkaz.morkazsk.optionals.sqlibrary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import ch.njol.skript.lang.ExpressionType;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprMysqlResultBoolean extends SimpleExpression<Boolean>{

	static {
		RegisterManager.registerExpression(
				ExprMysqlResultBoolean.class,
				Boolean.class,
				ExpressionType.SIMPLE,
				"[mor.]mysql boolean[s] %string% from %queryresult%"
		);
	}
	
	private Expression<String> string;
	private Expression<ResultSet> set;
	
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) e[0];
		set = (Expression<ResultSet>) e[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
	protected Boolean[] get(Event e) {
		if (set.getSingle(e) != null && string.getSingle(e) != null){
		    ArrayList<Boolean> data = new ArrayList();
			try {
				set.getSingle(e).beforeFirst();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		    try {
		      while(((ResultSet)this.set.getSingle(e)).next()) {
		        Boolean name = ((ResultSet)this.set.getSingle(e)).getBoolean((String)this.string.getSingle(e));
		        data.add(name);
		      }
		      return (Boolean[])data.toArray(new Boolean[data.size()]);
		    }
		    catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		}
		return new Boolean[] {};
	}






}
