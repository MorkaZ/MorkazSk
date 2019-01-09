package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.util.Getter;
import org.bukkit.event.Event;

public class EventValueData<E extends Event, V> {

	public Class<E> eventClass;
	public Class<V> valueClass;
	public Getter<E, V> getter;


	public EventValueData(Class<E> eventClass, Class<V> valueClass, Getter<E, V> getter) {
		this.eventClass = eventClass;
		this.valueClass = valueClass;
		this.getter = getter;
	}

	public Class<E> getEventClass() {
		return eventClass;
	}

	public Class<V> getValueClass() {
		return valueClass;
	}

	public Getter<E, V> getGetter() {
		return getter;
	}

}
