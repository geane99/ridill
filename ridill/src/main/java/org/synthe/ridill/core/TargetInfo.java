package org.synthe.ridill.core;

/**
 * When creating a value,TargetInfo with the information that would be helpful.<br/>
 * 値を作る時、参考になる情報を持つクラス。<br/>
 * @since 2015/01/18
 * @author masahiko.ootsuki
 * @version 1.0.0
 */
public class TargetInfo {
	/**
	 * when true, its a method.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private boolean _isMethod;
	/**
	 * method name.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private String _methodName;
	/**
	 * when apply method, the type of return value.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private String _returnClassName;
	/**
	 * when true, its a property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private boolean _isProperty;
	/**
	 * property name.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private String _propertyName;
	/**
	 * type of property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private String _propertyClassName;
	/**
	 * name of enclosing class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private String _ownerClassName;
	/**
	 * instance of enclosing class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private Object _target;
	
	/**
	 * create an intstance with a method information to the reference.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target enclosing class instance.
	 * @param methodName method name.
	 * @param returnClassName when apply method, the type of return value.
	 */
	public TargetInfo(Object target, String methodName, String returnClassName){
		_target = target;
		_methodName = methodName;
		_returnClassName = returnClassName;
		_isMethod = true;
		_isProperty = false;
	}
	/**
	 * create an intstance with a property information to the reference.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target instanceof enclosing class.
	 * @param propertyName property name.
	 * @param propertyClassName property type.
	 * @param ownerClassName name of enclosing class.
	 */
	public TargetInfo(Object target, String propertyName, String propertyClassName, String ownerClassName){
		_target = target;
		_propertyName = propertyName;
		_propertyClassName = propertyClassName;
		_ownerClassName = ownerClassName;
		_isMethod = false;
		_isProperty = true;
	}
	/**
	 * return instanceof enclosing class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return instanceof enclosing class.
	 */
	public Object getTarget(){
		return _target;
	}
	/**
	 * when true, its a method.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when true, its a method.
	 */
	public boolean isMethod(){
		return _isMethod;
	}
	/**
	 * when true, its a property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when true, its a property.
	 */
	public boolean isProperty(){
		return _isProperty;
		
	}
	/**
	 * return method name.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return method name.
	 */
	public String methodName(){
		return _methodName;
	}
	/**
	 * return class name of return value.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return class name of return value.
	 */
	public String returnClassName(){
		return _returnClassName;
	}
	/**
	 * return property name.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return property name.
	 */
	public String propertyName(){
		return _propertyName;
	}
	/**
	 * return name of property class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return name of property class.
	 */
	public String propertyClassName(){
		return _propertyClassName;
	}
	/**
	 * return name of enclosing class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return name of enclosing class.
	 */
	public String ownerClassName(){
		return _ownerClassName;
	}
}
