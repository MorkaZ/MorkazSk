package com.morkaz.morkazsk.optionals.sqlibrary.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import com.morkaz.morkazsk.MorkazSk;
import org.bukkit.Bukkit;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;

public class MysqlStaticManager {
  
	public static HashMap<String, ConnectionData> mysqlConnectData = new HashMap<String, ConnectionData>();
	public static HashMap<String, Database> mysqlConnections = new HashMap<String, Database>();	
	
	public static void setHost(String id, String host){
		if (!mysqlConnectData.containsKey(id)){
			mysqlConnectData.put(id, new ConnectionData());
		}
		mysqlConnectData.get(id).setHost(host);
	}
	
	public static void setPassword(String id, String password){
		if (!mysqlConnectData.containsKey(id)){
			mysqlConnectData.put(id, new ConnectionData());
		}
		mysqlConnectData.get(id).setPassword(password);
	}
	
	public static void setUser(String id, String user){
		if (!mysqlConnectData.containsKey(id)){
			mysqlConnectData.put(id, new ConnectionData());
		}
		mysqlConnectData.get(id).setUser(user);
	}
	
	public static void setDatabase(String id, String database){
		if (!mysqlConnectData.containsKey(id)){
			mysqlConnectData.put(id, new ConnectionData());
		}
		mysqlConnectData.get(id).setDatabase(database);
	}
	
	public static void setPort(String id, int port){
		if (!mysqlConnectData.containsKey(id)){
			mysqlConnectData.put(id, new ConnectionData());
		}
		mysqlConnectData.get(id).setPort(port);
	}
	
	public static void disconnect(String id){
		if (mysqlConnections.containsKey(id)){
			mysqlConnections.get(id).close();
			mysqlConnections.remove(id);
		}
	}
	
	public static void safeUpdate(String id, String query){
		if (!mysqlConnections.containsKey(id)){
			return;
		}
		Database database = mysqlConnections.get(id);
		try {
			database.query(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void update(String id, final String query){
		if (!mysqlConnections.containsKey(id)){
			return;
		}
		final Database database = mysqlConnections.get(id);
		MorkazSk.getInstance().getAsyncMysqlScheduler().scheduleRunnable(new Runnable() {
			public void run() {
				try {
					database.query(query);
				} catch (SQLException e) {
					e.printStackTrace();
					Bukkit.getConsoleSender().sendMessage("MySQL query error: "+e.getErrorCode());
				}
			}
		});
	}
	
	public static boolean isConnected(String id){
		if (mysqlConnections.containsKey(id)){
			return true;
		}
		return false;
	}
	
	public static ResultSet getResult(String id, String query){
		if (!mysqlConnections.containsKey(id)){
			return null;
		}
		ResultSet set = null;
		Database database = mysqlConnections.get(id);
		if (!database.isOpen() || !database.isConnected()) {
			return null;
		}
		try {
			set = database.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("MySQL get data from query error: "+e.getErrorCode());
		}
		return set;
	}
	
	public static String connect(String id){
		if (!mysqlConnectData.containsKey(id)){
			return "NO_CONNECTION_DATA";
		}
		ConnectionData data = mysqlConnectData.get(id);
		if (data.getHost() == null){
			return "NO_HOST";
		}
		if (data.getHost() == null){
			return "NO_PORT";
		}
		if (data.getPassword() == null){
			return "NO_PASSWORD";
		}
		if (data.getUser() == null){
			return "NO_USER";
		}
		if (data.getDatabase() == null){
			return "NO_DATABASE";
		}
		Database sql = new MySQL(Logger.getLogger("Minecraft"), 
	            "[SkMorkazMySQL-" + id + "] ", 
	            data.getHost(), 
	            data.getPort(), 
	            data.getDatabase(), 
	            data.getUser(), 
	            data.getPassword());
		sql.open();
		if (sql.isOpen()){
			if (mysqlConnections.containsKey(id)){
				Database db = mysqlConnections.get(id);
				if (db != null){
					db.close();
				}
			}
			mysqlConnections.put(id, sql);
			return "SUCCESS";
		} else {
			return "FAILED";
		}
	}
}


