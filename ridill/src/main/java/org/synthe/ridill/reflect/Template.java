package org.synthe.ridill.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.synthe.ridill.generator.TargetInfo;


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
	protected List<Template> _typeParameters;
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
	 * set In generics definition and parameterized type are paired<br/>
	 * <pre>
	 * {@code
	 *   Map<String,Template> thisClassGenericParameters = _parameterizedTypStore.get(_tempalte.getName()); //mean "Class#getName()"
	 *   Map<String,Template> superClassGenericsParameters = _parameterizedTypStore.get(_tempalte.getSuperclass().getName());
	 *   
	 *   thisClassGenericParameters.put("K", keyTemplate);
	 *   thisClassGenericParameters.put("V", valueTemplate);
	 * }
	 * </pre>
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected Map<String, Map<String,Template>> _parameterizedTypes;
	/**
	 * put a pair of generics definition and parameterized type to target class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param enclosing target class
	 * @param typeVariable generics definition
	 * @param real parameterized type
	 */
	public void addParameterizedType(Class<?> enclosing, Template typeVariable, Template real){
		if(_parameterizedTypes == null)
			_parameterizedTypes = new HashMap<>();
		String enclosingName = enclosing.getName();
		
		Map<String,Template> targetTemplateParameterizedTypes = _parameterizedTypes.get(enclosingName);
		if(targetTemplateParameterizedTypes == null){
			targetTemplateParameterizedTypes = new HashMap<>();
			_parameterizedTypes.put(enclosingName, targetTemplateParameterizedTypes);
		}
		targetTemplateParameterizedTypes.put(typeVariable.templateName(), real);
	}
	/**
	 * put a pair of generics definition and parameterized type to target class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param enclosing target class
	 * @param real parameterized types
	 */
	public void addRealParameterizedTypes(Class<?> enclosing, Map<String, Template> realParameterizedTypes){
		if(_parameterizedTypes == null)
			_parameterizedTypes = new HashMap<>();
		_parameterizedTypes.put(enclosing.getName(), realParameterizedTypes);
	}
	
	/**
	 * find pairs generics definition and parameterized type contained in target class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param typeVariable generics definition
	 * @return parameterized type, which is a generics definition and a pair
	 */
	public Template findEnclosingParameterizedTypeByTypeVariable(Template typeVariable){
		Class<?> target = _template;
		while(target != null){
			Map<String,Template> targetTemplateParameterizedTypes = _parameterizedTypes.get(target.getName());
			if(targetTemplateParameterizedTypes != null && targetTemplateParameterizedTypes.containsKey(typeVariable.templateName()))
				return targetTemplateParameterizedTypes.get(typeVariable.templateName());
			target = target.getSuperclass();
		}
		return null;
	}
	
	/**
	 * convert to public object
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance instance of target class
	 * @return {@link TargetInfo public object}
	 */
	abstract public TargetInfo toTargetInfo(Object instance);
	/**
	 * getter enclosing
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return enclosing of target class
	 */
	public Template enclosing(){
		return _enclosing;
	}
	/**
	 * setter enclosing
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param enclosing enclosing of target class
	 */
	public void enclosing(Template enclosing){
		_enclosing = enclosing;
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
	public Object newInstance() throws IllegalAccessException, InstantiationException{
		//TODO impl
		return _template.newInstance();
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
	 * getter type parameters
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return type parameters
	 */
	public List<Template> typeParameters(){
		return _typeParameters;
	}
	/**
	 * setter type parameters
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param typeParameters type parameters
	 */
	public void typeParameters(List<Template> typeParameters){
		_typeParameters = typeParameters;
	}
	/**
	 * get type parameter at index.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param index array index
	 * @return {@link Template}
	 */
	public Template typeParameterAt(Integer index){
		return _typeParameters.get(index);
	}
	/**
	 * add type parameter 
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param typeParameter {@link Template}
	 */
	public void addTypeParameter(Template typeParameter){
		if(_typeParameters == null)
			_typeParameters = new ArrayList<>();
		_typeParameters.add(typeParameter);
	}
	/**
	 * return type parameters isnt empty.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when type parameters isnot empty, return true.
	 */
	public Boolean hasTypeParameters(){
		return _typeParameters != null && _typeParameters.size() > 0;
	}
	/**
	 * remove all type parameters
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	public void clearTypeParameters(){
		_typeParameters = new ArrayList<>();
	}
	/**
	 * return target is immutalbe
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is immutable, return true.
	 */
	public Boolean isImmutable(){
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
	public Boolean isProperty(){
		return _templateType == TemplateType.property;
	}
	/**
	 * return target is method
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is method arg or method type parameter or return value, return true.
	 */
	public Boolean isMethod(){
		return 
			_templateType == TemplateType.methodArgument ||
			_templateType == TemplateType.methodTypeParameter ||
			_templateType == TemplateType.returnValue;
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
	public Boolean isTypeParameter(){
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
	public Boolean isInterface(){
		return _classType == ClassType.interfaceType;
	}
	/**
	 * return target is abstract
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is abstract, return true.
	 */
	public Boolean isAbstract(){
		return _classType == ClassType.abstractType;
	}
	/**
	 * return target is annotation
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is annotation, return true.
	 */
	public Boolean isAnnotation(){
		return _classType == ClassType.annotationType;
	}
	/**
	 * return target is object
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is object, return true.
	 */
	public Boolean isObject(){
		return _classType == ClassType.objectType;
	}
	/**
	 * return target is array
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is array, return true.
	 */
	public Boolean isArray(){
		return _classType == ClassType.arrayType;
	}
	/**
	 * return target is enumration
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is enum, return true.
	 */
	public Boolean isEnum(){
		return _classType == ClassType.enumType;
	}
	/**
	 * return target is local class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is local class, return true.
	 */
	public Boolean isLocalClass(){
		return _template.isLocalClass();
	}
	/**
	 * return target is member class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is member class, return true.
	 */
	public Boolean isMemberClass(){
		return _template.isMemberClass();
	}
	/**
	 * return target is anonymous class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is anonymous class, return true.
	 */
	public Boolean isAnonymousClass(){
		return _template.isAnonymousClass();
	}
	/**
	 * return target is primitive or primitive wrapper
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when target is primitive or primitive wrapper, return true
	 */
	public Boolean isEmbedClass(){
		return 
			_classType == ClassType.booleanType ||
			_classType == ClassType.byteType ||
			_classType == ClassType.characterType ||
			_classType == ClassType.doubleType ||
			_classType == ClassType.floatType ||
			_classType == ClassType.integerType ||
			_classType == ClassType.longType ||
			_classType == ClassType.objectType ||
			_classType == ClassType.nativeType ||
			_classType == ClassType.shortType ||
			_classType == ClassType.stringType;
	}
	
	/**
	 * To convert the generic definition the argument as an real
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param real an entity
	 */
	public void real(Template real){
		_template = real.template();
		_parameterizedTypes = real._parameterizedTypes;
		_classType = real.classType();
		_typeParameters = real.typeParameters();
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
