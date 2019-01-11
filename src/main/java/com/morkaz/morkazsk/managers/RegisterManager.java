package com.morkaz.morkazsk.managers;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Date;
import ch.njol.skript.util.Getter;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.conditions.CondIsPlayerHavingPotionEffect;
import com.morkaz.morkazsk.effects.EffPlaySound;
import com.morkaz.morkazsk.effects.EffPushEntityFromLocation;
import com.morkaz.morkazsk.effects.EffSpawnParticle;
import com.morkaz.morkazsk.events.*;
import com.morkaz.morkazsk.events.listeners.BlockFallListener;
import com.morkaz.morkazsk.events.listeners.BlockPistonMoveListener;
import com.morkaz.morkazsk.expressions.*;
import com.morkaz.morkazsk.expressions.dedicated.ExprFishingCaughtEntity;
import com.morkaz.morkazsk.expressions.dedicated.ExprFishingHook;
import com.morkaz.morkazsk.expressions.dedicated.ExprFishingState;
import com.morkaz.morkazsk.managers.data.*;
import com.morkaz.morkazsk.misc.AnsiColors;
import com.morkaz.morkazsk.optionals.protocollib.EffPlaySoundForPlayer;
import com.morkaz.morkazsk.optionals.protocollib.EffShowParticleToPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterManager {

	private static List<ExpressionData> expressionDataList = new ArrayList<>();
	private static List<ConditionData> conditionDataList = new ArrayList<>();
	private static List<EventData> eventDataList = new ArrayList<>();
	private static List<EffectData> effectDataList = new ArrayList<>();

	public static void registerAll(){
		registerEvents();
		registerConditions();
		registerEffects();
		registerExpressions();
		registerListeners();
	}

	private static void defineEvents(){
		eventDataList.clear();
		eventDataList.add(new EventData(
				"block start fall",
				SimpleEvent.class,
				EvtBlockFall.class,
				Arrays.asList(
						new EventValueData(EvtBlockFall.class, Block.class, new Getter<Block, EvtBlockFall>() {
							@Override
							public Block get(EvtBlockFall evt) {
								return evt.getBlock();
							}
						}),
						new EventValueData(EvtBlockFall.class, Entity.class, new Getter<Entity, EvtBlockFall>() {
							@Override
							public Entity get(EvtBlockFall evt) {
								return evt.getEntity();
							}
						})
				),
				"[mor.]block fall[ing]"
		));
		eventDataList.add(new EventData(
				"block start fall",
				SimpleEvent.class,
				EvtBlockStartFall.class,
				Arrays.asList(
						new EventValueData(EvtBlockStartFall.class, Block.class, new Getter<Block, EvtBlockStartFall>() {
							@Override
							public Block get(EvtBlockStartFall evt) {
								return evt.getBlock();
							}
						}),
						new EventValueData(EvtBlockStartFall.class, Entity.class, new Getter<Entity, EvtBlockStartFall>() {
							@Override
							public Entity get(EvtBlockStartFall evt) {
								return evt.getEntity();
							}
						})
				),
				"[mor.]block start fall[ing]"
		));
		eventDataList.add(new EventData(
				"block stop fall",
				SimpleEvent.class,
				EvtBlockStopFall.class,
				Arrays.asList(
						new EventValueData(EvtBlockStopFall.class, Block.class, new Getter<Block, EvtBlockStopFall>() {
							@Override
							public Block get(EvtBlockStopFall evt) {
								return evt.getBlock();
							}
						}),
						new EventValueData(EvtBlockStopFall.class, Entity.class, new Getter<Entity, EvtBlockStopFall>() {
							@Override
							public Entity get(EvtBlockStopFall evt) {
								return evt.getEntity();
							}
						})
				),
				"[mor.]block stop fall[ing]"
		));
		eventDataList.add(new EventData(
				"piston push block",
				SimpleEvent.class,
				EvtBlockPistonPush.class,
				Arrays.asList(
						new EventValueData(EvtBlockPistonPush.class, Block.class, new Getter<Block, EvtBlockPistonPush>() {
							@Override
							public Block get(EvtBlockPistonPush evt) {
								return evt.getBlock();
							}
						}),
						new EventValueData(EvtBlockPistonPush.class, String.class, new Getter<String, EvtBlockPistonPush>() {
							@Override
							public String get(EvtBlockPistonPush evt) {
								return evt.getBlockDirection();
							}
						})
				),
				"[mor.](block piston|piston block) push"
		));
		eventDataList.add(new EventData(
				"piston pull block",
				SimpleEvent.class,
				EvtBlockPistonPull.class,
				Arrays.asList(
						new EventValueData(EvtBlockPistonPull.class, Block.class, new Getter<Block, EvtBlockPistonPull>() {
							@Override
							public Block get(EvtBlockPistonPull evt) {
								return evt.getBlock();
							}
						}),
						new EventValueData(EvtBlockPistonPull.class, String.class, new Getter<String, EvtBlockPistonPull>() {
							@Override
							public String get(EvtBlockPistonPull evt) {
								return evt.getBlockDirection();
							}
						})
				),
				"[mor.](block piston|piston block) pull"
		));
	}

	private static void defineConditions(){
		conditionDataList.clear();
		conditionDataList.add(new ConditionData(
				CondIsPlayerHavingPotionEffect.class,
				"[mor.]%livingentity% (has|is having) potion [effect] %string%",
				"[mor.]%livingentity% (hasn(t|'t)|is((nt|n't)| not) having) potion [effect] %string%"
		));
	}

	private static void defineEffects(){
		effectDataList.clear();
		effectDataList.add(new EffectData(
				EffPushEntityFromLocation.class,  "[mor.]push [the ]%livingentity% from %location%"
		));
		effectDataList.add(new EffectData(
				EffPlaySound.class,  "[mor.]play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number%"
		));
		effectDataList.add(new EffectData(
				EffSpawnParticle.class, "[mor.](summon|play|create|activate|spawn) %number% [of] [particle] %string%:%number% offset (at|by|from) %number%, %number%(,| and) %number% at %location%"
		));
		if (MorkazSk.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &dProtocolLib &eadditional features added to load!&r"));
			effectDataList.add(new EffectData(
					EffShowParticleToPlayer.class, "[mor.](spawn|show) %number%[ of] particle[s] %string% (to|for) %player% at %location% offset[ by] %number%(, | and )%number%(, | and )%number% with speed %number%"
			));
			effectDataList.add(new EffectData(
					EffPlaySoundForPlayer.class,
					"[mor.]play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number% (to|for) %player%",
					"[mor.]play %string% specifically to %player% [with ]pitch %number%[ and] volume %number%"
			));
		}
	}

	private static void defineExpressions(){
		expressionDataList.clear();
		/*
			NORMAL EXPRESSIONS
		 */
		expressionDataList.add(new ExpressionData(
				ExprDropOfBlock.class, ItemStack.class, ExpressionType.SIMPLE,
				"[mor.]drops of %block%",
				"[mor.]drops of %block% (with|using) [tool] %itemstack%"
		));
		expressionDataList.add(new ExpressionData(
				ExprCursorItemOfPlayer.class, ItemStack.class, ExpressionType.SIMPLE,
				"[mor.]cursor item of %player%"
		));
		expressionDataList.add(new ExpressionData(
				ExprLastLoginOfOfflinePlayer.class, Date.class, ExpressionType.SIMPLE,
				"[mor.]last[ ](login|played[ date]) of %offlineplayer%"
		));
		expressionDataList.add(new ExpressionData(
				ExprLastLoginOfPlayer.class, Date.class, ExpressionType.SIMPLE,
				"[mor.]last[ ](login|played[ date]) of %player%"
		));
		expressionDataList.add(new ExpressionData(
				ExprDateFromUnix.class, Date.class, ExpressionType.SIMPLE,
				"[mor.]date from (unix|timestamp|milis) %number%"
		));
		expressionDataList.add(new ExpressionData(
				ExprUnixFromDate.class, Number.class, ExpressionType.SIMPLE,
				"[mor.](unix|timestamp|milis) (from|of) [date] %date%"
		));
		expressionDataList.add(new ExpressionData(
				ExprSortWithCustomOutput.class, String.class, ExpressionType.COMBINED,
				"[mor.]sorted %numbers% from highest to lowest with (output|format) %string%",
				"[mor.]sorted %numbers% from lowest to highest with (output|format) %string%"
		));
		/*
			DEDICATED TO EVENTS
		 */
		expressionDataList.add(new ExpressionData(
				ExprFishingCaughtEntity.class, Entity.class, ExpressionType.SIMPLE,
				"[mor.][fishing(-| )]caught(-| )entity"
		));
		expressionDataList.add(new ExpressionData(
				ExprFishingHook.class, Entity.class, ExpressionType.SIMPLE,
				"[mor.]fishing(-| )hook"
		));
		expressionDataList.add(new ExpressionData(
				ExprFishingState.class, String.class, ExpressionType.SIMPLE,
				"[mor.]fishing(-| )state"
		));
	}

	private static void registerListeners(){
		MorkazSk instance = MorkazSk.getInstance();
		if (instance == null){
			return;
		}
		instance.getServer().getPluginManager().registerEvents(new BlockFallListener(), instance);
		instance.getServer().getPluginManager().registerEvents(new BlockPistonMoveListener(), instance);
	}

	public static void registerExpressions(){
		defineExpressions();
		int counter = 0;
		for (ExpressionData data : expressionDataList){
			try {
				Skript.registerExpression(data.getExpressionClass(), data.getReturnValueClass(), data.getExpressionType(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.&r");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eExpressions!&r"));
	}

	public static void registerEvents(){
		defineEvents();
		int counter = 0;
		for (EventData data : eventDataList){
			try {
				Skript.registerEvent(data.getEventName(), data.getSkriptEventType(), data.getEvents(), data.getPatterns());
				for (EventValueData eventValueData : data.getEventValueDataList()){
					EventValues.registerEventValue(eventValueData.getEventClass(), eventValueData.getValueClass(), eventValueData.getGetter(), 0);
				}
				if (MorkazSk.getInstance() != null){
					for (Listener listener : data.getListenerList()){
						MorkazSk.getInstance().getServer().getPluginManager().registerEvents(data.getListener(), MorkazSk.getInstance());
					}
				}
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.&r");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eEvents!&r"));
	}

	public static void registerEffects(){
		defineEffects();
		int counter = 0;
		for (EffectData data : effectDataList){
			try {
				Skript.registerEffect(data.getEffectClass(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.&r");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eEffects!&r"));
	}

	public static void registerConditions(){
		defineConditions();
		int counter = 0;
		for (ConditionData data : conditionDataList){
			try {
				Skript.registerCondition(data.getConditionClass(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0])+"&c. Details are below.&r");
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eConditions!&r"));
	}


}
