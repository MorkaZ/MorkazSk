package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.event.Event;



public class EffMysqlSetPort extends Effect{

	static {
		RegisterManager.registerEffect(
				EffMysqlSetPort.class,
				"[mor.]set mysql port id %string% to %integer%"
		);
	}


    private Expression<String> id;
    private Expression<Integer> value;
   

	@Override
    public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
            id = (Expression<String>) e[0];
            value = (Expression<Integer>) e[1];
            return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
            return null;
    }

    @Override
    protected void execute(final Event e) {
    	if (this.id.getSingle(e) != null && this.value.getSingle(e) != null){
    		MysqlStaticManager.setPort(id.getSingle(e), value.getSingle(e));
    	}
    }

}