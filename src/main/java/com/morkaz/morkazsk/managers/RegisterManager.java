package com.morkaz.morkazsk.managers;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Date;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.effects.EffPlaySound;
import com.morkaz.morkazsk.effects.EffSpawnParticle;
import com.morkaz.morkazsk.expressions.*;
import com.morkaz.morkazsk.managers.data.ConditionData;
import com.morkaz.morkazsk.managers.data.EffectData;
import com.morkaz.morkazsk.managers.data.EventData;
import com.morkaz.morkazsk.managers.data.ExpressionData;
import com.morkaz.morkazsk.misc.AnsiColors;
import com.morkaz.morkazsk.optionals.protocollib.EffPlaySoundForPlayer;
import com.morkaz.morkazsk.optionals.protocollib.EffShowParticleToPlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RegisterManager {

	public static List<ExpressionData> expressionDataList = new ArrayList<>();
	public static List<ConditionData> conditionDataList = new ArrayList<>();
	public static List<EventData> eventDataList = new ArrayList<>();
	public static List<EffectData> effectDataList = new ArrayList<>();

	private static void defineEvents(){
		eventDataList.clear();
	}

	private static void defineConditions(){
		conditionDataList.clear();
	}

	private static void defineEffects(){
		effectDataList.add(new EffectData(
				EffPlaySound.class,  "[mor.]play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number%"
		));
		effectDataList.add(new EffectData(
				EffSpawnParticle.class, "[mor.](summon|play|create|activate|spawn) %number% [of] [particle] %string%:%number% offset (at|by|from) %number%, %number%(,| and) %number% at %location%"
		));
		if (MorkazSk.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &aProtocolLib &eadditional features added to load!"));
			effectDataList.add(new EffectData(
					EffShowParticleToPlayer.class, "[mor.](spawn|show) %number%[ of] particle[s] %string% (to|for) %player% at %location% offset[ by] %number%(, | and )%number%(, | and )%number% with speed %number%"
			));
			effectDataList.add(new EffectData(
					EffPlaySoundForPlayer.class, "[mor.]play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number% (to|for) %player%"
			));
		}
		effectDataList.clear();
	}

	private static void defineExpressions(){
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
		defineExpressions();
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
		defineEvents();
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
		defineEffects();
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
		defineConditions();
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
