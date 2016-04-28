package org.synthe.ridill.generate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.synthe.ridill.reflect.ClassInfo;

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
	public Object get(ClassInfo info, Object enclosingInstance);
	/**
	 * generate collection#size
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public Integer size(ClassInfo info);
	/**
	 * generate collection instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Collection<?>> T collection(ClassInfo info);
	/**
	 * generate list instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends List<?>> T list(ClassInfo info);
	/**
	 * generate set instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Set<?>> T set(ClassInfo info);
	/**
	 * generate queue instance.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Queue<?>> T queue(ClassInfo info);
	/**
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param info information of the generated value.
	 * @return generated value
	 */
	public <T extends Map<?,?>> T dictionary(ClassInfo info);
}
