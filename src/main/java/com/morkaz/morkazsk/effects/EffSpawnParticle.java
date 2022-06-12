package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Event;

@Name("Spawn Particle at Location")
@Description({
		"This effect will spawn specified particle with given options. It will be spawned for everyone",
		"If chunk will be not loaded, it will not spawn particle, so it is safe and it will not affect your TPS that much like normal spawning may do.",
		"To force particles to be spawned even if the chunk is not loaded, use force mode.",
		"Use bukkit \"Particle\" enum names as particle name. You can use dots and name do not have to be upper cased.",
		"\"Redstone\" particles can be further customized by specifying a color and size.",
		"List of names is here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html"
})
@Examples({
		"#0.01 value is speed in this case.",
		"spawn 20 of \"flame\":0.01 offset by 0.2, 0.2, 0.2 at location 2 meter above player",
		"spawn 20 of \"flame\" offset by 0.2, 0.2, 0.2 with extra 0.01 at location 2 meter above player",
		"spawn 20 of \"flame\" offset by 0.2, 0.2, 0.2 with extra 0.01 at location 2 meter above player in force mode",
		"spawn 20 of \"redstone\" offset by 0.2, 0.2, 0.2 with extra 0.01 at location 2 meter above player in force mode with color 255, 0, 255 and size 1"
})
@Since("1.0")

public class EffSpawnParticle extends Effect {
	static {
		RegisterManager.registerEffect(
				EffSpawnParticle.class,
				"[morkazsk] (summon|play|create|activate|spawn) %number% [of] [particle] %string%:%number% offset (at|by|from) %number%, %number%(,| and) %number% at %location%",
				"[morkazsk] (summon|play|create|activate|spawn) %number% [of] [particle] %string% offset (at|by|from) %number%, %number%(,| and) %number% with extra [data] %number% at %location%",
				"[morkazsk] (summon|play|create|activate|spawn) %number% [of] [particle] %string% offset (at|by|from) %number%, %number%(,| and) %number% with extra [data] %number% at %location% in %string% mode",
				"[morkazsk] (summon|play|create|activate|spawn) %number% [of] [particle] %string% offset (at|by|from) %number%, %number%(,| and) %number% with extra [data] %number% at %location% in %string% mode with color %number%[,] %number%[,] %number% and size %number%");
	}

	private Expression<String> particleExpr;
	private Expression<Location> locationExpr;
	private Expression<Number> offsetXExpr;
	private Expression<Number> offsetYExpr;
	private Expression<Number> offsetZExpr;
	private Expression<Number> amountExpr;
	private Expression<Number> extraExpr;
	private Expression<String> modeExpr;
	private Expression<Number> redColorExpr;
	private Expression<Number> greenColorExpr;
	private Expression<Number> blueColorExpr;
	private Expression<Number> sizeExpr;
	private int pattern = 0;

	public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
		this.pattern = pattern;
		amountExpr = (Expression<Number>) e[0];
		particleExpr = (Expression<String>) e[1];
		if (pattern == 0) {
			extraExpr = (Expression<Number>) e[2];
			offsetXExpr = (Expression<Number>) e[3];
			offsetYExpr = (Expression<Number>) e[4];
			offsetZExpr = (Expression<Number>) e[5];
		} else if (pattern == 1) {
			offsetXExpr = (Expression<Number>) e[2];
			offsetYExpr = (Expression<Number>) e[3];
			offsetZExpr = (Expression<Number>) e[4];
			extraExpr = (Expression<Number>) e[5];
		}
		locationExpr = (Expression<Location>) e[6];
		modeExpr = (Expression<String>) e[7];
		redColorExpr = (Expression<Number>) e[8];
		greenColorExpr = (Expression<Number>) e[9];
		blueColorExpr = (Expression<Number>) e[10];
		sizeExpr = (Expression<Number>) e[11];
		return true;
	}

	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		if (pattern == 0) {
			return "spawn " + amountExpr.toString(event, debug) +
					" of " + particleExpr.toString(event, debug) +
					":" + extraExpr.toString(event, debug) +
					" offset by " + offsetXExpr.toString(event, debug) +
					", " + offsetYExpr.toString(event, debug) +
					", " + offsetZExpr.toString(event, debug) +
					" at location " + locationExpr.toString(event, debug);
		} else {
			return String.format(
					new String[] {
							"spawn %s of %s offset by %s, %s, %s with extra %s at location %s",
							"spawn %s of %s offset by %s, %s, %s with extra %s at location %s in %s mode",
							"spawn %s of %s offset by %s, %s, %s with extra %s at location %s in %s mode with color %s, %s, %s and size %s" }[pattern
									- 1],
					this.amountExpr.toString(event, debug),
					this.particleExpr.toString(event, debug),
					this.offsetXExpr.toString(event, debug),
					this.offsetYExpr.toString(event, debug),
					this.offsetZExpr.toString(event, debug),
					this.extraExpr.toString(event, debug),
					this.locationExpr.toString(event, debug),
					this.modeExpr.toString(event, debug),
					this.redColorExpr.toString(event, debug),
					this.greenColorExpr.toString(event, debug),
					this.blueColorExpr.toString(event, debug),
					this.sizeExpr.toString(event, debug));
		}
	}

	protected void execute(Event e) {
		Number amount = amountExpr.getSingle(e);
		String particleName = particleExpr.getSingle(e);
		Number extra = extraExpr.getSingle(e);
		Number offsetX = offsetXExpr.getSingle(e);
		Number offsetY = offsetYExpr.getSingle(e);
		Number offsetZ = offsetZExpr.getSingle(e);
		Location location = locationExpr.getSingle(e);
		String mode = (this.modeExpr == null) ? null : this.modeExpr.getSingle(e).toLowerCase();
		Number redColor = (this.redColorExpr == null) ? null : this.redColorExpr.getSingle(e);
		Number greenColor = (this.greenColorExpr == null) ? null : this.greenColorExpr.getSingle(e);
		Number blueColor = (this.blueColorExpr == null) ? null : this.blueColorExpr.getSingle(e);
		Number size = (this.sizeExpr == null) ? null : this.sizeExpr.getSingle(e);

		if (amount != null && particleName != null && extra != null && offsetX != null && offsetY != null
				&& offsetZ != null && location != null) {
			particleName = particleName.toUpperCase().replace(".", "_");

			if (!ToolBox.enumContains(Particle.class, particleName)) {
				return;
			}

			if (!mode.equals("force") && !location.getChunk().isLoaded()) {
				return; // to avoid performance lose.
			}

			Particle.DustOptions options = (redColor != null && greenColor != null && blueColor != null && size != null)
					? new Particle.DustOptions(
							Color.fromRGB(redColor.intValue(), greenColor.intValue(), blueColor.intValue()),
							size.floatValue())
					: null;

			location.getWorld().spawnParticle(
					Particle.valueOf(particleName),
					location,
					amount.intValue(),
					offsetX.doubleValue(),
					offsetY.doubleValue(),
					offsetZ.doubleValue(),
					extra.doubleValue(),
					options, !mode.equals("normal"));
		}
	}

}
