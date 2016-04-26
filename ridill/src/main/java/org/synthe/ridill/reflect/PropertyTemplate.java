package org.synthe.ridill.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Class that provides type information.<br/>
 * Template provides {@link AccessibleObject} information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
abstract class PropertyTemplate extends ClassTemplate{
	/**
	 * constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target
	 */
	public PropertyTemplate(Class<?> target) {
		super(target);
	}
	/**
	 * Get property name. (field name or method name)
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return property name.
	 */
	abstract public String propertyName();
	/**
	 * set the value for property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance instance to set a value
	 * @param value value
	 * @throws IllegalAccessException when cant access property, thrown {@link IllegalAccessException}
	 */
	abstract public void set(Object instance, Object value) throws IllegalAccessException;
	/**
	 * get the value for property
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance instance to get a value
	 * @return set to an instance value
	 * @throws IllegalAccessException when cant access property, thrown {@link IllegalAccessException}
	 */
	abstract public Object get(Object instance) throws IllegalAccessException;
	/**
	 * method call in an instance
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance instance to call
	 * @param args method arguments
	 * @return return value
	 * @throws InvocationTargetException when call failed, thrown {@link InvocationTargetException}
	 * @throws IllegalAccessException when cant access property, thrown {@link IllegalAccessException}
	 */
	abstract public Object invoke(Object instance, Object...args) throws InvocationTargetException, IllegalAccessException;

}
