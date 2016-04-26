package org.synthe.ridill.reflect;

import java.util.ArrayList;
import java.util.List;

import org.synthe.ridill.generator.TargetInfo;

public class ReflectInfo {
	public ReflectInfo(Template template){
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
		if(_template.isProperty()){
			PropertyTemplate prop = (PropertyTemplate)_template;
			prop.set(instance, val);
		}
	}
	@SuppressWarnings("unchecked")
	public <V> V get(Object instance) throws IllegalAccessException{
		if(_template.isProperty()){
			PropertyTemplate prop = (PropertyTemplate)_template;
			return (V)prop.get(instance);
		}
		return null;
	}
	public List<ReflectInfo> properties(){
		List<ReflectInfo> r = new ArrayList<>();
		if(_template.templateType() == TemplateType.itself || _template.templateType() == TemplateType.property){
			ClassTemplate cTemplate = (ClassTemplate)_template;
			if(cTemplate.properties() != null){
				cTemplate.properties().forEach((t) -> {
					r.add(new ReflectInfo(t));
				});
			}
		}
		return r;
	}
	
	public ReflectInfo typeParameterAt(Integer idx){
		Template t = _template.typeParameterAt(idx);
		ReflectInfo typeInfo = new ReflectInfo(t);
		return typeInfo;
	}
}
