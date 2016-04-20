package org.synthe.ridill.reflect;



/**
 * It is InternalGeneratorStrategy for performing the process of {@link ClassType} by {@link Adapter}
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
interface InternalAdapterStrategy {
	/**
	 * Generate a value from the argument
	 * @param info {@link ReflectionInfo}
	 * @param adapter {@link Adapter}
	 * @param instance instance of enclosing class
	 * @param depth depth of recursive processing
	 * @return generated value
	 */
	public Object command(ReflectionInfo info, Adapter adapter, Object instance, Integer depth);
}
