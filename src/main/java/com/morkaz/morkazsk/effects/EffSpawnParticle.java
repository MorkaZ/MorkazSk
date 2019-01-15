package com.morkaz.morkazsk.effects;
 
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Spawn Particle at Location")
@Description({"This effect will spawn specified particle with given options. It will be spawned for everyone",
		"If chunk will be not loaded, it will not spawn particle, so it is safe and it will not affect your TPS that much like normal spawning may do.",
		"Use bukkit \"Particle\" enum names as particle name. You can use dots and name do not have to be upper cased.",
		"List of names is here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html"
})
@Examples({
		"spawn 20 of \"flame\":0.01 offset by 0.2, 0.2, 0.2 at location 2 meter above player"
})
@Since("1.0")

public class EffSpawnParticle extends Effect{

	static {
		RegisterManager.registerEffect(
				EffSpawnParticle.class,
				"([morkazsk ]|[mor.])(summon|play|create|activate|spawn) %number% [of] [particle] %string%:%number% offset (at|by|from) %number%, %number%(,| and) %number% at %location%",
				"([morkazsk ]|[mor.])(summon|play|create|activate|spawn) %number% [of] [particle] %string% offset (at|by|from) %number%, %number%(,| and) %number% with extra [data] %number% at %location%"
		);
	}
       
	private Expression<String> particleExpr;
	private Expression<Location> locationExpr;
	private Expression<Number> offsetXExpr;
	private Expression<Number> offsetYExpr;
	private Expression<Number> offsetZExpr;
	private Expression<Number> amountExpr;
	private Expression<Number> extraExpr;
	private int pattern = 0;

	public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
		this.pattern = pattern;
		if (pattern == 0){
			amountExpr = (Expression<Number>) e[0];
			particleExpr = (Expression<String>) e[1];
			extraExpr = (Expression<Number>) e[2];
			offsetXExpr = (Expression<Number>) e[3];
			offsetYExpr = (Expression<Number>) e[4];
			offsetZExpr = (Expression<Number>) e[5];
			locationExpr = (Expression<Location>) e[6];
		} else if (pattern == 1){
			amountExpr = (Expression<Number>) e[0];
			particleExpr = (Expression<String>) e[1];
			offsetXExpr = (Expression<Number>) e[2];
			offsetYExpr = (Expression<Number>) e[3];
			offsetZExpr = (Expression<Number>) e[4];
			extraExpr = (Expression<Number>) e[5];
			locationExpr = (Expression<Location>) e[6];
		}
		return true;
	}

	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		if (pattern == 0){
			return "spawn " + amountExpr.toString(event, debug)+
					" of " + particleExpr.toString(event, debug) +
					":" + extraExpr.toString(event, debug) +
					" offset by " + offsetXExpr.toString(event, debug) +
					", " + offsetYExpr.toString(event, debug) +
					", " + offsetZExpr.toString(event, debug) +
					" at location " + locationExpr.toString(event, debug);
		} else {
			return "spawn " + amountExpr.toString(event, debug)+
					" of " + particleExpr.toString(event, debug) +
					" offset by " + offsetXExpr.toString(event, debug) +
					", " + offsetYExpr.toString(event, debug) +
					", " + offsetZExpr.toString(event, debug) +
					" with extra " + extraExpr.toString(event, debug) +
					" at location " + locationExpr.toString(event, debug);
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
		if (amount != null && particleName != null && extra != null && offsetX != null && offsetY != null && offsetZ != null && location != null){
			particleName = particleName.toUpperCase().replace(".", "_");
			if (!ToolBox.enumContains(Particle.class, particleName)){
				return;
			}
			if (!location.getChunk().isLoaded()){
				return; // to avoid performance lose.
			}
			location.getWorld().spawnParticle(
					Particle.valueOf(particleName),
					location,
					amount.intValue(),
					offsetX.doubleValue(),
					offsetY.doubleValue(),
					offsetZ.doubleValue(),
					extra.doubleValue()
			);
		}
	}
 
}