package com.morkaz.morkazsk.effects;
 
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
 
public class EffSpawnParticle extends Effect{
       
	    private Expression<String> particle;
	    private Expression<Location> location;
	    private Expression<Number> offsetX;
	    private Expression<Number> offsetY;
	    private Expression<Number> offsetZ;
	    private Expression<Number> amount;
	    private Expression<Number> extra;
        
        @SuppressWarnings("unchecked")
		public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
	    		amount = (Expression<Number>) e[0];
	            particle = (Expression<String>) e[1];
	            extra = (Expression<Number>) e[2];
	            offsetX = (Expression<Number>) e[3];
	            offsetY = (Expression<Number>) e[4];
	            offsetZ = (Expression<Number>) e[5];
	            location = (Expression<Location>) e[6];
                return true;
        }
 
        public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
        	return null;
        }
 
        protected void execute(Event e) {
        	if (particle.getSingle(e) != null && location.getSingle(e) != null && offsetX.getSingle(e) != null && offsetY.getSingle(e) != null && offsetZ.getSingle(e) != null && extra.getSingle(e) != null){
            		location.getSingle(e).getWorld().spawnParticle(   
                		Particle.valueOf(particle.getSingle(e).toUpperCase()),
                		location.getSingle(e),
                		amount.getSingle(e).intValue(), 
                		offsetX.getSingle(e).doubleValue(), 
                		offsetY.getSingle(e).doubleValue(), 
                		offsetZ.getSingle(e).doubleValue(),
                		extra.getSingle(e).doubleValue());
        	}
        }
 
}