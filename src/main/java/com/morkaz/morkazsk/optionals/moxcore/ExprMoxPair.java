package com.morkaz.morkazsk.optionals.moxcore;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.moxlibrary.other.moxdata.MoxPair;
import com.morkaz.moxlibrary.other.moxdata.Separator;
import com.morkaz.moxlibrary.other.moxdata.UncorrectStringDataException;
import org.bukkit.event.Event;

@Name("Mox Pair")
@Description({
		"Returns special Mox Pair of key and value.",
		"Useable in Mox Data operations."
})
@Examples({
		"add value player's health with key \"health\" to {_mox.data} # Adds Mox Pair to Mox Data",
		"set {_health} to value of \"health\" from {_mox.data}"
})
@RequiredPlugins("MoxCore")
@Since("1.1-beta2")

public class ExprMoxPair extends SimpleExpression<MoxPair> {

	static {
		RegisterManager.registerType(new ClassInfo<>(MoxPair.class, "moxpair")
				.user("moxpair(s)?")
				.name("Mox Pair")
				.description(
						"Mox Pair type. It holds key and value. Useable in Mox Data operations",
						"Specially created for databases to not create a lot of columns but can be also used in common scripting."
				)
				.usage(
						"set {_mox.pair} to value player keyed \"%player%\""
				)
				.examples(
						"set {_mox.pair} to mox pair of value player and key \"%player%\"",
						"add {_mox.pair} to {_mox.data}",
						"set {_health} to value of \"health\" from {_mox.data}"
				)
				.since("1.1-beta2")
				.defaultExpression(new EventValueExpression<>(MoxPair.class))
				.parser(new Parser<MoxPair>() {
					@Override
					public MoxPair parse(final String moxPairString, final ParseContext context) {
						try {
							if (moxPairString.contains(Separator.PAIR.toString())){
								return new MoxPair(moxPairString);
							}
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
						return moxPair.toString();
					}

					@Override
					public String getDebugMessage(final MoxPair moxPair) {
						return moxPair.toString();
					}

					@Override
					public String toVariableNameString(final MoxPair moxPair) {
						return moxPair.toString();
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}
				})
		);
		RegisterManager.registerExpression(
				ExprMoxPair.class,
				MoxPair.class,
				ExpressionType.SIMPLE,
				"[mox pair [of]] value %object% ((with|and) key|key[ed [by]]) %string%"
		);

	}

	private Expression<String> keyExpr;
	private Expression<?> valueExpr;

	public Class<? extends MoxPair> getReturnType() {
		return MoxPair.class;
	}

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.valueExpr = expressions[0];
		this.keyExpr = (Expression<String>) expressions[1];
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
			return new MoxPair[]{moxPair};
		}
		return new MoxPair[]{};
	}

}

