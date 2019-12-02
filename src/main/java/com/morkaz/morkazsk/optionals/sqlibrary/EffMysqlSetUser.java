package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.event.Event;



public class EffMysqlSetUser extends Effect{


	static {
		RegisterManager.registerEffect(
				EffMysqlSetUser.class,
				"[mor.]set mysql user[name] id %string% to %string%"
		);
	}

    private Expression<String> id;
    private Expression<String> value;

	@Override
    public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
            id = (Expression<String>) e[0];
            value = (Expression<String>) e[1];
            return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
            return null;
    }

    @Override
    protected void execute(final Event e) {
    	if (this.id.getSingle(e) != null && this.value.getSingle(e) != null){
    		MysqlStaticManager.setUser(id.getSingle(e), value.getSingle(e));
    	}
    }

}