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
import java.util.function.Function;

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
	public Template createByReturnType(Method method, Object instance,Object... args){
		Class<?> returnType = method.getReturnType();
		Type typeParameter = method.getGenericReturnType();

		Class<?> enclosing = instance != null && !(instance instanceof Proxy) ? 
				instance.getClass() : 
				method.getDeclaringClass();

		Template enclosingTemplate = createByClassType(enclosing, null);

		// return type is type parameter of class
		if(typeParameter != null && typeParameter instanceof TypeVariable<?>){
			TypeParameterTemplate typeParameterTemplate = (TypeParameterTemplate) createByTypeVariable(
				enclosingTemplate, 
				(TypeVariable<?>) typeParameter,
				TemplateType.methodTypeParameter
			);
			
			ClassTemplate real = (ClassTemplate) enclosingTemplate
					.findEnclosingParameterizedTypeByTypeVariable(typeParameterTemplate);
			if (real != null){
				MethodTemplate template = new MethodTemplate(method, real);
				return build(template);
			} 
			else{
				MethodTemplate template = new MethodTemplate(method, typeParameterTemplate);
				return build(template);
			}
		}
		else{
			ClassTemplate real = (ClassTemplate) createByClassType(returnType, enclosingTemplate);
			MethodTemplate template = new MethodTemplate(method, real);
			return build(template);
		}
	}

	/**
	 * Generate the {@link Template} from the {@link Class}
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param clazz target
	 * @return {@link Template}
	 */
	private ClassTemplate createByClassType(Class<?> clazz, Template enclosing){
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
			if (enclosing != null)
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
				Template param = createByTypeVariable(
					template, 
					each,
					TemplateType.itsetfTypeParameters
				);
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
	 * Generate the {@link Template} from the {@link Class}
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param clazz target
	 * @return {@link Template}
	 */
	private Template createArrayTypeParameter(Class<?> clazz, Template enclosing, Type genericType){
		Class<?> componentType = clazz.getComponentType();
		Integer dimensions = 1;
		while(componentType.isArray()){
			componentType = componentType.getComponentType();
			dimensions++;
		}

		Boolean isTypeVariable = false;
		TypeVariable<?> foundTypeVariable = null;
		if(componentType.equals(Object.class) && genericType instanceof GenericArrayType){
			GenericArrayType gType = (GenericArrayType)genericType;
			while(gType != null && gType instanceof GenericArrayType){
				if(gType.getGenericComponentType() instanceof TypeVariable<?>){
					isTypeVariable = true;
					foundTypeVariable = (TypeVariable<?>)gType.getGenericComponentType();
					break;
				}
				if(gType.getGenericComponentType() instanceof GenericArrayType)
					gType = (GenericArrayType)gType.getGenericComponentType();
			}
		}
		
		Template typeParameter = !isTypeVariable ? 
			createByBaseType(
				componentType, 
				enclosing,
				TemplateType.propertyTypeParameters
			):
			createByTypeVariable(
				enclosing, 
				foundTypeVariable, 
				TemplateType.propertyTypeParameters
			);
		typeParameter.dimensions(dimensions);

		if(typeParameter.hasTypeParameters() && typeParameter.hasTypeVariableParameter()){
			Template typeTemplateGenericParameter = createByBaseType(
				genericType, 
				typeParameter,
				TemplateType.propertyTypeParameters
			);
			typeParameter.real(typeTemplateGenericParameter);
		}
		typeParameter.enclosing(enclosing);
		return typeParameter;
	}

	/**
	 * Generate the {@link Template} from the property that has enclosing of class.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param enclosing
	 *            {@link Template} that enclosing the properties.
	 * @return {@link Template}
	 */
	private List<Template> createFieldTypeAll(Template enclosing){
		Class<?> enclosingClass = enclosing.template();
		List<Template> templates = new ArrayList<Template>();

		Class<?> now = enclosingClass;
		Class<?> before = null;
		
		List<Class<?>> inheritanceStructure = new ArrayList<>();
		Map<Class<?>, Map<String,Template>> classTypeParametersAll =
			new HashMap<>();
		
		while (now != null && !now.equals(Object.class)) {
			
			Map<String, Template> classTypeParameters = getClassTypeParameters(now, before);
			classTypeParametersAll.put(now, classTypeParameters);
			//if contains typevariable, replace super(super->super...)'s parameterized type.
			if(containTypeVariable(classTypeParameters)){
				Map<String, Template> classTypeParametersNow = findClassTypeParameters(classTypeParameters);
				for(Map.Entry<String, Template> each : classTypeParametersNow.entrySet()){
					for(int i = inheritanceStructure.size() - 1; i >= 0; i--){
						Class<?> parentClass = inheritanceStructure.get(i);
						if(parentClass == null)
							break;
						Map<String, Template> classTypeParametersSuper = classTypeParametersAll.get(parentClass);
						Template target = classTypeParametersSuper.get(each.getKey());
						if(target != null){
							if(target instanceof TypeParameterTemplate)
								continue;
							classTypeParameters.put(each.getKey(), target);
							break;
						}
						else
							break;
					}
					
				}
			}
			classTypeParametersAll.put(now, classTypeParameters);
			enclosing.addRealParameterizedTypes(enclosingClass,classTypeParameters);

			List<Field> allFields = Arrays.asList(now.getDeclaredFields());

			if (allFields.size() > 0) {
				for (Field each : allFields) {
					// ignore JVM synthesize methods and fields.
					if (each.isSynthetic())
						continue;
					Class<?> fieldClass = each.getType();
					if (!each.getDeclaringClass().equals(now))
						continue;


					ClassTemplate fieldClassType = !enclosing.isEnum() ? 
						createByClassType(
							fieldClass, 
							enclosing
						) : 
						(ClassTemplate) enclosing
					;
							
					FieldTemplate fieldTemplate = new FieldTemplate(
						each,
						fieldClassType
					);
					fieldTemplate.enclosing(enclosing);

					if(fieldTemplate.isArray()){
						Template typeParameter = createArrayTypeParameter(
							fieldClass, 
							fieldTemplate,
							each.getGenericType()
						);
						fieldTemplate.dimensions(typeParameter.dimensions());
						if(typeParameter.classType() == ClassType.typeVariable){
							Template realTypeParameter = 
								classTypeParameters.get(typeParameter.templateName());
							if(realTypeParameter != null)
								typeParameter = realTypeParameter;
						}
						fieldTemplate.addTypeParameter(typeParameter);
						templates.add(fieldTemplate);
					}
					else{
						TypeVariable<?>[] fieldClassTypeVariables = fieldClass.getTypeParameters();
						if( fieldClassTypeVariables != null && 
							fieldClassTypeVariables.length > 0 && 
							!fieldTemplate.hasTypeParameters()
						){
							for(TypeVariable<?> fieldTypeVariable : fieldClassTypeVariables){
								Template fieldTypeVariableTemplate = createByTypeVariable(
									fieldTemplate, 
									fieldTypeVariable,
									TemplateType.propertyTypeParameters
								);
								
								fieldTemplate.addTypeParameter(fieldTypeVariableTemplate);
							}
						}
						else{
							Type type = each.getGenericType();
							Template typeTemplate = createByBaseType(
								type,
								fieldTemplate,
								TemplateType.propertyTypeParameters
							);
//							fieldTemplate.typeParameters(new ArrayList<>());
							
							if(type instanceof ParameterizedType){
								fieldTemplate.real(typeTemplate);
							}
							else
								fieldTemplate.addTypeParameter(typeTemplate);
						}
						templates.add(fieldTemplate);
					}
					// end for
				}
			}
			inheritanceStructure.add(now);
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
		TypeParameterTemplate param = new TypeParameterTemplate(
			templateType,
			parameterType, 
			enclosing
		);
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
		Template typeTemplate = createByBaseType(type, enclosing, templateType);
		Type[] actuals = parameterType.getActualTypeArguments();

		if( templateType == TemplateType.itsetfTypeParameters || 
			templateType == TemplateType.propertyTypeParameters || 
			templateType == TemplateType.methodTypeParameter
		)
			typeTemplate.clearTypeParameters();

		if(actuals != null){
			for(Type each : actuals){
				Template eachTemplate = createByBaseType(
					each, 
					enclosing,
					templateType
				);
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
	private Template createByClassType(Template enclosing, Class<?> clazz, TemplateType templateType){
		ClassTemplate template = new ClassTemplate(clazz);
		template.enclosing(enclosing);

		TypeVariable<?>[] parameters = clazz.getTypeParameters();
		if(parameters != null){
			for (TypeVariable<?> each : parameters) {
				Template param = createByTypeVariable(
					template, 
					each,
					templateType
				);
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
	private Template createByGenericArrayType(Template enclosing, GenericArrayType type, TemplateType templateType){
		return createByBaseType(
			type.getGenericComponentType(), 
			enclosing,
			templateType
		);
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
		if (type instanceof Class<?>) {
			Class<?> clazz = (Class<?>) type;
			if(clazz.isArray()){
				ClassTemplate arrayTemplate = new ClassTemplate(clazz);
				Template typeParameter = createArrayTypeParameter(
					clazz,
					arrayTemplate, 
					clazz.getGenericSuperclass()
				);
				arrayTemplate.dimensions(typeParameter.dimensions());
				arrayTemplate.addTypeParameter(typeParameter);
				arrayTemplate.enclosing(enclosing);
				return arrayTemplate;
			}

			return createByClassType(
				enclosing, 
				(Class<?>)type,
				TemplateType.propertyTypeParameters
			);
		}
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
	private Map<String, Template> getClassTypeParameters(Class<?> now, Class<?> before){
		Map<String, Template> classTypeParameters = new HashMap<String, Template>();

		if(before != null){
			Type superGenericsTypeParameter = before.getGenericSuperclass();

			if (superGenericsTypeParameter != null) {
				TypeVariable<?>[] superGenericsTypeDifinition = now.getTypeParameters();
				if (superGenericsTypeDifinition != null) {

					int index = 0;
					for(TypeVariable<?> each : superGenericsTypeDifinition){
						Template superClassDifinition = createByClassType(
							now,
							null
						);

						Template superClassTypeParameterDifinisionTemplate = createByBaseType(
							each, 
							superClassDifinition,
							TemplateType.itsetfTypeParameters
						);

						Template superGenericsTypeTemplate = createByBaseType(
							superGenericsTypeParameter, 
							null,
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
	
	private Template build(ClassTemplate target){
		reflectClassTypeParameters(target, target);
		return target;
	}
	
	private void reflectClassTypeParameters(ClassTemplate target, Template rootEnclosing){
		if(!target.hasProperty())
			return;
		target.properties().forEach(t -> {
			reflectTypeVariables(t, rootEnclosing);
			if(t.isObject() && t.hasTypeParameters())
				t.real(t.typeParameterAt(0));
		});
	}
	
	private void reflectTypeVariables(Template target, Template rootEnclosing){
		if(target.hasTypeParameters() && target.hasTypeVariableParameter()){
			target.typeParameters().forEach(t -> {
				if(!target.hasTypeParameters()){
					reflectTypeVariables(t, rootEnclosing);
					return;
				}
				
				if(t instanceof TypeParameterTemplate){
					TypeVariable<?> tv = ((TypeParameterTemplate)t).typeVariable();
					if(tv != null){
						Function<Template,Template> resolver = p -> {
							Template enclosing = p;
							Template real = null;
							while(enclosing != null){
								real = enclosing.findEnclosingParameterizedTypeByTypeVariable(t);
								if(real != null)
									break;
								enclosing = enclosing.enclosing();
							}
							return real;
						};
						
						Template real = resolver.apply(t);
						if(real == null)
							real = resolver.apply(target);
						if(real == null)
							real = resolver.apply(rootEnclosing);
						
						if(real != null)
							t.real(real);
					}
				}
			});
		}
		if(target instanceof ClassTemplate && ((ClassTemplate) target).hasProperty())
			reflectClassTypeParameters((ClassTemplate)target, rootEnclosing);
	}
	

	private Boolean containTypeVariable(Map<String, Template> store){
		for(Map.Entry<String, Template> each : store.entrySet()){
			if(each.getValue().classType() == ClassType.typeVariable)
				return true;
		}
		return false;
	}
	private Map<String, Template> findClassTypeParameters(Map<String,Template> store){
		Map<String,Template> result = new HashMap<>();
		for(Map.Entry<String, Template> each : store.entrySet()){
			if(each.getValue().classType() == ClassType.typeVariable)
				result.put(each.getKey(), each.getValue());
		}
		return result;
	}
}
