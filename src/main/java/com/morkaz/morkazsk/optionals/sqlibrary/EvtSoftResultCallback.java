package com.morkaz.morkazsk.optionals.sqlibrary;

import java.sql.ResultSet;
import java.sql.SQLException;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.events.EvtJump;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtSoftResultCallback extends Event{

	static {
		RegisterManager.registerEvent(
				"mor.mysql callback",
				SimpleEvent.class,
				new Class[]{EvtSoftResultCallback.class},
				"[mor.](query|mysql) callback"
		);
		// event ResultSet
		RegisterManager.registerEventValue(
				EvtSoftResultCallback.class,
				ResultSet.class,
				new Getter<ResultSet, EvtSoftResultCallback>() {
					@Override
					public ResultSet get(EvtSoftResultCallback evt) {
						return evt.getResultSet();
					}
				}
		);
		// Index (ID)
		RegisterManager.registerEventValue(
				EvtSoftResultCallback.class,
				String.class,
				new Getter<String, EvtSoftResultCallback>() {
					@Override
					public String get(EvtSoftResultCallback evt) {
						return evt.getIndex();
					}
				}
		);
	}
	
	private static final HandlerList handlers = new HandlerList();
	private ResultSet set;
	private String index;
	
	public EvtSoftResultCallback(String index, ResultSet set){
		try {
			set.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.set = set;
		this.index = index;
	}
	
	public ResultSet getResultSet() {
		return set;
	}

	public String getIndex() {
		return index;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	
}
