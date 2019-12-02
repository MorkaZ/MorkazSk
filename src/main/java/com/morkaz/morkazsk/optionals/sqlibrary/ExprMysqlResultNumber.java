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

public class ExprMysqlResultNumber extends SimpleExpression<Number>{

	static {
		RegisterManager.registerExpression(
				ExprMysqlResultNumber.class,
				Number.class,
				ExpressionType.SIMPLE,
				"[mor.]mysql number[s] %string% from %queryresult%"
		);
	}

	private Expression<String> string;
	private Expression<ResultSet> set;
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

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
	protected Number[] get(Event e) {
		if (set.getSingle(e) != null && string.getSingle(e) != null){
		    ArrayList<Number> data = new ArrayList();
			try {
				set.getSingle(e).beforeFirst();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		    try {
		      while(((ResultSet)this.set.getSingle(e)).next()) {
		    	Number name = ((ResultSet)this.set.getSingle(e)).getFloat((String)this.string.getSingle(e));
		        data.add(name);
		      }
		      return (Number[])data.toArray(new Number[data.size()]);
		    }
		    catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		}
		return new Number[] {};
	}






}
