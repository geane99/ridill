package org.synthe.ridill.reflect;


public interface Adapter {
	public Object getEmbedValue(ReflectionInfo info, Object enclosingInstance, Integer depth);
	public Object getObjectValue(ReflectionInfo info, Object enclosingInstance, Integer depth);
	public Object getEnumValue(ReflectionInfo info, Object enclosingInstance, Integer depth);
}
