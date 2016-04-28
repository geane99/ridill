package org.synthe.ridill.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TemplateFactory to generate a wrapper {@link Class}.<br/>
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
class TemplateFactory {
	/**
	 * Generate the {@link Template} from the return value.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param method method that will be called
	 * @param instance instance of the method to be invoked
	 * @param args parameters 
	 * @return {@link Template}
	 */
	public Template createByReturnType(Method method, Object instance, Object...args){
		Class<?> returnType = method.getReturnType();
		Type typeParameter = method.getGenericReturnType();
		
		Class<?> enclosing = instance != null && !(instance instanceof Proxy) ? 
			instance.getClass() : 
			method.getDeclaringClass();

		Template enclosingTemplate = createByClassType(enclosing, null);
		
		//return type is type parameter of class
		if(typeParameter != null && typeParameter instanceof TypeVariable<?>){
			TypeParameterTemplate typeParameterTemplate = (TypeParameterTemplate)createByTypeVariable(
				enclosingTemplate, 
				(TypeVariable<?>)typeParameter, 
				TemplateType.methodTypeParameter
			);
			ClassTemplate real = (ClassTemplate)enclosingTemplate
				.findEnclosingParameterizedTypeByTypeVariable(typeParameterTemplate);
			if(real != null){
				MethodTemplate template = new MethodTemplate(method, real);
				return template;
			}
			else{
				MethodTemplate template = new MethodTemplate(method, typeParameterTemplate);
				return template;
			}
		}
		else{
			ClassTemplate real = (ClassTemplate)createByClassType(returnType, enclosingTemplate);
			MethodTemplate template = new MethodTemplate(method, real);
			return template;
		}
	}
	
	/**
	 * Generate the {@link Template} from the {@link Class}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param clazz target 
	 * @return {@link Template}
	 */
	public ClassTemplate createByClassType(Class<?> clazz, Template enclosing){
		ClassTemplate template = new ClassTemplate(clazz);
		if(template.isLocalClass()){
			if(enclosing != null)
				template.enclosing(enclosing);
			else{
				Class<?> enclosingClass = clazz.getEnclosingClass();
				Template nestEnclosing = createByClassType(enclosingClass, null);
				template.enclosing(nestEnclosing);
			}
		}
		if(template.isMemberClass()){
			if(enclosing != null)
				template.enclosing(enclosing);
			else{
				Class<?> enclosingClass = clazz.getDeclaringClass();
				Template nestEnclosing = createByClassType(enclosingClass, null);
				template.enclosing(nestEnclosing);
			}
		}
		if(template.isAnonymousClass()){
			if(enclosing != null)
				template.enclosing(enclosing);
			else{
				Class<?> enclosingClass = clazz.getEnclosingClass();
				Template nestEnclosing = createByClassType(enclosingClass, null);
				template.enclosing(nestEnclosing);
			}
		}		
		
		TypeVariable<?>[] parameters = clazz.getTypeParameters();
		if(parameters != null){
			for(TypeVariable<?> each : parameters){
				Template param = createByTypeVariable(template, each, TemplateType.itsetfTypeParameters);
				template.addTypeParameter(param);
			}
		}
		if(!template.isEmbedClass()){
			List<Template> fields = createFieldTypeAll(template);
			template.properties(fields);
		}
		return template;
	}
	
