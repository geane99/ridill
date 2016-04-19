package org.synthe.ridill.reflect;


/**
 * Class that provides type information.<br/>
 * Template provides {@link Class} information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
abstract class Template{
	/**
	 * target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected Class<?> _template;
	/**
	 * parameterized type information for target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected Template[] _typeParameters;
	/**
	 * type information for target
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected ClassType _classType;
	/**
	 * where on whether target included
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected TemplateType _templateType;
	/**
	 * enclosing of target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected Template _enclosing;
	
	/**
	 * Get enclosing of target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return enclosing of target class
	 */
	public Template enclosing(){
		return _enclosing;
	}
	/**
	 * return target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return target class
	 */
	public Class<?> template(){
		return _template; 
	}
	/**
	 * return where on whether target included
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return where on whether target included
	 */
	public TemplateType templateType(){
		return _templateType;
	}
	/**
	 * return type information for target
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return type information for target
	 */
	public ClassType classType(){
		return _classType;
	}
	/**
	 * return new instance
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return new instance
	 */
	public Object newInstance(){
		//TODO impl
		return new Object();
	}
	/**
	 * return name of target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return name of target class
	 */
	public String templateName(){
		return _template.getName();
	}
	/**
	 * return target is immutalbe
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is immutable, return true.
	 */
	public boolean isImmutable(){
		return false;
//		return 
//			_ownerType == TemplateType.property && 
//			_owner instanceof Member && 
//			Modifier.isFinal(((Member)_owner).getModifiers());
	}
	/**
	 * return target is field
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is field, return true.
	 */
	public boolean isProperty(){
		return _templateType == TemplateType.property;
	}
	/**
	 * return target is type parameter<br/>
	 * type parameter of<br/>
	 *   class definition<br/>
	 *   method argument<br/>
	 *   property argument
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is type parameter, return true.
	 */
	public boolean isTypeParameter(){
		return _templateType == 
			TemplateType.methodTypeParameter || 
			_templateType == TemplateType.itsetfTypeParameters || 
			_templateType == TemplateType.propertyTypeParameters;
	}
	/**
	 * return target is interface
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is interface, return true.
	 */
	public boolean isInterface(){
		return _classType == ClassType.interfaceType;
	}
	/**
	 * return target is abstract
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is abstract, return true.
	 */
	public boolean isAbstract(){
		return _classType == ClassType.abstractType;
	}
	/**
	 * return target is annotation
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is annotation, return true.
	 */
	public boolean isAnnotation(){
		return _classType == ClassType.annotationType;
	}
	/**
	 * return target is object
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is object, return true.
	 */
	public boolean isObject(){
		return _classType == ClassType.objectType;
	}
	/**
	 * return target is array
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is array, return true.
	 */
	public boolean isArray(){
		return _classType == ClassType.arrayType;
	}
	/**
	 * return target is enumration
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is enum, return true.
	 */
	public boolean isEnum(){
		return _classType == ClassType.enumType;
	}
	/**
	 * return target is local class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is local class, return true.
	 */
	public boolean isLocalClass(){
		return _template.isLocalClass();
	}
	/**
	 * return target is member class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is member class, return true.
	 */
	public boolean isMemberClass(){
		return _template.isMemberClass();
	}
	/**
	 * return target is anonymous class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is anonymous class, return true.
	 */
	public boolean isAnonymousClass(){
		return _template.isAnonymousClass();
	}
//	public boolean isList(){
//		return _classType == ClassType.listType;
//	}
//	public boolean isMap(){
//		return _classType == ClassType.mapType;
//	}
//	public boolean isQueue(){
//		return _classType == ClassType.queueType;
//	}
//	public boolean isSet(){
//		return _classType == ClassType.setType;
//	}
//	public boolean isCollection(){
//		return _classType == ClassType.collectionType;
//	}
}
