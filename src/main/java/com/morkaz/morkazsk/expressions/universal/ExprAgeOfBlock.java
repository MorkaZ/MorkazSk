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
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.Event;

@Name("Growth Level/Age of Block")
@Description({
		"Returns age number of block. If block is crop, returns growth level. 1.14+"
})
@Examples({
		"set {_growth.level} to growth level of event-block",
		"add 1 to age of event-block"
})
@Since("1.2-beta3")

public class ExprAgeOfBlock extends SimpleExpression<Integer> {

	static {
		RegisterManager.registerExpression(
				ExprAgeOfBlock.class,
				Integer.class,
				ExpressionType.SIMPLE,
				"[morkazsk] (age[able]|growth level) of %block%"
		);
	}

	Expression<Block> blockExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "age of " + blockExpr.toString(event, debug);
	}

	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.blockExpr = (Expression<Block>)expressions[0];
		return true;
	}

	protected Integer[] get(Event event) {
		Block block = blockExpr.getSingle(event);
		if (block != null){
			if (block.getBlockData() instanceof Ageable){
				return new Integer[]{((Ageable) block.getBlockData()).getAge()};
			}
		}
		return new Integer[]{0};
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		Block block = blockExpr.getSingle(event);
		if (block != null){
			if (block.getBlockData() instanceof Ageable){
				Ageable ageable = (Ageable) block.getBlockData();
				if (mode == Changer.ChangeMode.SET){
					ageable.setAge(((Number)delta[0]).intValue());
				} else if (mode == Changer.ChangeMode.DELETE){
					ageable.setAge(0);
				} else if (mode == Changer.ChangeMode.ADD){
					ageable.setAge(ageable.getAge() + ((Number)delta[0]).intValue());
				} else if (mode == Changer.ChangeMode.REMOVE){
					ageable.setAge(ageable.getAge() - ((Number)delta[0]).intValue());
				}
				block.setBlockData(ageable);
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return (Class[]) CollectionUtils.array(new Class[] { Number.class });
		}
		return null;
	}

}

