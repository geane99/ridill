package org.synthe.ridill.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.synthe.ridill.stub.TargetInfo;


/**
 * {@link Class} that provides type information.<br/>
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
	 * dimensions of array
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected Integer _dimensions;
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
		while(_parameterizedTypes != null && target != null){
			Map<String,Template> targetTemplateParameterizedTypes = 
				_parameterizedTypes.get(target.getName());
			
			if(targetTemplateParameterizedTypes != null && 
			   targetTemplateParameterizedTypes.containsKey(typeVariable.templateName())
			)
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
	 * Set dimensions of array
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param dimensions dimensions of array
	 */
	public void dimensions(Integer dimensions){
		_dimensions = dimensions;
	}
	/**
	 * Get dimensions of array
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return dimensions of array
	 */
	public Integer dimensions(){
		return _dimensions;
	}
	/**
	 * return new instance
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return new instance
	 * @throws IllegalAccessException when cant access constructor, thrown {@link IllegalAccessException}
	 * @throws InstantiationException see {@link InstantiationException}
	 * @throws InvocationTargetException when cant access constructor, thrown {@link InvocationTargetException}
	 */
	public Object newInstance() throws IllegalAccessException, InstantiationException, InvocationTargetException{
		return _newInstance(this, enclosing());
	}
	/**
	 * return new array instance
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param s size of array
	 * @return new instance
	 */
	public Object[] componentNewInstance(Integer...s){
		Class<?> arrayClass = null;
		Template typeParameter = typeParameterAt(0);
		if(typeParameter.classType() == ClassType.typeVariable && typeParameter.template() == null)
			arrayClass = Object.class;
		else
			arrayClass = typeParameter.template();
		
		int[] l = new int[s.length];
		for(int i = 0; i < s.length; i++)
			l[i] = s[i];
		
		return (Object[])Array.newInstance(arrayClass, l);
	}
	
	/**
	 * return new instance
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return new instance
	 * @throws IllegalAccessException when cant access constructor, thrown {@link IllegalAccessException}
	 * @throws InstantiationException see {@link InstantiationException}
	 * @throws InvocationTargetException when cant access constructor, thrown {@link InvocationTargetException}
	 */
	private Object _newInstance(Template target, Template enclosing) throws IllegalAccessException, InstantiationException, InvocationTargetException{
		Constructor<?> constructor = null;
		boolean isEnclosing = false;
		
		if(isLocalClass()){
			constructor = target.template().getEnclosingConstructor();
			isEnclosing = true;
		}
		else if(isAnonymousClass()){
			constructor = target.template().getEnclosingConstructor();
			isEnclosing = true;
		}
		else if(isMemberClass()){
			try{
				Class<?> enclosingClass = target.template().getDeclaringClass();
				constructor = target.template().getDeclaredConstructor(enclosingClass);
				isEnclosing = true;
			}
			catch(NoSuchMethodException nme){
				throw new InvocationTargetException(nme);
			}
		}
		
		if(isEnum())
			return Arrays.asList(_template.getEnumConstants());
		
		constructor = constructor == null ? getNoArgConsructor(target) : constructor;
		//TODO impl?
		if(constructor == null)
			throw new InstantiationException();
		
		Object[] params = null;
		if(isEnclosing)
			params = new Object[]{ enclosing.newInstance() };
		
		return constructor.newInstance(params);
		
	}
	
	/**
	 * find no argument constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target target
	 * @return no argument constructor
	 */
	private Constructor<?> getNoArgConsructor(Template target){
		Constructor<?>[] constructors = target.template().getDeclaredConstructors();
		if(constructors == null)
			return null;
		for(Constructor<?> each : constructors)
			if(each.getParameterCount() == 0)
				return each;
		return null;
	}
	
	/**
	 * Get interfaces that the {@link Class} impmenets.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return interfaces
	 */
	public Class<?>[] interfaces(){
		if(isAnnotation() || isInterface())
			return new Class<?>[]{_template};
		return _template.getInterfaces();
	}
	
	/**
	 * when class instance means singleton, return true.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when class instance mean singleton, return true.
	 */
	public Boolean isSingleton(){
		Constructor<?>[] constructors = _template.getDeclaredConstructors();
		if(constructors == null)
			return true;
		for(Constructor<?> each : constructors){
			if(!each.isAccessible())
				return false;
			if(each.isSynthetic())
				continue;
		}
		return true;
	}
	
	/**
	 * return name of target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return name of target class
	 */
	public String templateName(){
		if(_template != null)
			return _template.getName();
		if(_classType == ClassType.typeVariable && hasTypeParameters())
			return typeParameterAt(0).templateName();
		return null;
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
	 * @param index index of array
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
	 * replace type parameter
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param typeParameter {@link Template}
	 * @param index index
	 */
	public void replaceTypeParameterAt(Template typeParameter, Integer index){
		_typeParameters.set(index, typeParameter);
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
	 * return type parameters has type variable
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when type parameters has type variable, return true.
	 */
	public Boolean hasTypeVariableParameter(){
		if(_typeParameters != null)
			for(Template t : _typeParameters)
				if(t.classType() == ClassType.typeVariable)
					return true;
		return false;
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
	 * Convert the generic definition the argument as an real
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
	/**
	 * set access to true
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target {@link AccessibleObject}
	 */
	protected void setAccessControl(AccessibleObject target){
		PrivilegedAction<Object> action = () -> {
			target.setAccessible(true);
			return target;
		};
		AccessController.doPrivileged(action);
	}

	/**
	 * Convert this to {@link ClassInfo}
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return {@link ClassInfo}
	 */
	public ClassInfo toClassInfo(){
		return new ClassInfoImpl(this);
	}
	@Override
	public String toString(){
		if(!hasTypeParameters())
			return templateName();
		
		final StringBuilder b = new StringBuilder();
		typeParameters().forEach(t -> {
			if(b.length() > 0)
				b.append(", ");
			b.append(t.toString());
		});
		
		return templateName() + "<" + b.toString() + ">";
	}
	
	/**
	 * implementation of {@link ClassInfo}
	 * @author masahiko.ootsuki
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private class ClassInfoImpl extends ClassInfo{
		/**
		 * constructor
		 * @since 2015/01/18
		 * @version 1.0.0
		 * @param template this
		 */
		public ClassInfoImpl(Template template){
			this._template = template;
		}
	}
}
