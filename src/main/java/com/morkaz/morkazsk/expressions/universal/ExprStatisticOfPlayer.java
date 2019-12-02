package com.morkaz.morkazsk.expressions.universal;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.morkaz.morkazsk.managers.RegisterManager;
import com.morkaz.morkazsk.misc.ToolBox;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Statistics of Player")
@Description({
		"Returns statistics of player. You can find all statistic names here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Statistic.html"
})
@Examples({
		"set {_time.played} to statistic \"PLAY_ONE_MINUTE\" of player #Name is misleading, it returns ticks!",
		"set {_time.played} to \"{_time.played} ticks\" parsed as timespan #Change raw ticks number to timespan",
		"send \"%{_time.played}%\" to player # Precise output of play time of player",
		" ",
		"set {_mined.stones} to statistic \"MINE_BLOCK\" of player with data \"STONE\" parsed as material #Grabs amount of mined stones",
		"send \"&aYou have mined &3%{_mined.stones}&a!%\" #NOTE - If one of your scripts/plugins cancel block break event, mined blocks will NOT be calculated propetly!",
})
@Since("1.2")

public class ExprStatisticOfPlayer extends SimpleExpression<Integer> {

	static {
		RegisterManager.registerExpression(
				ExprStatisticOfPlayer.class,
				Integer.class,
				ExpressionType.SIMPLE,
				"[morkazsk] statistic %string% from %player%",
				"[morkazsk] statistic %string% from %player% (of|with data) %object%"

		);
	}

	private Expression<Player> playerExpr;
	private Expression<String> statisticExpr;
	private Expression<Object> dataExpr;
	private int pattern = 0;

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}


	@Override
	public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		statisticExpr = (Expression<String>) expressions[0];
		playerExpr = (Expression<Player>) expressions[1];
		if (pattern == 1){
			dataExpr = (Expression<Object>) expressions[2];
		}
		this.pattern = pattern;
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		if (pattern == 0){
			return "morkazsk statistic "+statisticExpr.toString(event, debug)+" from " + playerExpr.toString(event, debug);
		}
		return "morkazsk statistic "+statisticExpr.toString(event, debug)+" from " + playerExpr.toString(event, debug) + "with data "+dataExpr.toString(event, debug);
	}

	@Override
	protected Integer[] get(Event event) {
		Player player = playerExpr.getSingle(event);
		String statisticName = statisticExpr.getSingle(event);
		if (player != null && statisticName != null){
			statisticName = statisticName.toUpperCase();
			if (ToolBox.enumContains(Statistic.class, statisticName)){
				Statistic statistic = Statistic.valueOf(statisticName);
				Object data = dataExpr.getSingle(event);
				if (data != null){
					if (data instanceof EntityType){
						return new Integer[] {player.getStatistic(statistic, (EntityType)data)};
					} else if (data instanceof Material){
						return new Integer[] {player.getStatistic(statistic, (Material)data)};
					} else {
						Skript.error("[MorkazSk] Given additional data in statistic expression not found! Acceptable are only EntityType and Material!");
						return new Integer[] {};
					}
				}
				return new Integer[] {player.getStatistic(statistic)};
			}
		}
		return new Integer[] {};
	}

	public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
		Player player = playerExpr.getSingle(event);
		String statisticName = statisticExpr.getSingle(event);
		if (player != null && statisticName != null){
			statisticName = statisticName.toUpperCase();
			if (ToolBox.enumContains(Statistic.class, statisticName)){
				Statistic statistic = Statistic.valueOf(statisticName);
				Object data = dataExpr.getSingle(event);
				if (mode == Changer.ChangeMode.SET){
					if (data != null){
						if (data instanceof EntityType){
							player.setStatistic(statistic, (EntityType)data, (Integer)delta[0]);
						} else if (data instanceof Material){
							player.setStatistic(statistic, (Material)data, (Integer)delta[0]);
						} else {
							Skript.error("[MorkazSk] Given additional data in statistic expression not found! Acceptable are only EntityType and Material!");
						}
					} else {
						player.setStatistic(statistic, (Integer)delta[0]);
					}
				} else if (mode == Changer.ChangeMode.DELETE){
					if (data != null){
						if (data instanceof EntityType){
							player.setStatistic(statistic, (EntityType)data, 0);
						} else if (data instanceof Material){
							player.setStatistic(statistic, (Material)data, 0);
						} else {
							Skript.error("[MorkazSk] Given additional data in statistic expression not found! Acceptable are only EntityType and Material!");
						}
					} else {
						player.setStatistic(statistic, 0);
					}
				} else if (mode == Changer.ChangeMode.ADD){
					if (data != null){
						if (data instanceof EntityType){
							player.setStatistic(statistic, (EntityType)data, player.getStatistic(statistic, (EntityType)data) + (Integer)delta[0]);
						} else if (data instanceof Material){
							player.setStatistic(statistic, (Material)data, player.getStatistic(statistic, (Material)data) + (Integer)delta[0]);
						} else {
							Skript.error("[MorkazSk] Given additional data in statistic expression not found! Acceptable are only EntityType and Material!");
						}
					} else {
						player.setStatistic(statistic, player.getStatistic(statistic) + (Integer)delta[0]);
					}
				} else if (mode == Changer.ChangeMode.REMOVE){
					if (data != null){
						if (data instanceof EntityType){
							player.setStatistic(statistic, (EntityType)data, player.getStatistic(statistic, (EntityType)data) - (Integer)delta[0]);
						} else if (data instanceof Material){
							player.setStatistic(statistic, (Material)data, player.getStatistic(statistic, (Material)data) - (Integer)delta[0]);
						} else {
							Skript.error("[MorkazSk] Given additional data in statistic expression not found! Acceptable are only EntityType and Material!");
						}
					} else {
						player.setStatistic(statistic, player.getStatistic(statistic) - (Integer)delta[0]);
					}
				}
			}
		}
	}

	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
			return (Class[]) CollectionUtils.array(new Class[] { Integer.class });
		}
		return null;
	}

}
