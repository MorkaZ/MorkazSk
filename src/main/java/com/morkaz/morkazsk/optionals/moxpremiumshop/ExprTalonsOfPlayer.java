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
		"This currency is in use as soft currency on GC2.PL Minecraft Network."
})
@Examples({
		"set {_talons} to talons of player",
		"set {_talons} to talons of \"Morkazoid\"",
		"add 10 to talons of player",
		"reset talons of player",
		"set talons of player to 50"
})
@RequiredPlugins("MoxPremiumShop")
@Since("1.2-beta3")

public class ExprTalonsOfPlayer extends SimpleExpression<Number> {

	static {
		RegisterManager.registerExpression(
				ExprTalonsOfPlayer.class,
				Number.class,
				ExpressionType.SIMPLE,
				"[new] talons of %object%"
		);
	}

	private Expression<?> playerExpr;

	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.playerExpr = e[0];
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "talons of " + playerExpr.toString(event, debug);
	}

	protected Number[] get(Event e) {
		if (this.playerExpr.getSingle(e) != null) {
			if ((this.playerExpr.getSingle(e) instanceof Player)) {
				Player player = (Player) playerExpr.getSingle(e);
				return new Number[] {MoxPremiumShop.getInstance().getCurrencyDataManager().getTalons(player.getName())};
			} else if ((this.playerExpr.getSingle(e) instanceof String)) {
				return new Number[] {MoxPremiumShop.getInstance().getCurrencyDataManager().getTalons((String) playerExpr.getSingle(e))};
			}
		}
		return new Number[0];
	}

	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Player player = null;
		String playerName = null;
		if ((this.playerExpr.getSingle(e) instanceof Player)) {
			playerName = ((Player)(this.playerExpr.getSingle(e))).getName();
		} else if ((this.playerExpr.getSingle(e) instanceof String)) {
			playerName = (String)this.playerExpr.getSingle(e);
		}
		if (mode == Changer.ChangeMode.DELETE) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().setTalons(playerName, 0, true);
		} else if (mode == Changer.ChangeMode.ADD) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().addTalons(playerName, ((Number)delta[0]).intValue(), true);
		} else if (mode == Changer.ChangeMode.REMOVE) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().subtractTalons(playerName, ((Number)delta[0]).intValue(), true);
		} else if (mode == Changer.ChangeMode.SET) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().setTalons(playerName, ((Number)delta[0]).intValue(), true);
		} else if (mode == Changer.ChangeMode.RESET) {
			MoxPremiumShop.getInstance().getCurrencyDataManager().setTalons(playerName, 0, true);
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.DELETE) || (mode == Changer.ChangeMode.REMOVE) || (mode == Changer.ChangeMode.RESET)) {
			return (Class[]) CollectionUtils.array(new Class[] { Number.class });
		}
		return null;
	}
}
