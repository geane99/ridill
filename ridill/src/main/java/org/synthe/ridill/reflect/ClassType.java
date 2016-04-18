package org.synthe.ridill.reflect;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Enum type that represents the type of object.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
enum ClassType {
	/**
	 * type of {@link Object}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	objectType,
	/**
	 * type of {@link Byte}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	byteType,
	/**
	 * type of {@link Boolean}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	booleanType,
	/**
	 * type of {@link Float}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	floatType,
	/**
	 * type of {@link Double}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	doubleType,
	/**
	 * type of {@link Short}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	shortType,
	/**
	 * type of {@link Integer}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	integerType,
	/**
	 * type of {@link Long}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	longType,
	/**
	 * type of {@link Character}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	characterType,
	/**
	 * type of {@link String}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	stringType,
	/**
	 * type of interface
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	interfaceType,
	/**
	 * type of abstract class
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	abstractType,
	/**
	 * type of pojo
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	domainType,
	/**
	 * type of {@link Enum}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	enumType,
	/**
	 * type of array
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	arrayType,
	/**
	 * type of annotation
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	annotationType,
	/**
	 * type of {@link Collection}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	collectionType,
	/**
	 * type of {@link List}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	listType,
	/**
	 * type of {@link Map}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	mapType,
	/**
	 * type of {@link Set}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	setType,
	/**
	 * type of {@link Queue}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	queueType,
	/**
	 * type of native
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	nativeType,
	;
	
	/**
	 * Get the type value from {@link Class}
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param clazz target class
	 * @return {@link ClassType}
	 */
	public static ClassType get(Class<?> clazz){
		if(clazz.getName().equals(Object.class.getName()))
			return ClassType.objectType;
        if(String.class.equals(clazz))
            return ClassType.stringType;
        if(Integer.TYPE.equals(clazz) || Integer.class.equals(clazz))
            return ClassType.integerType;
        if(Boolean.TYPE.equals(clazz) || Boolean.class.equals(clazz))
            return ClassType.booleanType;
        if(Float.TYPE.equals(clazz) || Float.class.equals(clazz))
            return ClassType.floatType;
        if(Double.TYPE.equals(clazz) || Double.class.equals(clazz))
            return ClassType.doubleType;
        if(Short.TYPE.equals(clazz) || Short.class.equals(clazz))
            return ClassType.shortType;
        if(Byte.TYPE.equals(clazz) || Byte.class.equals(clazz))
            return ClassType.byteType;
        if(Long.TYPE.equals(clazz) || Long.class.equals(clazz))
            return ClassType.longType;
        if(Character.TYPE.equals(clazz) || Character.class.equals(clazz))
            return ClassType.characterType;

        Class<?>[] interfaces = clazz.getInterfaces();
        if(clazz.equals(List.class) || contain(interfaces,List.class))
            return ClassType.listType;
        if(clazz.equals(Map.class) || contain(interfaces,Map.class))
            return ClassType.mapType;
        if(clazz.equals(Set.class) || contain(interfaces,Set.class))
            return ClassType.setType;
        if(clazz.equals(Queue.class) || contain(interfaces,Queue.class))
            return ClassType.queueType;
        if(clazz.equals(Collection.class) || contain(interfaces,Collection.class))
            return ClassType.collectionType;
        if(clazz.isAnnotation())
            return ClassType.annotationType;
        if(clazz.isInterface())
            return ClassType.interfaceType;
        if(clazz.isEnum())
            return ClassType.enumType;
        if(clazz.isArray())
            return ClassType.arrayType;
        if(Modifier.isAbstract(clazz.getModifiers()))
            return ClassType.abstractType;
        return ClassType.domainType;
    }
	
	/**
	 * To determine which contains the specified object to an array.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param array array
	 * @param target specified object
	 * @return when contain specified object, return true.
	 */
    private static <T> boolean contain(T[] array, T target){
        if(array == null)
            return false;
        for(T c : array){
            if(c.equals(target))
                return true;
        }
        return false;
    }
}
