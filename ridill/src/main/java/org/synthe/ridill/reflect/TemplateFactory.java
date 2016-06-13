package org.synthe.ridill.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationHandler;
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
import java.util.concurrent.atomic.AtomicInteger;
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
				if(typeParameterTemplate.isTypeVariableParameter()){
					
					Map<String,Template> store = getClassTypeParameters(instance, method);
					real = (ClassTemplate)store.get(typeParameterTemplate.typeVariable().getTypeName());
					if(real == null)
						real = typeParameterTemplate;
					MethodTemplate template = new MethodTemplate(method, real);
					return build(template);
				}
				
				MethodTemplate template = new MethodTemplate(method, typeParameterTemplate);
				return build(template);
			}
		}
		
		//return type is class. however, return class needs type parameter. 
		else if(typeParameter != null && typeParameter instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType)typeParameter;
			Boolean hasTypeVariable = false;
			for(Type type : parameterizedType.getActualTypeArguments()){
				if(type instanceof TypeVariable<?>)
					hasTypeVariable = true;
			}
			
			ClassTemplate real = (ClassTemplate) createByClassType(returnType, enclosingTemplate);
			MethodTemplate template = new MethodTemplate(method, real);

			if(hasTypeVariable){
				Map<String, Template> parameterizedTypes = getClassTypeParameters(instance, method);
				template.parameterizedTypes(parameterizedTypes);
				if(template.typeParameters() == null)
					template.typeParameters(new ArrayList<>());
				if(parameterizedTypes != null)
					template.typeParameters().addAll(parameterizedTypes.values());
			}
			return build(template);
		}
		
		//not need type parameter
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

		if(!template.isEmbedClass() && !template.isEnum()){
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
				replaceClassTypeParameters(classTypeParametersAll, classTypeParameters, inheritanceStructure);
			}
			classTypeParametersAll.put(now, classTypeParameters);

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
					fieldTemplate.parameterizedTypes(classTypeParameters);

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
						Boolean isTypeVariableProperty = fieldClass.equals(Object.class) && each.getGenericType() instanceof TypeVariable<?>;
						if((!fieldTemplate.isEmbedClass() && !fieldTemplate.isEnum()) || isTypeVariableProperty){
							Type type = each.getGenericType();
							
							Template typeTemplate = createByBaseType(
								type,
								fieldTemplate,
								TemplateType.propertyTypeParameters
							);
							if(isTypeVariableProperty){
								fieldTemplate.real(typeTemplate);
								fieldTemplate.addTypeParameter(typeTemplate);
								fieldTemplate.parameterizedTypes(classTypeParameters);
							}
							else if(type instanceof ParameterizedType){
								if(fieldTemplate.isDomainClass()){
									TypeVariable<?>[] tv = fieldTemplate.template().getTypeParameters();
									Map<String, Template> parameterizedTypes = new HashMap<>();
									for(int i = 0; i < tv.length; i++){
										parameterizedTypes.put(tv[i].getTypeName(), typeTemplate.typeParameterAt(i));
									}
									fieldTemplate.real(typeTemplate);
									parameterizedTypes.putAll(classTypeParameters);
									fieldTemplate.parameterizedTypes(parameterizedTypes);
									
								}
								else{
									fieldTemplate.real(typeTemplate);
									fieldTemplate.parameterizedTypes(classTypeParameters);
								}
							}
							else if(type instanceof TypeVariable<?>){
								fieldTemplate.addTypeParameter(typeTemplate);
								fieldTemplate.parameterizedTypes(classTypeParameters);
							}
							else if(type instanceof Class<?>){
								fieldTemplate.real(typeTemplate);
								fieldTemplate.parameterizedTypes(classTypeParameters);
							}
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
	 * @return {@link Map} key : type name(T, K, V, E...etc), value : {@link Template}
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
	
	/**
	 * Get a {@link Map} of generics name and type parameters of the class definition.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param enclosing enclosing of method
	 * @param method target method
	 * @return {@link Map} key : type name(T, K, V, E...etc), value : {@link Template}
	 */
	private Map<String,Template> getClassTypeParameters(Class<?> enclosing, Method method){
		Class<?> now = enclosing;
		Class<?> before = null;
		
		List<Class<?>> inheritanceStructure = new ArrayList<>();
		Map<Class<?>, Map<String,Template>> classTypeParametersAll =
			new HashMap<>();
		
		while (now != null && !now.equals(Object.class)) {
			Map<String, Template> classTypeParameters = getInterfaceTypeParameters(now, before);
			classTypeParametersAll.put(now, classTypeParameters);
			//if contains typevariable, replace super(super->super...)'s parameterized type.
			if(containTypeVariable(classTypeParameters)){
				replaceClassTypeParameters(classTypeParametersAll, classTypeParameters, inheritanceStructure);
			}
			classTypeParametersAll.put(now, classTypeParameters);
			
			if(method.getDeclaringClass().equals(now)){
				return classTypeParameters;
			}
			inheritanceStructure.add(now);
			before = now;
			now = before.getInterfaces().length > 0 ? now.getInterfaces()[0] : null;
		}
		
		return null;
	}
	
	/**
	 * Get a {@link Map} of generics name and type parameters of the class definition.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param now superclass
	 * @param before baseclass
	 * @return {@link Map} key : type name(T, K, V, E...etc), value : {@link Template}
	 */
	private Map<String, Template> getInterfaceTypeParameters(Class<?> now, Class<?> before){
		Map<String, Template> classTypeParameters = new HashMap<String, Template>();

		if(before != null){
			Type[] superGenericsTypeParameters = before.getGenericInterfaces();
			Type superGenericsTypeParameter = superGenericsTypeParameters != null && superGenericsTypeParameters.length > 0 ? superGenericsTypeParameters[0] : null;
			
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

	/**
	 * Get a {@link Map} of generics name and type parameters of the class definition.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param instance object
	 * @param method method
	 * @return {@link Map} key : type name(T, K, V, E...etc), value : {@link Template}
	 */
	private Map<String,Template> getClassTypeParameters(Object instance, Method method){
		Map<String,Template> parameterizedTypes = new HashMap<>();
		
		if(instance instanceof Proxy){
			Proxy p = (Proxy)instance;
			InvocationHandler handler = Proxy.getInvocationHandler(p);

			if(handler instanceof ProxyTemplate){
				ProxyTemplate proxyTemplate = (ProxyTemplate)handler;
				Class<?> defineClass = proxyTemplate.findDefineByMethod(method);
				
				return getClassTypeParameters(defineClass, method);
			}
		}
		return parameterizedTypes;
	}
	
	/**
	 * Replace TypeVariable to ParameterizedType.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param classTypeParametersAll a {@link Map} of generics name and type parameters of the class definition.
	 * @param classTypeParameters a {@link Map} of generics name and type parameters of the class definition.
	 * @param inheritanceStructure inheritance structures.
	 */
	private void replaceClassTypeParameters(Map<Class<?>,Map<String,Template>> classTypeParametersAll, Map<String,Template> classTypeParameters, List<Class<?>> inheritanceStructure){
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
	/**
	 * Change {@link Template} in ready state.
	 * @param target {@link Template}
	 * @return {@link Template target}
	 */
	private Template build(ClassTemplate target){
		reflectClassTypeParameters(target);
		return target;
	}
	
	/**
	 * properties replace TypeVariabele to ParameterizedType.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param target {@link Template}
	 */
	private void reflectClassTypeParameters(ClassTemplate target){
		if(!target.hasProperty())
			return;
		for(Template t : target.properties()){
			reflectTypeVariables(t);
			if(t.template() == null && t.hasTypeParameters()){
				if(!t.hasParameterizedType()){
					Template enclosingField = null;
					Template self = target;
					while(self != null){
						if(self instanceof FieldTemplate){
							enclosingField = self;
							if(enclosingField.hasParameterizedType())
								break;
						}
						self = self.enclosing();
					}
					if(enclosingField != null){
						t.parameterizedTypes(enclosingField.parameterizedTypes());
					}
				}
				t.real(t.typeParameterAt(0));
				t.clearTypeParameters();
			}
			if(t instanceof ClassTemplate && ((ClassTemplate) t).hasProperty())
				reflectClassTypeParameters((ClassTemplate)t);
		}
	}
	
	private void reflectTypeVariables(Template target){
		if(target.hasTypeParameters() && target.hasTypeVariableParameter()){
			AtomicInteger index = new AtomicInteger(0);
			TypeVariable<?>[] typeVariableDefinitions = target.template() != null ? target.template().getTypeParameters() : null;
			Map<String,Template> realParameterizedTypes = new HashMap<>();
			if(target.hasParameterizedType())
				realParameterizedTypes.putAll(target.parameterizedTypes());
			target.typeParameters().forEach(t -> {
				if(!target.hasTypeParameters()){
					reflectTypeVariables(t);
					return;
				}
				if(t instanceof ClassTemplate && t.hasTypeVariableParameter()){
					reflectTypeVariables(t);
				}
				
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
				
				if(real != null){
					t.real(real);
					if(typeVariableDefinitions != null){
						if(typeVariableDefinitions.length > index.get()){
							realParameterizedTypes.put(typeVariableDefinitions[index.get()].getTypeName(), real);
							index.incrementAndGet();
						}
					}
				}
				
			});
			target.parameterizedTypes(realParameterizedTypes);
		}
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
