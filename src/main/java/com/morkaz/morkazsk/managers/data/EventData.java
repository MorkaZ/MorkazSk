package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;


public class EventData {

	String eventName;
	Class<? extends SkriptEvent> skriptEventType;
	Class<? extends Event>[] events;
	String[] patterns;

	public EventData(String eventName, Class<? extends SkriptEvent> skriptEventType, Class<? extends Event>[] events, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skriptEventType;
		this.events = events;
		this.patterns = patterns;
	}

	public EventData(String eventName, Class<? extends SkriptEvent> skripEventType, Class<? extends Event> event, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skripEventType;
		this.events = CollectionUtils.array(event);
		this.patterns = patterns;
	}

	public String getEventName() {
		return eventName;
	}

	public Class<? extends SkriptEvent> getSkriptEventType() {
		return skriptEventType;
	}

	public Class<? extends Event>[] getEvents() {
		return events;
	}

	public String[] getPatterns() {
		return patterns;
	}

	public Class<? extends Event> getEvent(){
		return this.events[0];
	}

}
