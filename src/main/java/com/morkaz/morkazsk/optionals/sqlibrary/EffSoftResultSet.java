package com.morkaz.morkazsk.optionals.sqlibrary;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlStaticManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;


public class EffSoftResultSet extends Effect{


	static {
		RegisterManager.registerEffect(
				EffSoftResultSet.class,
				"[mor.]set [variable] %string% to result of [mysql] query %string% [with] id %string% [(using|with)] [callback] index %string%"
		);
	}

    private Expression<String> idExpr;
    private Expression<String> queryExpr;
    private Expression<String> variableExpr;
    private Expression<String> indexExpr;
   

	@Override
    public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
    	variableExpr = (Expression<String>) e[0];
    	queryExpr = (Expression<String>) e[1];
    	idExpr = (Expression<String>) e[2];
    	indexExpr = (Expression<String>) e[3];
    	return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
    	return null;
    }

    @Override
    protected void execute(final Event e) {
		String index = indexExpr.getSingle(e);
		String query = queryExpr.getSingle(e);
		String id = idExpr.getSingle(e);
		String variable = variableExpr.getSingle(e);
    	if (index != null && query != null && id != null && variable != null){
    		MorkazSk.getInstance().getAsyncMysqlScheduler().scheduleRunnable(new Runnable() {
    			public void run() {
    				ResultSet set = MysqlStaticManager.getResult(id, query);
					String varName = variable.replace("{", "").replace("}", "").toLowerCase();
    				Variables.setVariable(varName, set, e, false);
    				EvtSoftResultCallback evt = new EvtSoftResultCallback(index, set);
    				new BukkitRunnable() {
						@Override
						public void run() {
							Bukkit.getPluginManager().callEvent(evt);
						}
					}.runTask(MorkazSk.getInstance());
    			}
    		});
    	}
    }

}