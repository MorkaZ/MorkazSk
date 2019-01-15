package com.morkaz.morkazsk.optionals.moxtokensdatabase;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxtokensdatabase.MoxTokensDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Tokens of Player")
@Description({
		"Returns tokens currency of MoxTokensDatabase plugin.",
		"This currency is used as premium on GC2.PL Minecraft Network."
})
@Examples({
		"set {_tokens} to tokens of player",
		"set {_tokens to tokens of \"Morkazoid\"",
		"add 10 to tokens of player",
		"remove all tokens of player",
		"set tokens of player to 50"
})
@RequiredPlugins("MoxTokensDatabase")
@Since("1.0")

public class ExprTokensOfPlayer extends SimpleExpression<Number> {

	static {
		RegisterManager.registerExpression(
				ExprTokensOfPlayer.class,
				Number.class,
				ExpressionType.SIMPLE,
				"([morkazsk ]|[mor.])tokens of %object%"
		);
	}

	private Expression<?> objectExpr;

	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.objectExpr = e[0];
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "tokens of " + objectExpr.toString(event, debug);
	}

	protected Number[] get(Event e) {
		if (this.objectExpr.getSingle(e) != null) {
			if ((this.objectExpr.getSingle(e) instanceof Player)) {
				return new Number[] { MoxTokensDatabase.getInstance().getActionManager().getTokens(((Player)this.objectExpr.getSingle(e)).getName()) };
			} else if ((this.objectExpr.getSingle(e) instanceof String)) {
				return new Number[] { MoxTokensDatabase.getInstance().getActionManager().getTokens((String)this.objectExpr.getSingle(e)) };
			}
		}
		return new Number[0];
	}

	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Player player = null;
		String string = null;
		if ((this.objectExpr.getSingle(e) instanceof Player)) {
			player = (Player)this.objectExpr.getSingle(e);
		} else if ((this.objectExpr.getSingle(e) instanceof String)) {
			string = (String)this.objectExpr.getSingle(e);
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
