package com.morkaz.morkazsk.managers;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.*;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.events.listeners.BlockFallListener;
import com.morkaz.morkazsk.events.listeners.BlockPistonMoveListener;
import com.morkaz.morkazsk.events.listeners.PlayerChunkEnterListener;
import com.morkaz.morkazsk.events.listeners.PlayerJumpListener;
import com.morkaz.morkazsk.misc.AnsiColors;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterManager {

	private static Integer expressionsCount = 0, effectsCount = 0, conditionCount = 0, eventCount = 0, typesCount = 0, eventValuesCount = 0;

	public static void loadElementsClasses() {
		MorkazSk plugin = MorkazSk.getInstance();
		try {
			plugin.asSkriptAddon().loadClasses("com.morkaz.morkazsk.expressions", "universal", "dedicated");
			plugin.asSkriptAddon().loadClasses("com.morkaz.morkazsk.events");
			plugin.asSkriptAddon().loadClasses("com.morkaz.morkazsk.conditions");
			plugin.asSkriptAddon().loadClasses("com.morkaz.morkazsk.effects");
			List<String> optionals = new ArrayList<>();
			if (Bukkit.getPluginManager().getPlugin("MoxCore") != null){
				optionals.add("moxcore");
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5MoxCore &6additional elements scheduled to load!&r"));
			}
			if (Bukkit.getPluginManager().getPlugin("MoxTokensDatabase") != null){
				optionals.add("moxtokensdatabase");
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5MoxTokensDatabase &6additional elements scheduled to load!&r"));
			}
			if (Bukkit.getPluginManager().getPlugin("MoxPremiumShop") != null){
				optionals.add("moxpremiumshop");
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5MoxPremiumShop &6additional elements scheduled to load!&r"));
			}
			if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null){
				optionals.add("protocollib");
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5ProtocolLib &6additional elements scheduled to load!&r"));
			}
			if (Bukkit.getPluginManager().getPlugin("GlowAPI") != null){
				optionals.add("glowapi");
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5GlowAPI &6additional elements scheduled to load!&r"));
			}
			if (Bukkit.getPluginManager().getPlugin("MoxPerms") != null){
				optionals.add("moxperms");
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5MoxPerms &6additional elements scheduled to load!&r"));
			}
			if (Bukkit.getPluginManager().getPlugin("SQLibrary") != null){
				if (MorkazSk.getInstance().getConfig().getBoolean("elements.load-old-skmorkaz-sqlibrary-elements")){
					optionals.add("sqlibrary");
					Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &5SQLibrary &6additional elements scheduled to load!&r"));
				}
			}
			if (optionals.size() > 0){
				plugin.asSkriptAddon().loadClasses("com.morkaz.morkazsk.optionals", optionals.toArray(new String[optionals.size()]));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <E extends Expression, V> Boolean registerExpression(Class<E> expressionClass, Class<V> returnType, ExpressionType expressionType, String... patterns){
		try {
			Skript.registerExpression(expressionClass, returnType, expressionType, patterns);
			expressionsCount++;
			return true;
		} catch (Exception e){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f\""+patterns[0]+"\"&c. " +
					"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
			e.printStackTrace();
		}
		return false;
	}

	public static Boolean registerEffect(Class<? extends Effect> effectClass, String... patterns){
		try {
			Skript.registerEffect(effectClass, patterns);
			effectsCount++;
		} catch (Exception e){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f\""+patterns[0]+"\"&c. " +
					"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this problem here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
			e.printStackTrace();
		}
		return false;
	}

	public static Boolean registerCondition(Class<? extends Condition> conditionClass, String... patterns){
		try {
			Skript.registerCondition(conditionClass, patterns);
			conditionCount++;
			return true;
		} catch (Exception e){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: \"&f"+patterns[0]+"\"&c. " +
					"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this problem here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
			e.printStackTrace();
		}
		return false;
	}

	public static <T> Boolean registerType(ClassInfo<T> classInfo){
		try {
			Classes.registerClass(classInfo);
			typesCount++;
			return true;
		} catch (Exception e){
			if (e.getMessage().contains("is already registered") || e.getMessage().contains("is already used")){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cType: &d\""+classInfo.getC().getName()+"\" &cis already registered. Dismissing loading of this type. Registered name: \""+Classes.getExactClassInfo(classInfo.getC()).getName()+"\".&r"));
			} else {
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading type: \"&f"+classInfo.getC().getName()+"\"&c. " +
						"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this problem here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
				e.printStackTrace();
			}
		}
		return false;
	}

	public static SkriptEventInfo registerEvent(String eventName, Class<? extends SkriptEvent> eventType, Class<? extends Event>[] events, String[] patterns, String[] description, String[] examples, String[] requiredPlugins, String since, String documentationID){
		try {
			SkriptEventInfo eventInfo = Skript.registerEvent(eventName, eventType, events, patterns);
			if (description != null){
				eventInfo.description(description);
			}
			if (examples != null){
				eventInfo.examples(examples);
			}
			if (since != null){
				eventInfo.since(since);
			}
			if (documentationID != null){
				eventInfo.documentationID(documentationID);
			}
			if (requiredPlugins != null){
				eventInfo.requiredPlugins(requiredPlugins);
			}
			eventCount++;
			return eventInfo;
		} catch (Exception e){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+patterns[0]+"&c. " +
					"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this problem here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
			e.printStackTrace();
		}
		return null;
	}

	public static SkriptEventInfo registerEvent(String eventName, Class<? extends SkriptEvent> eventType, Class<? extends Event>[] events, String... patterns){
		return registerEvent(eventName, eventType, events, patterns, null, null, null, null, null);
	}

	public static <E extends Event, V> Boolean registerEventValue(Class<E> eventClass, Class<V> valueClass, Getter<V, E> getter){
		try {
			EventValues.registerEventValue(eventClass, valueClass, getter, 0);
			eventValuesCount++;
			return true;
		} catch (Exception e){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading event value: &f\""+valueClass.getName()+"\"&c of event: &f\""+eventClass.getName()+"\"&c. " +
					"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this problem here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
			e.printStackTrace();
		}
		return false;
	}

	public static void registerListeners(){
		MorkazSk main = MorkazSk.getInstance();
		if (main == null){
			return;
		}
		main.getServer().getPluginManager().registerEvents(new BlockFallListener(), main);
		main.getServer().getPluginManager().registerEvents(new BlockPistonMoveListener(), main);
		if (main.getConfig().getBoolean("elements.jump-event")){
			main.getServer().getPluginManager().registerEvents(new PlayerJumpListener(), main);
		}
		if (main.getConfig().getBoolean("elements.chunk-enter-event")){
			main.getServer().getPluginManager().registerEvents(new PlayerChunkEnterListener(), main);
		}
	}

	public static void displayCounts(){
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &f---------------------------------------------------&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eAmounts of registered elements:&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &a"+eventCount+" &eEvents and &a"+eventValuesCount+" &eEvent Values&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &a"+conditionCount+" &eConditions&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &a"+effectsCount+" &eEffects&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &a"+expressionsCount+" &eExpressions&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &a"+typesCount+" &eTypes&r"));
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &f---------------------------------------------------&r"));
	}

}
