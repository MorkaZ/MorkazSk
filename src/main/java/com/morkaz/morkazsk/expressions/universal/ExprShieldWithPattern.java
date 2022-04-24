package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Name("Shield with Banner")
@Description({
		"Return new itemstack which is shield with given banner pattern."
})
@Since("1.3-beta3")

public class ExprShieldWithPattern extends SimpleExpression<ItemStack> {

	static {
		RegisterManager.registerExpression(
				ExprShieldWithPattern.class,
				ItemStack.class,
				ExpressionType.SIMPLE,
				"[morkazsk] shield with banner pattern of %itemstack%"
		);
	}

	Expression<ItemStack> bannerExpr;

	public boolean isSingle() {
		return true;
	}

	public String toString(Event event, boolean debug) {
		return "[morkazsk] shield with banner pattern of " + bannerExpr.toString(event, debug);
	}

	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.bannerExpr = (Expression<ItemStack>) expressions[0];
		return true;
	}

	protected ItemStack[] get(Event event) {
		ItemStack bannerItem = bannerExpr.getSingle(event);

		if (bannerItem != null) {
			BannerMeta bannerItemMeta = (BannerMeta)bannerItem.getItemMeta();
			List<Pattern> patterns = bannerItemMeta.getPatterns();

			ItemStack shield = new ItemStack(Material.SHIELD, 1);
			BlockStateMeta bmeta = (BlockStateMeta)shield.getItemMeta();
			Banner banner = (Banner)bmeta.getBlockState();
			banner.setBaseColor(bannerItemMeta.getBaseColor());
			banner.update();
			for (Pattern pattern : patterns){
				banner.addPattern(pattern);
				banner.update();
			}
			bmeta.setBlockState((BlockState)banner);
			shield.setItemMeta((ItemMeta)bmeta);
			return new ItemStack[]{shield};

//			ItemMeta meta = shieldItem.getItemMeta();
//			BlockStateMeta bmeta = (BlockStateMeta) meta;
//			Banner banner = (Banner) bmeta.getBlockState();
//			Bukkit.broadcastMessage("przed "+banner);
//			banner.setPatterns(patterns);
//			banner.update();
//			Bukkit.broadcastMessage("po "+banner);
//			Bukkit.broadcastMessage("bmeta: "+bmeta);
//			Bukkit.broadcastMessage("has block state: "+bmeta.hasBlockState());
//			bmeta.setBlockState(banner);
//			shieldItem.setItemMeta(bmeta);
//			return new ItemStack[]{shieldItem};
		}
		return new ItemStack[]{};
	}
}