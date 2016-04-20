package org.synthe.ridill.reflect;

import java.util.List;

import org.synthe.ridill.generator.TargetInfo;


public class ClassTemplate extends Template{
	private List<Template> _properties;
	private List<Template> _methods;
	
	public ClassTemplate(Class<?> target){
		_template = target;
		_templateType = TemplateType.itself;
		_classType = ClassType.get(target);
	}
	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return null;
	}
	public List<Template> properties(){
		return _properties;
	}
	public void properties(List<Template> properties){
		_properties = properties;
	}
	public List<Template> methods(){
		return _methods;
	}
	public void methods(List<Template> methods){
		_methods = methods;
	}
}
