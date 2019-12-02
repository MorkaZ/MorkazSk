package com.morkaz.morkazsk.optionals.sqlibrary.misc;

import java.util.ArrayList;

import com.morkaz.morkazsk.MorkazSk;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AsyncMysqlScheduler {
	
	private MorkazSk main;
	private ArrayList<Runnable> runnableList = new ArrayList<Runnable>();
	private Boolean processing = false;
	private BukkitTask schedulerTask;
	
	
	public AsyncMysqlScheduler(MorkazSk main) {
		this.main = main;
	}
	
	public void cancelSchedulerTask() {
		this.schedulerTask.cancel();
	}
	
	public BukkitTask getSchedulerTask() {
		return this.schedulerTask;
	}
	
	public void reloadScheduler() {
		schedulerTask.cancel();
		runnableList.clear();
		processing = false;
		startScheduler();
	}
	
	public void scheduleRunnable(Runnable runnable) {
		if (processing == false) {
			runnableList.add(runnable);
		} else {
			main.getServer().getScheduler().runTaskLater(main, new Runnable() {
				@Override
				public void run() {
					runnableList.add(runnable);
				}
			}, 1L);
		}
	}
	
	public BukkitTask startScheduler() {
		this.schedulerTask = new BukkitRunnable() {
			@Override
			public void run() {
				if (!processing) {
					processing = true;
					ArrayList<Runnable> copiedRunnableList = (ArrayList<Runnable>)runnableList.clone();
					runnableList.clear();
					for (Runnable runnable : copiedRunnableList) {
						if (runnable != null){
							try {
								runnable.run();
							} catch (Exception e){
								e.printStackTrace();
							}
						}
					}
					processing = false;
				}
			}
		}.runTaskTimerAsynchronously(main, 0L, 2L);
		return schedulerTask;
	}

}
