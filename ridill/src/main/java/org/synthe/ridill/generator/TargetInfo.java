package org.synthe.ridill.generator;

/**
 * When creating a value,TargetInfo with the information that would be helpful.<br/>
 * 値を作る時、参考になる情報を持つインターフェース。<br/>
 * @since 2015/01/18
 * @author masahiko.ootsuki
 * @version 1.0.0
 */
public interface TargetInfo {
	/**
	 * return property name or method name.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return property name or method name.
	 */
	abstract public String name();
	/**
	 * return class name of return value or class name of property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return class name of return value.
	 */
	abstract public String classname();
	/**
	 * return name of enclosing class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return name of enclosing class.
	 */
	abstract public String eclosingClassName();
	/**
	 * when true, its a property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when true, its a property.
	 */
	abstract public Boolean isProperty();
	/**
	 * when true, its a method.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return when true, its a method.
	 */
	abstract public Boolean isMethod();
	/**
	 * Get instance of enclosing class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return instance of enclosing class.
	 */
	abstract public Object target();
}
