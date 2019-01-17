package com.morkaz.morkazsk.effects;
 
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.Event;
 
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Play Sound at Location")
@Description({
		"It will play sound at specific locationExpr with given pitchExpr and volumeExpr for everyone.",
		"Use bukkit \"Sound\" enum names as sound name.",
		"List of names is here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html",
		"Bukkit names may be in any case heigh and may be separated with \".\" instead of \"_\"."
})
@Examples({
		"on rightclick:",
		"\tmorkazsk play sound \"entity_bat_death\" at player with pitch 2.0 and volume 2.0 #Make sure to prefix effects that may be identical as other addons syntaxes",
})
@Since("1.0")

public class EffPlaySound extends Effect{

	static{
		RegisterManager.registerEffect(
				EffPlaySound.class,
				"([morkazsk ]|[mor.])play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number%"
		);
	}
       
	private Expression<String> soundExpr;
	private Expression<Number> pitchExpr;
	private Expression<Number> volumeExpr;
	private Expression<Location> locationExpr;


	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, ParseResult parseResult) {
		soundExpr = (Expression<String>) expressions[0];
		locationExpr = (Expression<Location>) expressions[1];
		pitchExpr = (Expression<Number>) expressions[2];
		volumeExpr = (Expression<Number>) expressions[3];
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "play sound " + soundExpr.toString(event, debug) +
				" at " + locationExpr.toString(event, debug) +
				" with pitch " + pitchExpr.toString(event, debug) +
				" and volume " + volumeExpr.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		String sound = soundExpr.getSingle(event);
		Location location = locationExpr.getSingle(event);
		if (sound == null || location == null){
			return;
		}
		sound = sound.toUpperCase().replace(".", "_");
		if (!ToolBox.enumContains(Sound.class, sound)){
			Skript.error("[MorkazSk] Sound: \t"+sound+"\t does not exist in bukkit enums.");
			return;
		}
		Number pitch = pitchExpr.getSingle(event) == null ? 1.0f : pitchExpr.getSingle(event);
		Number volume = volumeExpr.getSingle(event) == null ? 1.0f : volumeExpr.getSingle(event);
		location.getWorld().playSound(
				location,
				Sound.valueOf(sound),
				volume.floatValue(),
				pitch.floatValue()
		);
	}

 
}