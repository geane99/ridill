package org.synthe.ridill.reflect;

import java.lang.reflect.TypeVariable;

import org.synthe.ridill.generator.TargetInfo;

class ParameterTemplate extends Template{
	private TypeVariable<?> _typeVariable;
	
	public ParameterTemplate(TemplateType templateType, TypeVariable<?> typeVariable, Template enclosing){
		_templateType = templateType;
		enclosing(enclosing);
		initByTypeVariable((TypeVariable<?>)typeVariable);
	}
	public ParameterTemplate(Class<?> type, Template enclosing){
		enclosing(enclosing);
		initByClass((Class<?>)type);
	}
	public void real(Template real){
		_template = real.template();
		_typeParameters = real.typeParameters();
	}
	public Boolean hasReal(){
		return _template != null;
	}
	
	public Boolean isTypeVariableParameter(){
		return _typeVariable != null;
	}
	
	public Boolean isRealParameter(){
		return !isTypeVariableParameter();
	}
	
	private void initByTypeVariable(TypeVariable<?> typeVariable){
		_typeVariable = typeVariable;
		_templateType = TemplateType.typeParameter;
	}
	
	private void initByClass(Class<?> type){
		_template = type;
		_classType = ClassType.get(type);
		_templateType = TemplateType.typeParameter;
	}
	@Override
	public Class<?> template(){
		if(hasReal())
			return _template;
		if(isRealParameter())
			return _template;
		return null;
	}
	@Override
	public String templateName(){
		Class<?> target = template();
		if(target != null)
			return _template.getName();
		return _typeVariable.getTypeName();
	}
	@Override
	public Boolean isLocalClass(){
		Class<?> target = template();
		if(target != null)
			return target.isLocalClass();
		return false;
	}
	@Override
	public Boolean isMemberClass(){
		Class<?> target = template();
		if(target != null)
			return target.isMemberClass();
		return false;
	}
	@Override
	public Boolean isAnonymousClass(){
		Class<?> target = template();
		if(target != null)
			return target.isAnonymousClass();
		return false;
	}
	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return null;
	}	
}
