package com.morkaz.morkazsk.managers.data;

import ch.njol.skript.classes.ClassInfo;

public class TypeData <T> {

	private ClassInfo<T> classInfo;

	public TypeData(ClassInfo<T> classInfo){
		this.classInfo = classInfo;
	}

	public ClassInfo<T> getClassInfo() {
		return classInfo;
	}

}
