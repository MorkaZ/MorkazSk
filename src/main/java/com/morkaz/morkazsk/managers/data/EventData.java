package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;


public class EventData {

	String eventName;
	Class<? extends SkriptEvent> skriptEventType;
	Class<? extends Event>[] events;
	List<EventValueData> eventValueDataList = new ArrayList<>();
	List<Listener> listenerList = new ArrayList<>();
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

	public EventData(String eventName, Class<? extends SkriptEvent> skriptEventType, Class<? extends Event>[] events, Listener listener, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skriptEventType;
		this.events = events;
		this.listenerList.add(listener);
		this.patterns = patterns;
	}

	public EventData(String eventName, Class<? extends SkriptEvent> skriptEventType, Class<? extends Event>[] events, List<EventValueData> eventValueDataList, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skriptEventType;
		this.events = events;
		this.eventValueDataList = eventValueDataList;
		this.patterns = patterns;
	}

	public EventData(String eventName, Class<? extends SkriptEvent> skripEventType, Class<? extends Event> event, List<EventValueData> eventValueDataList, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skripEventType;
		this.events = CollectionUtils.array(event);
		this.eventValueDataList = eventValueDataList;
		this.patterns = patterns;
	}

	public EventData(String eventName, Class<? extends SkriptEvent> skripEventType, Class<? extends Event> event, List<EventValueData> eventValueDataList, Listener listener, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skripEventType;
		this.events = CollectionUtils.array(event);
		this.eventValueDataList = eventValueDataList;
		this.listenerList.add(listener);
		this.patterns = patterns;
	}

	public EventData(String eventName, Class<? extends SkriptEvent> skriptEventType, Class<? extends Event>[] events, List<EventValueData> eventValueDataList, List<Listener> listenerList, String... patterns) {
		this.eventName = eventName;
		this.skriptEventType = skriptEventType;
		this.events = events;
		this.eventValueDataList = eventValueDataList;
		this.listenerList = listenerList;
		this.patterns = patterns;
	}

	public List<Listener> getListenerList() {
		return listenerList;
	}

	public Listener getListener(){
		return listenerList.size() > 0 ? listenerList.get(0) : null;
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
		return this.events.length > 0 ? events[0] : null;
	}

	public List<EventValueData> getEventValueDataList() {
		return eventValueDataList;
	}

}
