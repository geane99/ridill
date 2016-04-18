package org.synthe.ridill.reflect;

/**
 * Eum indicating whether the type belongs to which set.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
enum ClassOwnerType {
	/**
	 * belongs to the class itself
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	itself,
	/**
	 * belongs to the property
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	property,
	/**
	 * belongs to the return value
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	returnValue,
	/**
	 * belongs to the type parameter
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	typeParameter,
	;
}
