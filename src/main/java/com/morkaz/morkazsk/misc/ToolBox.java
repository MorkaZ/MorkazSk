package com.morkaz.morkazsk.misc;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ToolBox {

	public static <E extends Enum<E>> Boolean enumContains(Class<E> clazz, String valueString){
		E[] enumValues = clazz.getEnumConstants();
		List<String> enumStringValuesList = toStringList(enumValues);
		return enumStringValuesList.contains(valueString);
	}

	public static List<String> toStringList(List<Object> objectList){
		return objectList.stream()
				.map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());
	}

	public static List<String> toStringList(Object... objects){
		return toStringList(Arrays.asList(objects));
	}

}
