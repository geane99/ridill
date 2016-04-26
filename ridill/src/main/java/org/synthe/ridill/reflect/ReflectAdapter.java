package org.synthe.ridill.reflect;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * ReflectAdapter that will be called in various structual analysis.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
public interface ReflectAdapter {
	/**
	 * It will be called when generating embed values.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @param depth the depth of the object hierarchy
	 * @return generated value
	 */
	public Object getEmbedValue(ReflectInfo info, Object enclosingInstance, Integer depth);
	
	/**
	 * It will be called when generating {@link Object} values.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Object getObjectValue(ReflectInfo info, Object enclosingInstance, Integer depth);
	
	/**
	 * It will be called when generating enum value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Object getEnumValue(ReflectInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Map} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Map<?,?>> T getMap(ReflectInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link List} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends List<?>> T getList(ReflectInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Set} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Set<?>> T getSet(ReflectInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Collection} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Collection<?>> T getCollection(ReflectInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Queue} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Queue<?>> T getQueue(ReflectInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when decide size of collection.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Integer getCollectionSize(ReflectInfo info, Object enclosingInstance, Integer depth);
}
