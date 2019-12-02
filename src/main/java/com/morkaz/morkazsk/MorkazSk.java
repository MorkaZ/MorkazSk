package com.morkaz.morkazsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.AnsiColors;
import com.morkaz.morkazsk.misc.Metrics;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.AsyncMysqlScheduler;
import com.morkaz.morkazsk.optionals.sqlibrary.misc.MysqlConnectionChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MorkazSk extends JavaPlugin {

	private Metrics metrics;
	private static MorkazSk main;
	private SkriptAddon skriptAddon;
	private AsyncMysqlScheduler mysqlScheduler;

	private File configFile;

	@Override
	public void onEnable() {
		//Set Instance
		main = this;

		//Add Metrics
		metrics = new Metrics(this);

		//Load & Save Config
		saveDefaultConfig();
		reloadConfig();

		//Register addon into Skript
		this.skriptAddon = Skript.registerAddon(this);

		//Register elements classes to run their static blocks
		RegisterManager.loadElementsClasses();

		//Register listeners
		RegisterManager.registerListeners();

		//Output elements counts
		RegisterManager.displayCounts();

		if (Bukkit.getPluginManager().isPluginEnabled("SQLibrary")){
			if (getConfig().getBoolean("elements.load-old-skmorkaz-sqlibrary-elements")){
				mysqlScheduler = new AsyncMysqlScheduler(this);
				mysqlScheduler.startScheduler();
				new MysqlConnectionChecker();
			}
		}

		//Ending
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9[" + getDescription().getName() + "] &aPlugin enabled!&r"));
	}

	@Override
	public void onDisable() {
		//Ending
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9[" + getDescription().getName() + "] &ePlugin disabled!&r"));
	}

	public AsyncMysqlScheduler getAsyncMysqlScheduler() {
		return mysqlScheduler;
	}

	public static MorkazSk getInstance() {
		return main;
	}

	public SkriptAddon asSkriptAddon() {
		return skriptAddon;
	}

}
