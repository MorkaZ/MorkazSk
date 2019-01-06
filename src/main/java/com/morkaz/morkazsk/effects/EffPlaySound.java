package com.morkaz.morkazsk.effects;
 
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.Event;
 
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
 
//[mor.]play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number%
public class EffPlaySound extends Effect{
       
        private Expression<String> sound;
        private Expression<Number> pitch;
        private Expression<Number> volume;
        private Expression<Location> location;
       

        @SuppressWarnings("unchecked")
		@Override
        public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
                sound = (Expression<String>) e[0];
                location = (Expression<Location>) e[1];
                pitch = (Expression<Number>) e[2];
                volume = (Expression<Number>) e[3];
                return true;
        }
 
        @Override
        public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
                return null;
        }
 
        @Override
        protected void execute(Event e) {
        	if (sound.getSingle(e) != null && pitch.getSingle(e) != null && volume.getSingle(e) != null && location.getSingle(e) != null){
            	location.getSingle(e).getWorld().playSound(location.getSingle(e), 
            			Sound.valueOf(sound.getSingle(e)),
            			volume.getSingle(e).floatValue(),
            			pitch.getSingle(e).floatValue());
        	}
        }
        
 
}