	/**
	 * Generate the {@link Template} from the property that has enclosing of class.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param enclosing {@link Template} that enclosing the properties.
	 * @return {@link Template}
	 */
	private List<Template> createFieldTypeAll(Template enclosing){
		Class<?> enclosingClass = enclosing.template();
		List<Template> templates = new ArrayList<Template>();
		
		Class<?> now = enclosingClass;
		Class<?> before = null;
		
		while(now != null && !now.equals(Object.class)){
			Map<String,Template> classTypeParameters = getClassTypeParameters(now, before);
			enclosing.addRealParameterizedTypes(enclosingClass, classTypeParameters);

			List<Field> allFields = Arrays.asList(now.getDeclaredFields());
			
			if(allFields.size() > 0){
				for(Field each : allFields){
					//ignore JVM synthesize methods and field.
					if(each.isSynthetic())
						continue;
					Class<?> fieldClass = each.getType();
					if(!each.getDeclaringClass().equals(now))
						continue;
					
					ClassTemplate fieldClassType = !enclosing.isEnum() ?
						createByClassType(fieldClass, enclosing) : 
						(ClassTemplate)enclosing
					;
					FieldTemplate fieldTemplate = new FieldTemplate(each, fieldClassType);
					fieldTemplate.enclosing(enclosing);
					
					if(fieldTemplate.isArray()){
						Class<?> componentType = fieldClass.getComponentType();
						Integer dimensions = 1;
						while(componentType.isArray()){
							componentType = componentType.getComponentType();
							dimensions++;
						}
						
						Template typeParameter = createByBaseType(
							componentType,
							fieldTemplate,
							TemplateType.propertyTypeParameters
						);
						fieldTemplate.dimensions(dimensions);
						
						if(typeParameter.hasTypeParameters() && typeParameter.hasTypeVariableParameter()){
							Type type = each.getGenericType();
							Template typeTemplaterGenericParameter = createByBaseType(
								type, 
								typeParameter, 
								TemplateType.propertyTypeParameters
							);
							typeParameter.real(typeTemplaterGenericParameter);
						}
						fieldTemplate.addTypeParameter(typeParameter);
						templates.add(fieldTemplate);
					}
					else{
						TypeVariable<?>[] fieldClassTypeVariables = fieldClass.getTypeParameters();
						if(fieldClassTypeVariables != null){
							for(TypeVariable<?> fieldTypeVariable : fieldClassTypeVariables){
								Template fieldTypeVariableTemplate = createByTypeVariable(
									fieldTemplate, 
									fieldTypeVariable, 
									TemplateType.propertyTypeParameters
								);
								fieldTemplate.addTypeParameter(fieldTypeVariableTemplate);
							}
						}
						
						Type type = each.getGenericType();
						Template typeTemplater = createByBaseType(
							type, 
							fieldTemplate, 
							TemplateType.propertyTypeParameters
						);
						if(typeTemplater instanceof TypeParameterTemplate){
							TypeParameterTemplate parameterTemplate = (TypeParameterTemplate)typeTemplater;
							if(parameterTemplate.isTypeVariableParameter()){
								Template real = classTypeParameters.get(parameterTemplate.templateName());
								if(real != null){
									parameterTemplate.real(real);
									fieldTemplate.real(parameterTemplate);
								}
							}
						}
						else if(typeTemplater instanceof ClassTemplate)
							fieldTemplate.real(typeTemplater);
						templates.add(fieldTemplate);
					}
					//end for
				}
			}
			before = now;
			now = now.getSuperclass();
		}
		
		return templates;
	}
	
	/**
	 * Generate the {@link Template} from the {@link TypeVariable}
	 * @param enclosing {@link Class} that enclosing the type parameter
	 * @param parameterType type parameter
	 * @param templateType what type parameterization
	 * @return {@link Template}
	 */
	private TypeParameterTemplate createByTypeVariable(Template enclosing, TypeVariable<?> parameterType, TemplateType templateType){
		TypeParameterTemplate param = 
			new TypeParameterTemplate(templateType, parameterType, enclosing);
		return param;
	}
	
	/**
	 * Generate the {@link Template} from the parameterized type.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param enclosing {@link Class} that enclosing the type parameter 
	 * @param parameterType type parameter
	 * @param templateType what type parameterization
	 * @return {@link Template}
	 */
	private Template createByParameterizedType(Template enclosing, ParameterizedType parameterType, TemplateType templateType){
		Type type = parameterType.getRawType();
		Template typeTemplate = createByBaseType(type, enclosing,templateType);
		Type[] actuals = parameterType.getActualTypeArguments();
		
		if(templateType == TemplateType.itsetfTypeParameters || 
		   templateType == TemplateType.propertyTypeParameters || 
		   templateType == TemplateType.methodTypeParameter)
			typeTemplate.clearTypeParameters();
		
		if(actuals != null){
			for(Type each : actuals){
				Template eachTemplate = createByBaseType(each, enclosing, templateType);
				typeTemplate.addTypeParameter(eachTemplate);
			}
		}
		return typeTemplate;
		
	}
	
