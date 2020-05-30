package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

@Name("Potion Effect Types of Potion Item")
@Description({
		"Returns all potion effect types in potion."
})
@Examples({
		"set {_potion.effects::*} to potion effects of player's tool"
})
@Since("1.2-beta3")

public class ExprPotionEffectsOfPotion  extends SimpleExpression<PotionEffectType> {

	static {
		RegisterManager.registerExpression(
				ExprPotionEffectsOfPotion.class,
				PotionEffectType.class,
				ExpressionType.SIMPLE,
				"[morkazsk] [all] potion effects of %itemstack%"
		);
	}

	Expression<ItemStack> itemExpr;
	int pattern = 0;

	public boolean isSingle() {
		return false;
	}

	public String toString(Event event, boolean debug) {
		return "morkazsk potion effects of " + itemExpr.toString(event, debug);
	}

	public Class<? extends PotionEffectType> getReturnType() {
		return PotionEffectType.class;
	}

	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.itemExpr = (Expression<ItemStack>)expressions[0];
		return true;
	}

	protected PotionEffectType[] get(Event event) {
		ItemStack item = itemExpr.getSingle(event);
		if (item != null){
			ItemMeta itemMeta = item.getItemMeta();
			PotionMeta potionMeta =  (PotionMeta) itemMeta;
			List<PotionEffectType> potionEffectTypes = new ArrayList<>();
			for (PotionEffect potionEffect : potionMeta.getCustomEffects()){
				potionEffectTypes.add(potionEffect.getType());
			}
			return potionEffectTypes.toArray(new PotionEffectType[potionEffectTypes.size()]);
		}
		return new PotionEffectType[]{};
	}


}
