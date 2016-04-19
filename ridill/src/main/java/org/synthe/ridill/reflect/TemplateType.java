package org.synthe.ridill.reflect;

/**
 * Eum indicating whether the type belongs to which set.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
enum TemplateType {
	/**
	 * belongs to the class itself
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	itself,
	/**
	 * belongs to the class itself type parameters
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	itsetfTypeParameters,
	/**
	 * belongs to the property
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	property,
	/**
	 * belongs to the type parameters of property 
	 */
	propertyTypeParameters,
	/**
	 * belongs to the returnValue
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	returnValue,
	/**
	 * belongs to the argument of method
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	methodArgument,
	/**
	 * belongs to the type parameters of method
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	methodTypeParameter,
	/**
	 * belongs to the type parameter
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	typeParameter,
	;
}
