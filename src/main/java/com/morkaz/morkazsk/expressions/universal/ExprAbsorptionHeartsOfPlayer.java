package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ReflectionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;

@Name("Absorption Hearts Of Player")
@Description({
		"Returns number value of absorption hears of player."
})
@Examples({
		"set {_absorption} to player's absorption hears",
		"add 1 to absorption hearts of player"

})
@Since("1.2-beta3")

public class ExprAbsorptionHeartsOfPlayer extends SimpleExpression<Number> {

	static {
		RegisterManager.registerExpression(
				ExprAbsorptionHeartsOfPlayer.class,
				Number.class,
				ExpressionType.SIMPLE,
				"[morkazsk] absorption heart[s] of %player%",
				"[morkazsk] %player%'s absorption heart[s]"
		);
	}

	private Expression<Player> playerExpr;

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}


	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		playerExpr = (Expression<Player>) expressions[0];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk absorption hears of " + playerExpr.toString(event, debug);
	}

	@Override
	protected Number[] get(Event event) {
		Player player = playerExpr.getSingle(event);
		if (player != null) {
			return new Number[]{getAbsorptionHearts(player)};
		}
		return new Number[]{};
	}

	@Override
	public void change(Event event, Object[] delta, Changer.ChangeMode mode){
		Player player = playerExpr.getSingle(event);
		if (player != null) {
			Number num = (Number)delta[0];
			Number numNow = getAbsorptionHearts(player);
			if (mode == Changer.ChangeMode.SET) {
				setAbsorptionHearts(player, num.floatValue());
			} else if (mode == Changer.ChangeMode.RESET) {
				setAbsorptionHearts(player, 0);
			} else if (mode == Changer.ChangeMode.ADD) {
				setAbsorptionHearts(player, numNow.floatValue() + num.floatValue());
			} else if (mode == Changer.ChangeMode.REMOVE) {
				setAbsorptionHearts(player, numNow.floatValue() - num.floatValue());
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return (Class[]) CollectionUtils.array(new Class[] { Number.class });
		}
		return null;
	}

	private Number getAbsorptionHearts(Player player) {
		try {
			Object handle = ReflectionUtil.getHandle(player);
			Float aborption = (Float) handle.getClass().getMethod("getAbsorptionHearts").invoke(handle);
			return aborption;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setAbsorptionHearts(Player player, Number number) {
		try {
			Object handle = ReflectionUtil.getHandle(player);
			handle.getClass().getMethod("setAbsorptionHearts", Float.TYPE).invoke(handle, number.floatValue());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

}
