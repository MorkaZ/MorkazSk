package com.morkaz.morkazsk.effects;
 
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
 
public class EffPushEntityFromLocation extends Effect{

		private Expression<LivingEntity> entity;
		private Expression<Location> location;


		@SuppressWarnings("unchecked")
		@Override
		public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
				entity = (Expression<LivingEntity>) e[0];
				location = (Expression<Location>) e[1];
				return true;
		}
 
		@Override
		public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
				return null;
		}
 
		@Override
		protected void execute(Event event) {
			LivingEntity p = (LivingEntity)this.entity.getSingle(event);
			Location n = (Location)this.location.getSingle(event);
			if ((p == null) || (n == null)) {
				return;
			}
			Vector direction = p.getLocation().toVector().subtract(n.toVector()).normalize();
			p.setVelocity(direction);
		}
 
}