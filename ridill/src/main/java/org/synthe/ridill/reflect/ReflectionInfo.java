package org.synthe.ridill.reflect;

import org.synthe.ridill.core.TargetInfo;

public class ReflectionInfo {
	private ClassInfo<?> _typeInfo;
	
//	public Field field(){
//		return (Field)_typeInfo.owner();
//	}
	
	public String className(){
		return _typeInfo.type().getName();
	}
	
	public Class<?>[] interfaces(){
		return _typeInfo.type().getInterfaces();
	}
	ClassType classType(){
		return _typeInfo.classType();
	}

	public boolean isImmutable(){
		return _typeInfo.isImmutable();
	}
	
	public TargetInfo getTargetInfo(){
		//TODO impl
		return null;
	}
	
	public void set(Object instance, Object val){
		
	}
	
	public Object newInstance(){
		return _typeInfo.newInstance();
	}
}