	/**
	 * Geneate the {@link Template} from the type parameter.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param enclosing {@link Class} that enclosing the type parameter
	 * @param clazz type parameter
	 * @param templateType what type parameterization
	 * @return {@link Template}
	 */
	public Template createByClassType(Template enclosing, Class<?> clazz, TemplateType templateType){
		ClassTemplate template = new ClassTemplate(clazz);
		template.enclosing(enclosing);
		
		TypeVariable<?>[] parameters = clazz.getTypeParameters();
		if(parameters != null){
			for(TypeVariable<?> each : parameters){
				Template param = createByTypeVariable(template, each, templateType);
				template.addTypeParameter(param);
			}
		}
		if(!template.isEmbedClass() && !template.isEnum()){
			List<Template> fields = createFieldTypeAll(template);
			template.properties(fields);
		}
		return template;
	}
	
	/**
	 * Geneate the {@link Template} from the generic array type parameter.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param enclosing {@link Class} that enclosing the type parameter
	 * @param type type parameter
	 * @param templateType what type parameterization
	 * @return {@link Template}
	 */
	public Template createByGenericArrayType(Template enclosing, GenericArrayType type, TemplateType templateType){
		return createByBaseType(type.getGenericComponentType(), enclosing, templateType);
	}
	
	/**
	 * Generate the {@link Template} from the {@link Type}
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param type {@link Type}
	 * @param enclosing {@link Class} that enclosing the type parameter
	 * @param templateType what type parameterization
	 * @return {@link Template}
	 */
	private Template createByBaseType(Type type, Template enclosing, TemplateType templateType){
		if(type instanceof Class<?>)
			return createByClassType(
				enclosing, 
				(Class<?>)type, 
				TemplateType.propertyTypeParameters
			);
		
		else if(type instanceof ParameterizedType)
			return createByParameterizedType(
				enclosing, 
				(ParameterizedType)type, 
				templateType
			);
		
		else if(type instanceof TypeVariable<?>)
			return createByTypeVariable(
				enclosing, 
				(TypeVariable<?>)type, 
				TemplateType.propertyTypeParameters
			);
		else if(type instanceof GenericArrayType)
			return createByGenericArrayType(
				enclosing,
				(GenericArrayType)type,
				templateType
			);
		return null;
	}	
	
	/**
	 * Get a {@link Map} of generics name and type parameters of the class definition.
	 * @since 2015/01/18
 	 * @version 1.0.0
	 * @param now superclass
	 * @param before baseclass
	 * @return {@link Template}
	 */
	private Map<String,Template> getClassTypeParameters(Class<?> now, Class<?> before){
		Map<String,Template> classTypeParameters = new HashMap<String,Template>();
		
		if(before != null){
			Type superGenericsTypeParameter = before.getGenericSuperclass();
			
			if(superGenericsTypeParameter != null){
				TypeVariable<?>[] superGenericsTypeDifinition = now.getTypeParameters();
				if(superGenericsTypeDifinition != null){

					int index = 0;
					
					for(TypeVariable<?> each : superGenericsTypeDifinition){
						Template superClassDifinition = createByClassType(now, null);

						Template superClassTypeParameterDifinisionTemplate = createByBaseType(
							each, 
							superClassDifinition, 
							TemplateType.itsetfTypeParameters
						);

						Template superGenericsTypeTemplate = createByBaseType(
							superGenericsTypeParameter, null, 
							TemplateType.itsetfTypeParameters
						);
						
						Template pairClassTypeParameterParameterizedTypeTemplate = 
							superGenericsTypeTemplate.typeParameterAt(index++);
						
						classTypeParameters.put(
							superClassTypeParameterDifinisionTemplate.templateName(), 
							pairClassTypeParameterParameterizedTypeTemplate
						);
					}
				}
			}
		}
		return classTypeParameters;
	}
}
