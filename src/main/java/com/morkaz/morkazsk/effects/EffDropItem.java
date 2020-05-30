package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Name("Drop Item At Location")
@Description({
		"This effect is created because Skript does not always want to spawn dropped item.",
		"Also, Skript's build in effect is very often bugged so this one is just always-working mirror to protect Skript users from troubles."
})
@Examples({
		"morkazsk drop item %itemstack% at %location%"
})
@Since("1.2-beta3")

public class EffDropItem extends Effect {

	static {
		RegisterManager.registerEffect(
				EffDropItem.class,
				"[morkazsk] drop item %itemstack% at %location%"
		);
	}

	private Expression<Location> locationExpr;
	private Expression<ItemStack> itemExpr;

	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		itemExpr = (Expression<ItemStack>) expressions[0];
		locationExpr = (Expression<Location>) expressions[1];
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "morkazsk drop item " + itemExpr.toString(event, debug) + " at " + locationExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event e) {
		ItemStack itemStack = itemExpr.getSingle(e);
		Location location = locationExpr.getSingle(e);
		if (itemStack != null && location != null){
			location.getWorld().dropItem(location, itemStack);
		}
	}

}
