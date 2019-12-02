package com.morkaz.morkazsk.optionals.sqlibrary;

import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffMysqlSafeUpdate extends Effect{

	static {
		RegisterManager.registerEffect(
				EffMysqlSafeUpdate.class,
				"[mor.](normal|safe) update mysql id %string% [[(with|using)] query] %string%"
		);
	}

    private Expression<String> id;
    private Expression<String> query;
   

	@Override
    public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
            id = (Expression<String>) e[0];
            query = (Expression<String>) e[1];
            return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
            return null;
    }

    @Override
    protected void execute(final Event e) {
    	if (id.getSingle(e) != null && query.getSingle(e) != null){
    		MysqlStaticManager.safeUpdate(id.getSingle(e), query.getSingle(e));
    	}
    }

}