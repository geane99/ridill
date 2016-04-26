package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.synthe.ridill.generator.TargetInfo;

/**
 * Provide {@link Class} information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
public class ClassInfo {
	/**
	 * {@link Template} with a {@link Class} information
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	protected Template _template;
	
	/**
	 * Return name of target class
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return name of target class
	 */
	public String className(){
		return _template.templateName();
	}
	/**
	 * Return the interfaces implemented by the class or interface represented by this class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return the interfaces implemented
	 */
	public Class<?>[] interfaces(){
		return _template.template().getInterfaces();
	}
	/**
	 * Return type information for target 
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return type information for target
	 */
	public ClassType classType(){
		return _template.classType();
	}
	/**
	 * Return target is immutalbe
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return target is immutalbe
	 */
	public boolean isImmutable(){
		return _template.isImmutable();
	}
	/**
	 * Convert to public object
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance convert to public object
	 * @return {@link TargetInfo}
	 */
	public TargetInfo toTargetInfo(Object instance){
		return _template.toTargetInfo(instance);
	}
	/**
	 * create new instance.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return new instance
	 * @throws IllegalAccessException when cant access constructor, thrown {@link IllegalAccessException}
	 * @throws InstantiationException see {@link InstantiationException}
	 * @throws InvocationTargetException when cant access constructor, thrown {@link InvocationTargetException}
	 */
	public Object newInstance() throws IllegalAccessException, InstantiationException, InvocationTargetException{
		return _template.newInstance();
	}
	/**
	 * set the value for property.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance instance to set a value
	 * @param value value
	 * @throws IllegalAccessException when cant access property, thrown {@link IllegalAccessException}
	 */
	public void set(Object instance, Object val) throws IllegalAccessException{
		if(_template.isProperty() || _template.isMethod()){
			PropertyTemplate prop = (PropertyTemplate)_template;
			prop.set(instance, val);
		}
	}
	/**
	 * get the value for property
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance instance to get a value
	 * @return set to an instance value
	 * @throws IllegalAccessException when cant access property, thrown {@link IllegalAccessException}
	 */
	@SuppressWarnings("unchecked")
	public <V> V get(Object instance) throws IllegalAccessException{
		if(_template.isProperty()){
			PropertyTemplate prop = (PropertyTemplate)_template;
			return (V)prop.get(instance);
		}
		return null;
	}
	/**
	 * get properties that belongs to this class info
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @return properties properties
	 */
	public List<ClassInfo> properties(){
		List<ClassInfo> r = new ArrayList<>();
		if(_template.templateType() == TemplateType.itself || _template.templateType() == TemplateType.property){
			ClassTemplate cTemplate = (ClassTemplate)_template;
			if(cTemplate.properties() != null){
				cTemplate.properties().forEach((t) -> {
					r.add(t.toClassInfo());
				});
			}
		}
		return r;
	}
	
	/**
	 * get type parameter at index.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param index index of array
	 * @return {@link ClassInfo}
	 */
	public ClassInfo typeParameterAt(Integer idx){
		Template t = _template.typeParameterAt(idx);
		ClassInfo typeInfo = t.toClassInfo();
		return typeInfo;
	}
}
