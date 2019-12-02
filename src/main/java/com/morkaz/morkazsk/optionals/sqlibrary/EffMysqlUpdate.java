package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.event.Event;

public class EffMysqlUpdate extends Effect{

	static {
		RegisterManager.registerEffect(
				EffMysqlUpdate.class,
				"[mor.]update mysql id %string% [[(with|using)] query] %string%"
		);
	}

    private Expression<String> idExpr;
    private Expression<String> queryExpr;
   

	@Override
    public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
            idExpr = (Expression<String>) e[0];
            queryExpr = (Expression<String>) e[1];
            return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
            return null;
    }

    @Override
    protected void execute(final Event e) {
		String id = idExpr.getSingle(e);
		String query = queryExpr.getSingle(e);
    	if (id != null && query != null){
    		MysqlStaticManager.update(id, query);
    	}
    }

}