package com.morkaz.morkazsk.managers;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Date;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.expressions.*;
import com.morkaz.morkazsk.managers.data.ConditionData;
import com.morkaz.morkazsk.managers.data.EffectData;
import com.morkaz.morkazsk.managers.data.EventData;
import com.morkaz.morkazsk.managers.data.ExpressionData;
import com.morkaz.morkazsk.misc.AnsiColors;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RegisterManager {

	public static List<ExpressionData> expressionDataList = new ArrayList<>();
	public static List<ConditionData> conditionDataList = new ArrayList<>();
	public static List<EventData> eventDataList = new ArrayList<>();
	public static List<EffectData> effectDataList = new ArrayList<>();

	public static void defineEvents(){
		eventDataList.clear();
	}

	public static void defineConditions(){
		conditionDataList.clear();
	}

	public static void defineEffects(){
		effectDataList.clear();
	}

	public static void defineExpressions(){
		expressionDataList.clear();
		expressionDataList.add(new ExpressionData(
				ExprDropOfBlock.class, ItemStack.class, ExpressionType.SIMPLE,
				"[mor.]drops of %block%",
				"[mor.]%drops of %block% (with|using) [tool] %itemstack%"
		));
		expressionDataList.add(new ExpressionData(
				ExprCursorItemOfPlayer.class, ItemStack.class, ExpressionType.SIMPLE, "[mor.]cursor item of %player%"
		));
		expressionDataList.add(new ExpressionData(
				ExprLastLoginOfOfflinePlayer.class, Date.class, ExpressionType.SIMPLE, "[mor.]last[ ](login|played[ date]) of %offlineplayer%"
		));
		expressionDataList.add(new ExpressionData(
				ExprLastLoginOfPlayer.class, Date.class, ExpressionType.SIMPLE, "[mor.]last[ ](login|played[ date]) of %player%"
		));
		expressionDataList.add(new ExpressionData(
				ExprDateFromUnix.class, Date.class, ExpressionType.SIMPLE, "[mor.]date from (unix|timestamp|milis) %number%"
		));
		expressionDataList.add(new ExpressionData(
				ExprUnixFromDate.class, Number.class, ExpressionType.SIMPLE, "[mor.][unix|timestamp|milis) (from|of) [date] %date%"
		));
	}


	public static void registerAll(){
		registerEvents();
		registerConditions();
		registerEffects();
		registerExpressions();
	}

	public static void registerExpressions(){
		int counter = 0;
		for (ExpressionData data : expressionDataList){
			try {
				Skript.registerExpression(data.getExpressionClass(), data.getReturnValueClass(), data.getExpressionType(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eExpressions!"));
	}

	public static void registerEvents(){
		int counter = 0;
		for (EventData data : eventDataList){
			try {
				Skript.registerEvent(data.getEventName(), data.getSkriptEventType(), data.getEvents(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eEvents!"));
	}

	public static void registerEffects(){
		int counter = 0;
		for (EffectData data : effectDataList){
			try {
				Skript.registerEffect(data.getEffectClass(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eEffects!"));
	}

	public static void registerConditions(){
		int counter = 0;
		for (ConditionData data : conditionDataList){
			try {
				Skript.registerCondition(data.getConditionClass(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eConditions!"));
	}


}
