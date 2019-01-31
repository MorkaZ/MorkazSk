package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxData;
import org.bukkit.event.Event;

@Name("Main Key of Mox Data")
@Description({
		"Returns main key of Mox Data. Usually it is index name, for example: player name, uuid."
})
@Examples({
		"set {_main.key} to main key of {_mox.data} #get example",
		"set main key of {_mox.data} to \"User2\" #set example",
		"delete main key of {_mox.data} #delete example"
})
@Since("1.1")
@RequiredPlugins("MoxCore")

public class ExprMainKeyOfMoxData extends SimpleExpression<String> {

	static {
		RegisterManager.registerExpression(
				ExprMainKeyOfMoxData.class,
				String.class,
				ExpressionType.SIMPLE,
				"main key of %moxdata%"
		);
	}

	Expression<MoxData> moxDataExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "main key of " + moxDataExpr.toString(event, debug);
	}

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.moxDataExpr = (Expression<MoxData>) expressions[0];
		return true;
	}

	protected String[] get(Event event) {
		MoxData moxData = moxDataExpr.getSingle(event);
		if (moxData != null){
			return new String[]{moxData.getMainKey()};
		}
		return new String[]{};
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		MoxData moxData = moxDataExpr.getSingle(event);
		if (moxData != null){
			if (mode == Changer.ChangeMode.SET){
				moxData.setMainKey((String)delta[0]);
			} else if (mode == Changer.ChangeMode.DELETE){
				moxData.setMainKey(null);
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
			return (Class[]) CollectionUtils.array(new Class[] { String.class });
		}
		return null;
	}

}
