package org.synthe.ridill.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ReflectionInfoFactory {
	public static ReflectionInfo returnType(Object proxy, Method method, Object[] params){
		return null;
	}
	
	public static ReflectionInfo fieldType(ReflectionInfo info){
		return null;
	}
	
	public static List<ReflectionInfo> fieldTypeAll(ReflectionInfo owner){
		List<ReflectionInfo> r = new ArrayList<>();
		
		return null;
	}
	
	public static ReflectionInfo fieldTypeParameterType(ReflectionInfo info, Integer genericsIndex){
		return null;
	}
}
