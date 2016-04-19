package org.synthe.ridill.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

import org.synthe.ridill.TargetInfo;

abstract class PropertyTemplate extends Template{
	abstract public String propertyName();
	abstract public void set(Object instance, Object value) throws IllegalAccessException;
	abstract public Object get(Object instance) throws IllegalAccessException;
	abstract public Object invoke(Object instance, Object...args) throws InvocationTargetException, IllegalAccessException;
	abstract public TargetInfo toTargetInfo(Object instance);
	
	protected void setAccessSecury(AccessibleObject target){
		target.setAccessible(true);
	}
}
