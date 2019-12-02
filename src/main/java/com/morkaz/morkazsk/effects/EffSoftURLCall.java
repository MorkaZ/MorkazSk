package com.morkaz.morkazsk.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
 
//call url %string%
public class EffSoftURLCall extends Effect{

	static{
		RegisterManager.registerEffect(
				EffSoftURLCall.class,
				"[mor.]call url %string%"
		);
	}

	private Expression<String> urlText;
	
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		urlText = (Expression<String>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@javax.annotation.Nullable
        protected void execute(Event e) {
			final Event evt = e;
			final String urlTxt = urlText.getSingle(evt);
			if (urlTxt == null){
				return;
			}
			new BukkitRunnable() {
				public void run() {
					   try {
				            URL url = new URL(urlTxt);
							url.openStream();
				        } catch(IOException exc) {
				            exc.printStackTrace();
				            Skript.warning("[SkMorkaz] There was been problem with call connection to URL: " +urlText.getSingle(evt)+". Please, check that URL is correctly.");
				        } 
				  }
			}.runTaskAsynchronously(MorkazSk.getInstance());
        }
 
}