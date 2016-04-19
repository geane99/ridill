package org.synthe.ridill.reflect;


abstract class Template{
	protected Class<?> _template;
	protected Template[] _typeParameters;
	protected ClassType _classType;
	protected TemplateType _templateType;
	protected Template _enclosing;
	
	
	public Template enclosing(){
		return _enclosing;
	}
	
	public Class<?> template(){
		return _template; 
	}
	
	public TemplateType templateType(){
		return _templateType;
	}
	
	public ClassType classType(){
		return _classType;
	}
	
	public Object newInstance(){
		//TODO impl
		return new Object();
	}
	
	public String templateName(){
		return _template.getName();
	}
	
	public boolean isImmutable(){
		return false;
//		return 
//			_ownerType == TemplateType.property && 
//			_owner instanceof Member && 
//			Modifier.isFinal(((Member)_owner).getModifiers());
	}
	
	public boolean isProperty(){
		return _templateType == TemplateType.property;
	}
	public boolean isReturnValue(){
		return _templateType == TemplateType.returnValue;
	}
	public boolean isMethodArgument(){
		return _templateType == TemplateType.methodArgument;
	}
	public boolean isMethodTypeParameter(){
		return _templateType == TemplateType.methodTypeParameter;
	}
	public boolean isTypeParameter(){
		return _templateType == 
			TemplateType.methodTypeParameter || 
			_templateType == TemplateType.itsetfTypeParameters || 
			_templateType == TemplateType.propertyTypeParameters;
	}
	public boolean isInterface(){
		return _classType == ClassType.interfaceType;
	}
	public boolean isAbstract(){
		return _classType == ClassType.abstractType;
	}
	public boolean isAnnotation(){
		return _classType == ClassType.annotationType;
	}
	public boolean isObject(){
		return _classType == ClassType.objectType;
	}
	public boolean isList(){
		return _classType == ClassType.listType;
	}
	public boolean isMap(){
		return _classType == ClassType.mapType;
	}
	public boolean isQueue(){
		return _classType == ClassType.queueType;
	}
	public boolean isSet(){
		return _classType == ClassType.setType;
	}
	public boolean isCollection(){
		return _classType == ClassType.collectionType;
	}
	public boolean isArray(){
		return _classType == ClassType.arrayType;
	}
	public boolean isEnum(){
		return _classType == ClassType.enumType;
	}
	public boolean isLocalClass(){
		return _template.isLocalClass();
	}
	public boolean isMemberClass(){
		return _template.isMemberClass();
	}
	public boolean isAnonymousClass(){
		return _template.isAnonymousClass();
	}
}
