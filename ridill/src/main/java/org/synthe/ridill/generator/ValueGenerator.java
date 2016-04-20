package org.synthe.ridill.generator;

import java.util.List;

/**
 * ValueGenerator that generates the value of the stub.<br/>
 * usage:
 * <pre>
 * {@code
 * @Override
 * public String getString(TargetInfo info){
 *   if(info.isProperty() && info.name().toLowerCase().contain("firstname"))
 *     return "foo";
 *   if(info.isProperty() && info.name().toLowerCase().contain("lastname"))
 *     return "bar";
 *   .
 *   .
 *   .
 * }
 * </pre>
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
public interface ValueGenerator {
	/**
	 * generate byte value.
	 * @param info information of the generated value.
	 * @return byte value
	 */
	public Byte getByte(TargetInfo info);
	/**
	 * generate boolean value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return boolean value.
	 */
	public Boolean getBoolean(TargetInfo info);
	/**
	 * generate float value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return float value.
	 */
	public Float getFloat(TargetInfo info);
	/**
	 * generate double value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return double value.
	 */
	public Double getDouble(TargetInfo info);
	/**
	 * generate short value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return short value.
	 */
	public Short getShort(TargetInfo info);
	/**
	 * generate int value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return int value.
	 */
	public Integer getInteger(TargetInfo info);
	/**
	 * generate long value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return long value.
	 */
	public Long getLong(TargetInfo info);
	/**
	 * generate char value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return char value.
	 */
	public Character getCharacter(TargetInfo info);
	/**
	 * generate string value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return string value.
	 */
	public String getString(TargetInfo info);
	/**
	 * generate collection#size.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return collection#size.
	 */
	public Integer getCollectionSize(TargetInfo info);
	/**
	 * select enum object.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param  info information of the generated value. 
	 * @return boolean value.
	 * @return choosen enumeration.
	 */
	public Enum<?> getEnum(TargetInfo info, List<Enum<?>> enums);
}
