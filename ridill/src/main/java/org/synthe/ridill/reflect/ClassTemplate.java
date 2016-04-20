package org.synthe.ridill.reflect;

import java.util.List;

import org.synthe.ridill.generator.TargetInfo;


/**
 * Class that provides type information.<br/>
 * ClassTemplate provides {@link Class} information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
public class ClassTemplate extends Template{
	/**
	 * see {@link FieldTemplate}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private List<Template> _properties;
	/**
	 * see {@link MethodTemplate}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private List<Template> _methods;
	/**
	 * constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target {@link Class}
	 */
	public ClassTemplate(Class<?> target){
		_template = target;
		_templateType = TemplateType.itself;
		_classType = ClassType.get(target);
	}
	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return null;
	}
	/**
	 * get properties that belongs to this temlpate
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return properties properties
	 */
	public List<Template> properties(){
		return _properties;
	}
	/**
	 * set properties that belongs to this temlpate
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param properties properties
	 */
	public void properties(List<Template> properties){
		_properties = properties;
	}
	/**
	 * get methods that belongs to this temlpate
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return methods
	 */
	public List<Template> methods(){
		return _methods;
	}
	/**
	 * set methods that belongs to this temlpate
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param methods methods
	 */
	public void methods(List<Template> methods){
		_methods = methods;
	}
}
