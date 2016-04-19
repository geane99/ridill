package org.synthe.ridill;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.synthe.ridill.reflect.ReflectionInfo;

/**
 * ExtValueGenerator that generates the value of the stub.<br/>
 * usage:
 * <pre>
 * {@code
 * @Override
 * public String get(ReflectionInfo info){
 *   if(info.templateName().equals(Member.class.getName()) && info.name().equals("firstName"))
 *     return "foobar";
 *   .
 *   .
 *   .
 * }
 * </pre>
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
public interface ExtValueGenerator{
	/**
	 * generate property or method return value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @param enclosingInstance instance of property enclosing instance
	 * @return generated value
	 */
	public Object get(ReflectionInfo info, Object enclosingInstance);
	/**
	 * generate collection#size
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public Integer size(ReflectionInfo info);
	/**
	 * generate collection instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Collection<?>> T collection(ReflectionInfo info);
	/**
	 * generate list instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends List<?>> T list(ReflectionInfo info);
	/**
	 * generate set instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Set<?>> T set(ReflectionInfo info);
	/**
	 * generate queue instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Queue<?>> T queue(ReflectionInfo info);
	/**
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Map<?,?>> T dictionary(ReflectionInfo info);
}
