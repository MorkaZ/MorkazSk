package com.morkaz.morkazsk;

import ch.njol.skript.Skript;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.AnsiColors;
import com.morkaz.morkazsk.misc.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MorkazSk extends JavaPlugin {

	private Metrics metrics;
	private static MorkazSk main;

	@Override
	public void onEnable() {
		//Set Instance
		main = this;

		//Add Metrics
		metrics = new Metrics(this);

		//Register addon into Skript
		Skript.registerAddon(this);

		//Register listeners
		RegisterManager.registerListeners();

		//Output elements counts
		RegisterManager.displayCounts();

		//Ending
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9[" + getDescription().getName() + "] &aPlugin enabled!&r"));
	}

	@Override
	public void onDisable() {
		//Ending
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9[" + getDescription().getName() + "] &ePlugin disabled!&r"));
	}

	public static MorkazSk getInstance() {
		return main;
	}


}
