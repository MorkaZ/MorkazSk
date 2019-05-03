package com.morkaz.morkazsk.optionals.moxpremiumshop;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxpremiumshop.MoxPremiumShop;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Tokens of Player")
@Description({
		"Returns tokens currency of MoxPremiumShop plugin.",
		"This currency is in use as premium currency on GC2.PL Minecraft Network."
})
@Examples({
		"set {_tokens} to tokens of player",
		"set {_tokens} to tokens of \"Morkazoid\"",
		"add 10 to tokens of player",
		"reset tokens of player",
		"set tokens of player to 50"
})
@RequiredPlugins("MoxPremiumShop")
@Since("1.2-beta3")

public class ExprTokensOfPlayer extends SimpleExpression<Number> {

	static {
		RegisterManager.registerExpression(
				ExprTokensOfPlayer.class,
				Number.class,
				ExpressionType.SIMPLE,
				"[new] tokens of %object%"
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
				Player player = (Player) objectExpr.getSingle(e);
				return new Number[] {MoxPremiumShop.getInstance().getCurrencyDataManager().getTokens(player.getName())};
			} else if ((this.objectExpr.getSingle(e) instanceof String)) {
				return new Number[] {MoxPremiumShop.getInstance().getCurrencyDataManager().getTokens((String)objectExpr.getSingle(e))};
			}
		}
		return new Number[0];
	}

	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Player player = null;
		String playerName = null;
		if ((this.objectExpr.getSingle(e) instanceof Player)) {
			playerName = ((Player)(this.objectExpr.getSingle(e))).getName();
		} else if ((this.objectExpr.getSingle(e) instanceof String)) {
			playerName = (String)this.objectExpr.getSingle(e);
		}
		if (mode == Changer.ChangeMode.DELETE) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().setTokens(playerName, 0, true);
		} else if (mode == Changer.ChangeMode.ADD) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().addTokens(playerName, (Integer) delta[0], true);
		} else if (mode == Changer.ChangeMode.REMOVE) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().subtractTokens(playerName, (Integer) delta[0], true);
		} else if (mode == Changer.ChangeMode.SET) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().setTokens(playerName, (Integer) delta[0], true);
		} else if (mode == Changer.ChangeMode.RESET) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().setTokens(playerName, 0, true);
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.DELETE) || (mode == Changer.ChangeMode.REMOVE) || (mode == Changer.ChangeMode.RESET)) {
			return (Class[]) CollectionUtils.array(new Class[] { Number.class });
		}
		return null;
	}
}
