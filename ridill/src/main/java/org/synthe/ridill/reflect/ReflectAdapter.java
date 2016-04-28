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
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @param depth the depth of the object hierarchy
	 * @return generated value
	 */
	public Object getEmbedValue(ClassInfo info, Object enclosingInstance, Integer depth);
	
	/**
	 * It will be called when generating {@link Object} values.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Object getObjectValue(ClassInfo info, Object enclosingInstance, Integer depth);
	
	/**
	 * It will be called when generating enum value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Object getEnumValue(ClassInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Map} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Map<?,?>> T getMap(ClassInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link List} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends List<?>> T getList(ClassInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Set} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Set<?>> T getSet(ClassInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Collection} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Collection<?>> T getCollection(ClassInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when generating {@link Queue} interface.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public <T extends Queue<?>> T getQueue(ClassInfo info, Object enclosingInstance, Integer depth);
	/**
	 * It will be called when decide size of collection.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ClassInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Integer getCollectionSize(ClassInfo info, Object enclosingInstance, Integer depth);
}
