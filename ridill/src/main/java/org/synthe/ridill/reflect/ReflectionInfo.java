package org.synthe.ridill.reflect;

import org.synthe.ridill.TargetInfo;

public class ReflectionInfo {
	private PropertyTemplate _template;
	
	public String className(){
		return _template.templateName();
	}
	public Class<?>[] interfaces(){
		return _template.template().getInterfaces();
	}
	ClassType classType(){
		return _template.classType();
	}
	public boolean isImmutable(){
		return _template.isImmutable();
	}
	public TargetInfo toTargetInfo(Object instance){
		return _template.toTargetInfo(instance);
	}
	public void set(Object instance, Object val) throws IllegalAccessException{
		_template.set(instance, val);
	}
	public Object newInstance(){
		return _template.newInstance();
	}
}
