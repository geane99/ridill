package org.synthe.ridill.reflect;

import org.synthe.ridill.generator.TargetInfo;

public class ReflectionInfo {
	public ReflectionInfo(Template template){
		_template = template;
	}
	
	private Template _template;
	
	public String className(){
		return _template.templateName();
	}
	public Class<?>[] interfaces(){
		return _template.template().getInterfaces();
	}
	public ClassType classType(){
		return _template.classType();
	}
	public boolean isImmutable(){
		return _template.isImmutable();
	}
	public TargetInfo toTargetInfo(Object instance){
		return _template.toTargetInfo(instance);
	}
	public Object newInstance(){
		return _template.newInstance();
	}
	public void set(Object instance, Object val) throws IllegalAccessException{
		//TODO impl
	}
}
