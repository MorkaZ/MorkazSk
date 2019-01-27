package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.StringMode;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxPair;
import com.morkaz.moxlibrary.other.moxdata.UncorrectStringDataException;
import org.bukkit.event.Event;

@Name("Mox Pair")
@Description({
		"Returns special Mox Pair of key and value.",
		"Useable in Mox Data operations."
})
@Examples({
		"add key \"health\" valued by player's health to {_mox.data} # Adds Mox Pair to Mox Data",
		"set {_health} to value of \"health\" from {_mox.data}"
})
@RequiredPlugins("MoxCore")
@Since("1.1-beta2")

public class ExprMoxPair extends SimpleExpression<MoxPair> {

	static {
		RegisterManager.registerType(new ClassInfo<>(MoxPair.class, "moxpair")
				.user("mox pair")
				.name("Mox Pair")
				.description(
						"Mox Pair type. It holds key and value. Useable in Mox Data operations",
						"Specially created for databases to not create a lot of columns but can be also used in common scripting."
				)
				.usage(
						"set {_mox.pair} to key \"%player%\" with value player"
				)
				.examples(
						"set {_mox.pair} to mox pair of key \"%player%\" and value player",
						"add {_mox.pair} to {_mox.data}",
						"set {_health} to value of \"health\" from {_mox.data}"
				)
				.since("1.1-beta2")
				.defaultExpression(new EventValueExpression<>(MoxPair.class))
				.parser(new Parser<MoxPair>() {
					@Override
					public MoxPair parse(final String moxPairString, final ParseContext context) {
						try {
							return new MoxPair(moxPairString);
						} catch (UncorrectStringDataException e) {
							e.printStackTrace();
							Skript.error("[MorkazSk] Problem accoured while trying to create MoxPair from text (probably there was no key or value in your string). String that generated this problem: "+moxPairString);
						}
						return null;
					}

					@Override
					public boolean canParse(final ParseContext context) {
						return true;
					}

					@Override
					public String toString(final MoxPair moxPair, final int flags) {
						return Classes.toString(moxPair.toString());
					}

					@Override
					public String getDebugMessage(final MoxPair moxPair) {
						return "MoxPair=[" + Classes.getDebugMessage(moxPair.getKey()+"],["+moxPair.getValue()+"]");
					}

					@Override
					public String toVariableNameString(final MoxPair moxPair) {
						return "MoxPair=[" + Classes.toString(moxPair.getKey()+"],["+moxPair.getValue()+"]", StringMode.VARIABLE_NAME);
					}

					@Override
					public String getVariableNamePattern() {
						return "MoxPair=[.+],[.+]";
					}
				})
		);
		RegisterManager.registerExpression(
				ExprMoxPair.class,
				MoxPair.class,
				ExpressionType.SIMPLE,
				"[mox pair [(of|with)]] key %string% (and value|valued by) %object%"
		);

	}

	private Expression<String> keyExpr;
	private Expression<?> valueExpr;
	private MoxPair moxPair;

	public Class<? extends MoxPair> getReturnType() {
		return MoxPair.class;
	}

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.keyExpr = (Expression<String>) expressions[0];
		this.valueExpr = expressions[1];
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "mox pair of "+keyExpr.toString(event, debug)+" and value "+valueExpr.toString(event, debug);
	}

	protected MoxPair[] get(Event e) {
		String key = keyExpr.getSingle(e);
		Object value = valueExpr.getSingle(e);
		if (key != null && value != null){
			MoxPair moxPair = new MoxPair(key, value);
			this.moxPair = moxPair;
			return new MoxPair[]{};
		}
		return new MoxPair[]{};
	}

	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET){
			this.moxPair = (MoxPair) delta[0];
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if ((mode == Changer.ChangeMode.SET)) {
			return (Class[]) CollectionUtils.array(new Class[] { MoxPair.class });
		}
		return null;
	}
}

