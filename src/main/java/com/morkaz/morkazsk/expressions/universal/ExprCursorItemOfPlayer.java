package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Cursor Item of Player")
@Description({
		"Returns item in cursor while any gui is oppened."
})
@Examples({
		"set cursor item of player to air",
		"if type of event-item is type of cursor item of player",
		"delete cursor item of player #sets item in cursor to air",
		"add 5 to cursor item of player #adds 5 to amount of item in cursor."
})
@Since("1.0")

public class ExprCursorItemOfPlayer extends SimpleExpression<ItemStack>{

	static {
		RegisterManager.registerExpression(
				ExprCursorItemOfPlayer.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"[morkazsk] cursor item of %player%"
		);
	}

	private Expression<Player> playerExpr;

	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}


	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		playerExpr = (Expression<Player>) expressions[0];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "cursor item of " + playerExpr.toString(event, debug);
	}

	@Override
	protected ItemStack[] get(Event event) {
		Player player = playerExpr.getSingle(event);
		if (player != null){
			return new ItemStack[] {player.getItemOnCursor()};
		}
		return new ItemStack[] {};
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		Player player = playerExpr.getSingle(event);
		if (player != null){
			if (mode == Changer.ChangeMode.SET){
				player.setItemOnCursor((ItemStack)delta[0]);
			} else if (mode == Changer.ChangeMode.DELETE){
				player.setItemOnCursor(new ItemStack(Material.AIR));
			} else if (mode == Changer.ChangeMode.ADD){
				player.getItemOnCursor().setAmount(player.getItemOnCursor().getAmount() + ((Number)delta[0]).intValue());
			} else if (mode == Changer.ChangeMode.REMOVE){
				player.getItemOnCursor().setAmount(player.getItemOnCursor().getAmount() - ((Number)delta[0]).intValue());
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return (Class[]) CollectionUtils.array(new Class[] { ItemStack.class });
		}
		return null;
	}

}