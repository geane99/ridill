package org.synthe.ridill.reflect;


/**
 * It is InternalGeneratorStrategy for performing the process of {@link ClassType} by {@link InternalGenerator}
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
interface InternalGeneratorStrategy {
	/**
	 * Generate a value from the argument
	 * @param info {@link ReflectionInfo}
	 * @param adapter {@link InternalGenerator}
	 * @param instance instance of enclosing class
	 * @return generated value
	 */
	public Object create(ReflectionInfo info, InternalGenerator adapter, Object instance);
}
