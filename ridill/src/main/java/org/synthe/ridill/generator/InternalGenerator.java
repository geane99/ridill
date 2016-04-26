package org.synthe.ridill.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.synthe.ridill.reflect.ReflectAdapter;
import org.synthe.ridill.reflect.ClassType;
import org.synthe.ridill.reflect.ReflectInfo;

/**
 * Class that the connection to {@link ValueGenerator} or {@link ExtValueGenerator} in generating value.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
class InternalGenerator implements ReflectAdapter{
	/**
	 * see {@link ExtValueGenerator}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 */
	private ExtValueGenerator _extValueGenerator;
	/**
	 * see {@link ValueGenerator}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 */
	private ValueGenerator _generator;
	/**
	 * flag of use {@link ValueGenerator}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 */
	private Boolean _isSimple = true;
	/**
	 * constructor of use {@link ExtValueGenerator}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param extGenerator {@link ExtValueGenerator}
	 */
	public InternalGenerator(ExtValueGenerator extGenerator){
		_extValueGenerator = extGenerator;
		_isSimple = false;
	}
	/**
	 * constructor of user {@link ValueGenerator}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param generator {@link ValueGenerator}
	 */
	public InternalGenerator(ValueGenerator generator){
		_generator = generator;
		_isSimple = true;
	}
	/**
	 * when generator to be used {@link ValueGenerator}, return true.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @return when generator to be used {@link ValueGenerator}, return true.
	 */
	private Boolean isSimple(){
		return _isSimple;
	}

	@Override
	public Object getEmbedValue(ReflectInfo info, Object enclosingInstance, Integer depth){
		if(isSimple()){
			TargetInfo gi = info.toTargetInfo(enclosingInstance);
			
			if(info.classType() == ClassType.byteType)
				return _generator.getByte(gi);
			if(info.classType() == ClassType.booleanType)
				return _generator.getBoolean(gi);
			if(info.classType() == ClassType.floatType)
				return _generator.getFloat(gi);
			if(info.classType() == ClassType.doubleType)
				return _generator.getDouble(gi);
			if(info.classType() == ClassType.shortType)
				return _generator.getShort(gi);
			if(info.classType() == ClassType.integerType)
				return _generator.getInteger(gi);
			if(info.classType() == ClassType.longType)
				return _generator.getLong(gi);
			if(info.classType() == ClassType.characterType)
				return _generator.getCharacter(gi);
			if(info.classType() == ClassType.stringType)
				return _generator.getString(gi);
			return getObjectValue(info, enclosingInstance, depth);
		}
		
		return _extValueGenerator.get(info, enclosingInstance);
	}
	
	@Override
	public Object getObjectValue(ReflectInfo info, Object enclosingInstance, Integer depth){
		if(isSimple())
			return new Object();
		return _extValueGenerator.get(info, enclosingInstance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getEnumValue(ReflectInfo info, Object enclosingInstance, Integer depth){
		if(isSimple()){
			TargetInfo gi = info.toTargetInfo(enclosingInstance);
			List<Enum<?>> enums = (List<Enum<?>>)info.newInstance();
			return _generator.getEnum(gi, enums);
		}
		return _extValueGenerator.get(info, enclosingInstance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Map<?, ?>> T getMap(ReflectInfo info, Object enclosingInstance, Integer depth) {
		if(isSimple())
			return (T)new HashMap<>();
		return _extValueGenerator.dictionary(info);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends List<?>> T getList(ReflectInfo info, Object enclosingInstance, Integer depth) {
		if(isSimple())
			return (T)new ArrayList<>();
		return _extValueGenerator.list(info);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Set<?>> T getSet(ReflectInfo info, Object enclosingInstance, Integer depth) {
		if(isSimple())
			return (T)new HashSet<>();
		return _extValueGenerator.set(info);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Collection<?>> T getCollection(ReflectInfo info, Object enclosingInstance, Integer depth) {
		if(isSimple())
			return (T)new ArrayList<>();
		return _extValueGenerator.collection(info);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Queue<?>> T getQueue(ReflectInfo info, Object enclosingInstance, Integer depth) {
		if(isSimple())
			return (T)new LinkedList<>();
		return _extValueGenerator.queue(info);
	}
	@Override
	public Integer getCollectionSize(ReflectInfo info, Object enclosingInstance, Integer depth) {
		if(isSimple())
			return _generator.getCollectionSize(info.toTargetInfo(enclosingInstance));
		return _extValueGenerator.size(info);
	}
}
