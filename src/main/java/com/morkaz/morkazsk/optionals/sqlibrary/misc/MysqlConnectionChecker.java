package com.morkaz.morkazsk.optionals.sqlibrary.misc;

import com.morkaz.morkazsk.MorkazSk;
import lib.PatPeter.SQLibrary.Database;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;

public class MysqlConnectionChecker {
	
	private BukkitTask task;

	public MysqlConnectionChecker(){
		this.task = Bukkit.getScheduler().runTaskTimer(MorkazSk.getInstance(), new Runnable(){
			@Override
			public void run() {
				if (MysqlStaticManager.mysqlConnections != null){
					for (String key : MysqlStaticManager.mysqlConnections.keySet()){
						Database database = MysqlStaticManager.mysqlConnections.get(key);
						if (database == null){ // null value of "key".
							String result = MysqlStaticManager.connect(key);
							Bukkit.getLogger().warning("[MorkazSk] Program problem of mysql connection: "+key+". Trying to create new connection!");
							if (result.equalsIgnoreCase("SUCCESS")){
								Bukkit.getLogger().warning("[MorkazSk] MySQL connection named: "+key+" has been fixed! It is now stable!");
							} else {
								Bukkit.getLogger().warning("[MorkazSk] Problem ocoured while trying to create MySQL connection named: "+key+"! Result of try: "+result);
							}
							continue;
						}
						try {
							if (!database.isOpen() || database.getConnection().isClosed()){
								MysqlStaticManager.connect(key);
								Bukkit.getLogger().warning("[MorkazSk] MySQL connection of "+key+" was been closed. Opening new connection!");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, 100, 100);
	}
	
	public void cancelTask(){
		task.cancel();
	}
	
}
