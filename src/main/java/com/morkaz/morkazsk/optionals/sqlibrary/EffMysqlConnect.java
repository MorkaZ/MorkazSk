package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.sql.ResultSet;

public class EffMysqlConnect extends Effect{

	static{

		//Result set type
		RegisterManager.registerType(new ClassInfo<>(ResultSet.class, "queryresult")
						.user("queryresult(s)")
						.name("queryresult")
						.description("ResultSet of sql query.")
						.usage()
						.examples()
						.since("1.2-beta3")
						.defaultExpression(new EventValueExpression<>(ResultSet.class))
						.parser(new Parser<ResultSet>() {
							@Override
							public ResultSet parse(final String moxDataString, final ParseContext context) {
								return null;
							}

							@Override
							public String toString(final ResultSet resultSet, final int flags) {
								return resultSet.toString();
							}


							@Override
							public String toVariableNameString(final ResultSet resultSet) {
								return resultSet.toString();
							}

							public String getVariableNamePattern() {
								return ".+";
							}
						})
		);

		RegisterManager.registerEffect(
				EffMysqlConnect.class,
				"[mor.]connect [to] mysql id %string%"
		);
	}

    private Expression<String> id;
   

	@Override
    public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
            id = (Expression<String>) e[0];
            return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
            return null;
    }

    @Override
    protected void execute(final Event e) {
    	if (this.id.getSingle(e) != null){
    		String result = MysqlStaticManager.connect(id.getSingle(e));
    		if (!result.equalsIgnoreCase("SUCCESS")){
    			Skript.error(result);
    			for (Player p : Bukkit.getOnlinePlayers()){
    				if (p.isOp()){
    					p.sendMessage("[SkMorkaz-MySQL] Failed to connect to MySQL. Id: " + id.getSingle(e) + ", error message: " + result + ".");
    				}
    			}
    		}
    	}
    }

}