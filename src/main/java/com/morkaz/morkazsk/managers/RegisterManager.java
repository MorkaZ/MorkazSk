package com.morkaz.morkazsk.managers;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
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
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

	public static void registerAll(){
		registerTypes();
		registerEvents();
		registerConditions();
		registerEffects();
		registerExpressions();
		registerListeners();
	}

	private static void defineTypes(){
		typeDataList.clear();
		/*
			UNIVERSAL EVENTS
		 */
		// Just for test & make sure that it will be alwatys registered (potion effect types syntaxes)
		typeDataList.add(new TypeData<PotionType>(new ClassInfo(PotionEffectType.class, "potioneffecttype")
				.name("potioneffecttype")
				.user("potioneffecttype(s)?")
				.description("Potion effect type.")
				.defaultExpression(new EventValueExpression<>(PotionEffectType.class))
				.parser(new Parser<PotionEffectType>() {
					public PotionEffectType parse(final String textName, final ParseContext context) {
						return PotionEffectType.getByName(textName);
					}

					@Override
					public String toVariableNameString(final PotionEffectType potionType) {
						return potionType.toString();
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public String toString(final PotionEffectType potionType, final int flags) {
						return potionType.toString();
					}
				})
			)
		);
	}

	private static void defineEvents(){
		eventDataList.clear();
		/*
			UNIVERSAL EVENTS
		 */
		eventDataList.add(new EventData(
				"block fall",
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
				"[morkaz[sk]] block fall[ing]"
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
				"[morkaz[sk]] block start fall[ing]"
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
				"[morkaz[sk]] block stop fall[ing]"
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
				"[morkaz[sk]] (block piston|piston block) push"
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
				"[morkaz[sk]] (block piston|piston block) pull"
		));
	}

	private static void defineConditions(){
		conditionDataList.clear();
		/*
			UNIVERSAL CONDITIONS
		 */
		// Checks if entity has given potion effect
		conditionDataList.add(new ConditionData(
				CondIsEntityHavingPotionEffect.class,
				"[morkaz[sk]] %livingentity% (has|is having) potion [effect] %potioneffecttypes%",
				"[morkaz[sk]] %livingentity% (hasn(t|'t)|is((nt|n't)| not) having) potion [effect] %potioneffecttypes%"
		));
	}

	private static void defineEffects(){
		effectDataList.clear();
		/*
			UNIVERSAL EFFECTS
		 */
		// It will break block naturally with specific item (if is defined)
		effectDataList.add(new EffectData(
				EffBreakBlock.class,
				"[morkaz[sk]] [naturally] break %block% [(using|with) %item%]"
		));
		// It will push any living entity from specific location.
		effectDataList.add(new EffectData(
				EffPushEntityFromLocation.class,
				"[morkaz[sk]] push [the ]%livingentity% from %location%"
		));
		// Play specific sound with given options at location.
		effectDataList.add(new EffectData(
				EffPlaySound.class,
				"[morkaz[sk]] play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number%"
		));
		// Spawn specific particle type with given options at location.
		effectDataList.add(new EffectData(
				EffSpawnParticle.class,
				"[morkaz[sk]] (summon|play|create|activate|spawn) %number% [of] [particle] %string%:%number% offset (at|by|from) %number%, %number%(,| and) %number% at %location%"
		));
		/*
			ProtocolLib EFFECTS
		 */
		if (MorkazSk.getInstance().getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
			Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &dProtocolLib &eadditional features included!&r"));
			// Spawn specific particle with given options at location for single player.
			effectDataList.add(new EffectData(
					EffShowParticleToPlayer.class,
					"[morkaz[sk]] (spawn|show) %number%[ of] particle[s] %string% (to|for) %player% at %location% offset[ by] %number%(, | and )%number%(, | and )%number% with speed %number%"
			));
			// Play specific sound with given options at location to single player.
			effectDataList.add(new EffectData(
					EffPlaySoundForPlayer.class,
					"[morkaz[sk]] play [raw ]sound %string% at %location% [with ]pitch %number%[ and] volume %number% (to|for) %player%",
					"[morkaz[sk]] play %string% specifically to %player% [with ]pitch %number%[ and] volume %number%"
			));
		}
	}

	private static void defineExpressions(){
		expressionDataList.clear();
		/*
			UNIVERSAL EXPRESSIONS
		 */
		// Tier(s) of specific type of potions applied on lived entity
		expressionDataList.add(new ExpressionData(
				ExprTierOfPotionOnEntity.class, Timespan.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] tier[s] of [potion [effect [type]]] %potioneffecttypes% of %livingentity%"
		));
		// Duration(s) of specific type of potions applied on lived entity
		expressionDataList.add(new ExpressionData(
				ExprDurationOfPotionOnEntity.class, Timespan.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] duration[s] of [potion [effect [type]]] %potioneffecttypes% of %livingentity%"
		));
		// ItemStack of dropped Item entity.
		expressionDataList.add(new ExpressionData(
				ExprItemOfItemEntity.class, ItemStack.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] item[s] (of|within) %entity%"
		));
		// List of items that will drop when block will be broken using (optional) specific item.
		expressionDataList.add(new ExpressionData(
				ExprDropOfBlock.class, ItemStack.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] drops of %block%",
				"[morkaz[sk]] drops of %block% (with|using) [tool] %itemstack%"
		));
		// Item in cursor of player.
		expressionDataList.add(new ExpressionData(
				ExprCursorItemOfPlayer.class, ItemStack.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] cursor item of %player%"
		));
		// Last login unix date of player.
		expressionDataList.add(new ExpressionData(
				ExprLastLoginOfOfflinePlayer.class, Date.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] last[ ](login|played[ date]) of %offlineplayer%"
		));
		// Last login unix date of offline player.
		expressionDataList.add(new ExpressionData(
				ExprLastLoginOfPlayer.class, Date.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] last[ ](login|played[ date]) of %player%"
		));
		// Returns skript's date from unix date.
		expressionDataList.add(new ExpressionData(
				ExprDateFromUnix.class, Date.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] date from (unix|timestamp|milis) %number%"
		));
		// Returns unix date from skript's date.
		expressionDataList.add(new ExpressionData(
				ExprUnixFromDate.class, Number.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] (unix|timestamp|milis) (from|of) [date] %date%"
		));
		// List of sorted numbers with custom output by variable value where value is number and index can be anything.
		expressionDataList.add(new ExpressionData(
				ExprSortWithCustomOutput.class, String.class, ExpressionType.COMBINED,
				"[morkaz[sk]] sorted %numbers% from highest to lowest with (output|format) %string%",
				"[morkaz[sk]] sorted %numbers% from lowest to highest with (output|format) %string%"
		));
		/*
			DEDICATED TO EVENTS
		 */
		// Caught entity with fishing rod in "on fishing" event.
		expressionDataList.add(new ExpressionData(
				ExprFishingCaughtEntity.class, Entity.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] [fishing(-| )]caught(-| )entity"
		));
		// Fishing hook entity in "on fishing" event.
		expressionDataList.add(new ExpressionData(
				ExprFishingHook.class, Entity.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] fishing(-| )hook"
		));
		// State of fishing in "on fishing" event.
		expressionDataList.add(new ExpressionData(
				ExprFishingState.class, String.class, ExpressionType.SIMPLE,
				"[morkaz[sk]] fishing(-| )state"
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

	public static void registerTypes(){
		defineTypes();
		int counter = 0;
		for (TypeData data : typeDataList){
			try {
				Classes.registerClass(data.getClassInfo());
				counter++;
			} catch (Exception e){
				if (e.getMessage().contains("is already registered")){
					Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cType: &d\""+data.getClassInfo().getC().getName()+"\" &cis already registered. Dismissing loading of this type.&r"));
					continue;
				}
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading type: \"&f"+data.getClassInfo().getC().getName()+"\"&c. Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eTypes!&r"));
	}

	public static void registerExpressions(){
		defineExpressions();
		int counter = 0;
		for (ExpressionData data : expressionDataList){
			try {
				Skript.registerExpression(data.getExpressionClass(), data.getReturnValueClass(), data.getExpressionType(), data.getPatterns());
				counter++;
			} catch (Exception e){
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0]+"&c. Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
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
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0]+"&c. Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
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
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0]+"&c. Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
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
				Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &cException has ben thrown while loading: &f"+data.getPatterns()[0]+"&c. Do not worry, it will &aNOT AFFECT OTHER STUFF&c. Details are below. Please, report this here: &1https://github.com/MorkaZ/MorkazSk/issues&r"));
				e.printStackTrace();
			}
		}
		Bukkit.getLogger().info(AnsiColors.translate("&", "&9["+ MorkazSk.getInstance().getDescription().getName()+"] &eRegistered &a"+counter+" &eConditions!&r"));
	}


}
