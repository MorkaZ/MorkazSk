package com.morkaz.morkazsk.optionals.moxtokensdatabase;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.moxtokensdatabase.MoxTokensDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprTokensOfPlayer extends SimpleExpression<Number> {

	private Expression<?> object;

	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.object = e[0];
		return true;
	}

	public String toString(Event arg0, boolean arg1) {
		return "tokens of %object%";
	}

	protected Number[] get(Event e) {
		if (this.object.getSingle(e) != null) {
			if ((this.object.getSingle(e) instanceof Player)) {
				return new Number[] { MoxTokensDatabase.getInstance().getActionManager().getTokens(((Player)this.object.getSingle(e)).getName()) };
			} else if ((this.object.getSingle(e) instanceof String)) {
				return new Number[] { MoxTokensDatabase.getInstance().getActionManager().getTokens((String)this.object.getSingle(e)) };
			}
		}
		return new Number[0];
	}

	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Player player = null;
		String string = null;
		if ((this.object.getSingle(e) instanceof Player)) {
			player = (Player)this.object.getSingle(e);
		} else if ((this.object.getSingle(e) instanceof String)) {
			string = (String)this.object.getSingle(e);
		}
		if (player != null) {
			if (mode == Changer.ChangeMode.DELETE) {
				MoxTokensDatabase.getInstance().getActionManager().removePlayer(player.getName(), "morkazsk.expression");
			} else if (mode == Changer.ChangeMode.ADD) {
				MoxTokensDatabase.getInstance().getActionManager().addTokensWithCheck(player.getName(), Integer.valueOf(((Number)delta[0]).intValue()));
			} else if (mode == Changer.ChangeMode.REMOVE) {
				MoxTokensDatabase.getInstance().getActionManager().removeTokens(player.getName(), Integer.valueOf(((Number)delta[0]).intValue()));
			} else if (mode == Changer.ChangeMode.SET) {
				MoxTokensDatabase.getInstance().getActionManager().setTokensWithCheck(player.getName(), Integer.valueOf(((Number)delta[0]).intValue()));
			} else if (mode == Changer.ChangeMode.RESET) {
				MoxTokensDatabase.getInstance().getActionManager().setTokensWithCheck(player.getName(), Integer.valueOf(0));
			}
		} else if (string != null) {
			if (mode == Changer.ChangeMode.DELETE) {
				MoxTokensDatabase.getInstance().getActionManager().removePlayer(string, "morkazsk.expression");
			} else if (mode == Changer.ChangeMode.ADD) {
				MoxTokensDatabase.getInstance().getActionManager().addTokensWithCheck(string, Integer.valueOf(((Number)delta[0]).intValue()));
			} else if (mode == Changer.ChangeMode.REMOVE) {
				MoxTokensDatabase.getInstance().getActionManager().removeTokens(string, Integer.valueOf(((Number)delta[0]).intValue()));
			} else if (mode == Changer.ChangeMode.SET) {
				MoxTokensDatabase.getInstance().getActionManager().setTokensWithCheck(string, Integer.valueOf(((Number)delta[0]).intValue()));
			} else if (mode == Changer.ChangeMode.RESET) {
				MoxTokensDatabase.getInstance().getActionManager().setTokensWithCheck(string, Integer.valueOf(0));
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.DELETE) || (mode == Changer.ChangeMode.REMOVE) || (mode == Changer.ChangeMode.RESET)) {
			return (Class[])CollectionUtils.array(new Class[] { Number.class });
		}
		return null;
	}
}
