package org.synthe.ridill.reflect;

import java.lang.reflect.TypeVariable;

import org.synthe.ridill.stub.TargetInfo;

/**
 * Class that provides type information.<br/>
 * ParameterTemplate provides type parameter information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
class TypeParameterTemplate extends ClassTemplate{
	/**
	 * generics definiton
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private TypeVariable<?> _typeVariable;
	/**
	 * constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param templateType {@link TemplateType}
	 * @param typeVariable generics definition
	 * @param enclosing template enclosing the generics definition 
	 */
	public TypeParameterTemplate(TemplateType templateType, TypeVariable<?> typeVariable, Template enclosing){
		_templateType = templateType;
		enclosing(enclosing);
		initByTypeVariable((TypeVariable<?>)typeVariable);
	}
	/**
	 * constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param type {@link Class} of type parameter
	 * @param enclosing template enclosing the type parameter
	 */
	public TypeParameterTemplate(Class<?> type, Template enclosing){
		enclosing(enclosing);
		initByClass((Class<?>)type);
	}
	/**
	 * has pair of generics definition and parameterized type
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when this template has pair of generics definition and parameterized type, retrun true.
	 */
	public Boolean hasReal(){
		return _template != null;
	}
	/**
	 * this template is generics definition
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when this template is generics definition, return true
	 */
	public Boolean isTypeVariableParameter(){
		return _typeVariable != null;
	}
	
	/**
	 * this template isnt generics definition
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when this template isnt generics definition, return true
	 */
	public Boolean isRealParameter(){
		return !isTypeVariableParameter();
	}
	/**
	 * initialized with generics definition
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param typeVariable generics definition
	 */
	private void initByTypeVariable(TypeVariable<?> typeVariable){
		_typeVariable = typeVariable;
		_templateType = TemplateType.typeParameter;
		_classType = ClassType.typeVariable;
	}
	/**
	 * initialized with type parameter
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param type type parameter
	 */
	private void initByClass(Class<?> type){
		_template = type;
		_classType = ClassType.get(type);
		_templateType = TemplateType.typeParameter;
	}
	/**
	 * Get typeVariable
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return typeVariable
	 */
	public TypeVariable<?> typeVariable(){
		return _typeVariable;
	}
	@Override
	public String toString(){
		if(_typeVariable != null)
			return _typeVariable.getTypeName();
		return super.toString();
	}
	@Override
	public void real(Template real){
		super.real(real);
		if(template() != null)
			_typeVariable = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.Template#template()
	 */
	@Override
	public Class<?> template(){
		if(hasReal())
			return _template;
		if(isRealParameter())
			return _template;
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.Template#templateName()
	 */
	@Override
	public String templateName(){
		Class<?> target = template();
		if(target != null)
			return _template.getName();
		return _typeVariable.getTypeName();
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.Template#isLocalClass()
	 */
	@Override
	public Boolean isLocalClass(){
		Class<?> target = template();
		if(target != null)
			return target.isLocalClass();
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.Template#isMemberClass()
	 */
	@Override
	public Boolean isMemberClass(){
		Class<?> target = template();
		if(target != null)
			return target.isMemberClass();
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.Template#isAnonymousClass()
	 */
	@Override
	public Boolean isAnonymousClass(){
		Class<?> target = template();
		if(target != null)
			return target.isAnonymousClass();
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.Template#toTargetInfo(java.lang.Object)
	 */
	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return null;
	}	
}
