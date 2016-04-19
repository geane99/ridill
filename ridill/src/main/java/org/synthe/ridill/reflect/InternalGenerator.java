package org.synthe.ridill.reflect;

import java.util.List;

import org.synthe.ridill.ExtValueGenerator;
import org.synthe.ridill.TargetInfo;
import org.synthe.ridill.ValueGenerator;

/**
 * Class that the connection to {@link ValueGenerator} or {@link ExtValueGenerator} in generating value.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
class InternalGenerator {
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
	
	/**
	 * Connect to {@link ValueGenerator} or {@link ExtValueGenerator} when generating embed values.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectionInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	public Object generateEmbedValue(ReflectionInfo info, Object enclosingInstance){
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
			return generateObjectValue(info, enclosingInstance);
		}
		
		return _extValueGenerator.get(info, enclosingInstance);
	}
	
	/**
	 * Connect to {@link ValueGenerator} or {@link ExtValueGenerator} when generating {@link Object} values.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectionInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */	public Object generateObjectValue(ReflectionInfo info, Object enclosingInstance){
		if(isSimple())
			return new Object();
		return _extValueGenerator.get(info, enclosingInstance);
	}
	
	/**
	 * Connect to {@link ValueGenerator} or {@link ExtValueGenerator} when generating enum value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info {@link ReflectionInfo}
	 * @param enclosingInstance instance of enclosing class.
	 * @return generated value
	 */
	@SuppressWarnings("unchecked")
	public Object generateEnumValue(ReflectionInfo info, Object enclosingInstance){
		if(isSimple()){
			TargetInfo gi = info.toTargetInfo(enclosingInstance);
			List<Enum<?>> enums = (List<Enum<?>>)info.newInstance();
			return _generator.getEnum(gi, enums);
		}
		return _extValueGenerator.get(info, enclosingInstance);
	}
}
