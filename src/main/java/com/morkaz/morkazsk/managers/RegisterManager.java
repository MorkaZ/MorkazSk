package com.morkaz.morkazsk.managers;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Date;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.conditions.CondIsEntityHavingPotionEffect;
import com.morkaz.morkazsk.effects.EffBreakBlock;
import com.morkaz.morkazsk.effects.EffPlaySound;
import com.morkaz.morkazsk.effects.EffPushEntityFromLocation;
import com.morkaz.morkazsk.effects.EffSpawnParticle;
import com.morkaz.morkazsk.events.*;
import com.morkaz.morkazsk.events.listeners.BlockFallListener;
import com.morkaz.morkazsk.events.listeners.BlockPistonMoveListener;
import com.morkaz.morkazsk.expressions.dedicated.ExprFishingCaughtEntity;
import com.morkaz.morkazsk.expressions.dedicated.ExprFishingHook;
import com.morkaz.morkazsk.expressions.dedicated.ExprFishingState;
import com.morkaz.morkazsk.expressions.universal.*;
import com.morkaz.morkazsk.managers.data.*;
import com.morkaz.morkazsk.misc.AnsiColors;
import com.morkaz.morkazsk.optionals.protocollib.EffPlaySoundForPlayer;
import com.morkaz.morkazsk.optionals.protocollib.EffShowParticleToPlayer;
import jdk.internal.jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterManager {

	private static List<ExpressionData> expressionDataList = new ArrayList<>();
	private static List<ConditionData> conditionDataList = new ArrayList<>();
	private static List<EventData> eventDataList = new ArrayList<>();
	private static List<EffectData> effectDataList = new ArrayList<>();
	private static List<TypeData> typeDataList = new ArrayList<>();

	private static Integer expressionsCount = 0, effectsCount = 0, conditionCount = 0, eventCount = 0, typesCount = 0, eventValuesCount = 0;

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
			if (e.getMessage().contains("is already registered")){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cType: &d\""+classInfo.getC().getName()+"\" &cis already registered. Dismissing loading of this type.&r"));
			} else {
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading type: \"&f"+classInfo.getC().getName()+"\"&c. " +
						"Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this problem here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
				e.printStackTrace();
			}
		}
		return false;
	}

	@Nullable
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

	@Nullable
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
		MorkazSk instance = MorkazSk.getInstance();
		if (instance == null){
			return;
		}
		instance.getServer().getPluginManager().registerEvents(new BlockFallListener(), instance);
		instance.getServer().getPluginManager().registerEvents(new BlockPistonMoveListener(), instance);
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
