package com.morkaz.morkazsk.effects;
 
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import mkremins.fanciful.FancyMessage;
import net.md_5.bungee.api.ChatColor;
 
public class EffSendJsonMessage extends Effect{

	static {
		RegisterManager.registerEffect(
				EffSendJsonMessage.class,
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% then send [(second|next)] message %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% (with link|linked|with url) %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% to run[ command] %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% (with link|linked|with url) %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% then send [(second|next)] message %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% to run[ command] %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% then send [(second|next)] message %string% to run[ command] %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% to run[ command] %string% then send [(second|next)] message %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% (with link|linked|with url) %string% to run[ command] %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% to run[ command] %string% (with link|linked|with url) %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% then send [(second|next)] message %string% (with link|linked|with url) %string%",
				"[morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% (with link|linked|with url) %string% then send [(second|next)] message %string%"
		);
	}

	
	private Expression<Player> player;
	private Expression<String> firstMessage;
	private Expression<String> tooltip;
	private Expression<String> link;
	private Expression<String> thenMessage;
	private Expression<String> command;
	private int pattern;


	@Override
	public boolean init(Expression<?>[] e, int pattern, Kleenean arg2, ParseResult arg3) {
			this.pattern = pattern;
			firstMessage = (Expression<String>) e[0];
			player = (Expression<Player>) e[1];
			tooltip = (Expression<String>) e[2];
			
		//0 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string%
		//1 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string%
			if (pattern == 1){
				link = (Expression<String>) e[3];
		//2 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string%
			} else if (pattern == 2){
				thenMessage = (Expression<String>) e[3];
		//3 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string%
			} else if (pattern == 3){
				command = (Expression<String>) e[3];
				
				
				
		//4 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% then send [(second|next)] message %string%
			} else if (pattern == 4){
				link = (Expression<String>) e[3];
				thenMessage = (Expression<String>) e[4];
		//5 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% (with link|linked|with url) %string%
			} else if (pattern == 5){
				link = (Expression<String>) e[4];
				thenMessage = (Expression<String>) e[3];
				
		//6 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% to run[ command] %string%
			} else if (pattern == 6){
				link = (Expression<String>) e[3];
				command = (Expression<String>) e[4];
		//7 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% (with link|linked|with url) %string%
			} else if (pattern == 7){
				link = (Expression<String>) e[4];
				command = (Expression<String>) e[3];
				
		//8 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% then send [(second|next)] message %string%
			} else if (pattern == 8){
				command = (Expression<String>) e[3];
				thenMessage = (Expression<String>) e[4];
		//9 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% to run[ command] %string%
			} else if (pattern == 9){
				command = (Expression<String>) e[4];
				thenMessage = (Expression<String>) e[3];
				
				
				
		//10 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% then send [(second|next)] message %string% to run[ command] %string% 
			} else if (pattern == 10){
				link = (Expression<String>) e[3];
				thenMessage = (Expression<String>) e[4];
				command = (Expression<String>) e[5];
				
		//11 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% (with link|linked|with url) %string% to run[ command] %string% then send [(second|next)] message %string%
			} else if (pattern == 11){
				link = (Expression<String>) e[3];
				command = (Expression<String>) e[4];
				thenMessage = (Expression<String>) e[5];
				
		//12 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% (with link|linked|with url) %string% to run[ command] %string%
			} else if (pattern == 12){
				thenMessage = (Expression<String>) e[3];
				link = (Expression<String>) e[4];
				command = (Expression<String>) e[5];
				
		//13 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% then send [(second|next)] message %string% to run[ command] %string% (with link|linked|with url) %string%
			} else if (pattern == 13){
				thenMessage = (Expression<String>) e[3];
				command = (Expression<String>) e[4];
				link = (Expression<String>) e[5];
				
		//14 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% then send [(second|next)] message %string% (with link|linked|with url) %string%
			} else if (pattern == 14){
				command = (Expression<String>) e[3];
				thenMessage = (Expression<String>) e[4];
				link = (Expression<String>) e[5];
				
		//15 [morkazsk] send json message %string% to %player% with (secred|tool[ ]tip|hidden)[ message] %string% to run[ command] %string% (with link|linked|with url) %string% then send [(second|next)] message %string%
			} else if (pattern == 15){
				command = (Expression<String>) e[3];
				link = (Expression<String>) e[4];
				thenMessage = (Expression<String>) e[5];
			}
			return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	protected void execute(Event e) {
		if (player.getSingle(e) != null && firstMessage.getSingle(e) != null && tooltip.getSingle(e) != null){
			if (pattern == 0){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.send(player.getSingle(e));
			} else if (pattern == 1){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.link(link.getSingle(e))
				.send(player.getSingle(e));
			} else if (pattern == 2){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.then(ChatColor.translateAlternateColorCodes('&', thenMessage.getSingle(e)))
				.send(player.getSingle(e));
			} else if (pattern == 3){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.command(command.getSingle(e))
				.send(player.getSingle(e));
			} else if (pattern == 4 || pattern == 5){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.link(link.getSingle(e))
				.then(ChatColor.translateAlternateColorCodes('&', thenMessage.getSingle(e)))
				.send(player.getSingle(e));
			} else if (pattern == 6 || pattern == 7){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.link(link.getSingle(e))
				.command(command.getSingle(e))
				.send(player.getSingle(e));
			} else if (pattern == 8 || pattern == 9){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.then(ChatColor.translateAlternateColorCodes('&', thenMessage.getSingle(e)))
				.command(command.getSingle(e))
				.send(player.getSingle(e));
			} else if (pattern >= 10 && pattern <= 15){
				new FancyMessage(ChatColor.translateAlternateColorCodes('&', firstMessage.getSingle(e)))
				.tooltip(ChatColor.translateAlternateColorCodes('&', tooltip.getSingle(e)))
				.then(ChatColor.translateAlternateColorCodes('&', thenMessage.getSingle(e)))
				.command(command.getSingle(e))
				.link(link.getSingle(e))
				.send(player.getSingle(e));
			} else {
				new FancyMessage("[MorkazSk-ERROR] Can not find good pattern that will execute propetly JSON script effect. Report it on MorkazSk's github page.").send(player.getSingle(e));
			}
		}
	}
 
}