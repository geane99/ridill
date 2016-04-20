package org.synthe.ridill.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TemplateFactory {
	public static ReflectionInfo returnType(Object proxy, Method method, Object[] params){
		Class<?> returnType = method.getReturnType();
		
		return null;
	}
	
	public static ReflectionInfo fieldType(ReflectionInfo info, Field field){
		return null;
	}
	
	
	public static List<ReflectionInfo> fieldTypeAll(ReflectionInfo owner){
		List<ReflectionInfo> r = new ArrayList<>();
		
		return null;
	}
	
	public static ReflectionInfo fieldTypeParameterType(ReflectionInfo info, Integer genericsIndex){
		return null;
	}
	
	
	
	public static Template createByClassType(Class<?> clazz){
		ClassTemplate template = new ClassTemplate(clazz);
		if(template.isLocalClass()){
			Class<?> enclosingClass = clazz.getEnclosingClass();
			Template nestEnclosing = createByClassType(enclosingClass);
			template.enclosing(nestEnclosing);
		}
		if(template.isMemberClass()){
			Class<?> enclosingClass = clazz.getDeclaringClass();
			Template nestEnclosing = createByClassType(enclosingClass);
			template.enclosing(nestEnclosing);
		}
		if(template.isAnonymousClass()){
			Class<?> enclosingClass = clazz.getEnclosingClass();
			Template nestEnclosing = createByClassType(enclosingClass);
			template.enclosing(nestEnclosing);
		}		
		
		TypeVariable<?>[] parameters = clazz.getTypeParameters();
		if(parameters != null){
			for(TypeVariable<?> each : parameters){
				Template param = createByTypeVariableType(template, each, TemplateType.itsetfTypeParameters);
				template.addTypeParameter(param);
			}
		}
		if(!template.isEmbedClass()){
			List<Template> fields = createFieldTypeAll(template);
			template.properties(fields);
		}
		return template;
	}
	
	public static Template createByTypeVariableType(Template enclosing, TypeVariable<?> parameterType, TemplateType templateType){
		TypeParameterTemplate param = new TypeParameterTemplate(templateType, parameterType, enclosing);
		return param;
	}
	
	public static Template createByParameterizedType(Template enclosing, ParameterizedType parameterType, TemplateType templateType){
		Type type = parameterType.getRawType();
		Template typeTemplate = createByBaseType(type, enclosing,templateType);
		Type[] actuals = parameterType.getActualTypeArguments();
		if(templateType == TemplateType.itsetfTypeParameters || templateType == TemplateType.propertyTypeParameters || templateType == TemplateType.methodTypeParameter)
			typeTemplate.clearTypeParameters();
		if(actuals != null){
			for(Type each : actuals){
				Template eachTemplate = createByBaseType(each, enclosing, templateType);
				typeTemplate.addTypeParameter(eachTemplate);
			}
		}
		return typeTemplate;
		
	}
	
	public static Template createByClassType(Template enclosing, Class<?> clazz, TemplateType tempalteType){
		ClassTemplate template = new ClassTemplate(clazz);
		template.enclosing(enclosing);
		
		TypeVariable<?>[] parameters = clazz.getTypeParameters();
		if(parameters != null){
			for(TypeVariable<?> each : parameters){
				Template param = createByTypeVariableType(template, each, tempalteType);
				template.addTypeParameter(param);
			}
		}
		return template;
	}
	
	private static Map<String,Template> getClassTypeParameters(Class<?> now, Class<?> before){
		Map<String,Template> classTypeParameters = new HashMap<String,Template>();
		if(before != null){
			Type superGenericsTypeParameter = before.getGenericSuperclass();
			if(superGenericsTypeParameter != null){
				Template superGenericsTypeTemplate = createByBaseType(superGenericsTypeParameter, null, TemplateType.itsetfTypeParameters);
				TypeVariable<?>[] superGenericsTypeDifinition = now.getTypeParameters();
				if(superGenericsTypeDifinition != null){
					Template superClassDifinition = createByClassType(now);
					int index = 0;
					for(TypeVariable<?> each : superGenericsTypeDifinition){
						Template superClassTypeParameterDifinisionTemplate = createByBaseType(each, superClassDifinition, TemplateType.itsetfTypeParameters);
						Template pairClassTypeParameterParameterizedTypeTemplate = superGenericsTypeTemplate.typeParameterAt(index++);
						classTypeParameters.put(superClassTypeParameterDifinisionTemplate.templateName(), pairClassTypeParameterParameterizedTypeTemplate);
					}
				}
			}
		}
		return classTypeParameters;
	}
	
	public static List<Template> createFieldTypeAll(Template enclosing){
		Class<?> enclosingClass = enclosing.template();
		List<Template> templates = new ArrayList<Template>();
		
		Class<?> now = enclosingClass;
		Class<?> before = null;
		
		while(now != null && !now.equals(Object.class)){
			Map<String,Template> classTypeParameters = getClassTypeParameters(now, before);
			enclosing.addRealParameterizedTypes(enclosingClass, classTypeParameters);
//			if(before != null){
//				Type superGenericsTypeParameter = before.getGenericSuperclass();
//				if(superGenericsTypeParameter != null){
//					Template superGenericsTypeTemplate = createByBaseType(superGenericsTypeParameter, null, TemplateType.itsetfTypeParameters);
//					TypeVariable<?>[] superGenericsTypeDifinition = now.getTypeParameters();
//					if(superGenericsTypeDifinition != null){
//						Template superClassDifinition = createByClassType(now);
//						int index = 0;
//						for(TypeVariable<?> each : superGenericsTypeDifinition){
//							Template superClassTypeParameterDifinisionTemplate = createByBaseType(each, superClassDifinition, TemplateType.itsetfTypeParameters);
//							Template pairClassTypeParameterParameterizedTypeTemplate = superGenericsTypeTemplate.typeParameterAt(index++);
//							classTypeParameters.put(superClassTypeParameterDifinisionTemplate.templateName(), pairClassTypeParameterParameterizedTypeTemplate);
//							enclosing.addParameterizedType(now, superClassTypeParameterDifinisionTemplate, pairClassTypeParameterParameterizedTypeTemplate);
//							
//						}
//					}
//				}
//			}
			
			List<Field> fields = new ArrayList<>();
			List<Field> privateFields = Arrays.asList(now.getDeclaredFields());
			if(privateFields != null)
				fields.addAll(privateFields);
			List<Field> publicFields = Arrays.asList(now.getFields());
			if(publicFields != null)
				fields.addAll(publicFields);
			
			if(fields.size() > 0){
				for(Field each : fields){
					if(each.isSynthetic())
						continue;
					Class<?> fieldClass = each.getType();
					FieldTemplate fieldTemplate = new FieldTemplate(each, fieldClass);
					fieldTemplate.enclosing(enclosing);
					
					TypeVariable<?>[] fieldClassTypeVariables = fieldClass.getTypeParameters();
					if(fieldClassTypeVariables != null){
						for(TypeVariable<?> fieldTypeVariable : fieldClassTypeVariables){
							Template fieldTypeVariableTemplate = createByTypeVariableType(fieldTemplate, fieldTypeVariable, TemplateType.propertyTypeParameters);
							fieldTemplate.addTypeParameter(fieldTypeVariableTemplate);
						}
					}
					
					Type type = each.getGenericType();
					Template typeTemplate = createByBaseType(type, fieldTemplate, TemplateType.propertyTypeParameters);
					if(typeTemplate instanceof TypeParameterTemplate){
						TypeParameterTemplate parameterTemplate = (TypeParameterTemplate)typeTemplate;
						if(parameterTemplate.isTypeVariableParameter()){
							Template real = classTypeParameters.get(parameterTemplate.templateName());
							if(real != null){
								parameterTemplate.real(real);
								fieldTemplate.real(parameterTemplate);
							}
						}
					}
					else if(typeTemplate instanceof ClassTemplate){
						fieldTemplate.real(typeTemplate);
					}
					
					if(!fieldTemplate.isEmbedClass()){
						Template fieldClassTemplate = createByClassType(fieldTemplate.template());
						fieldClassTemplate.enclosing(fieldTemplate);
						fieldTemplate.propertyTemplate(fieldClassTemplate);
					}
					templates.add(fieldTemplate);
				}
			}
			before = now;
			now = now.getSuperclass();
		}
		
		return templates;
	}
	
	private static Template createByBaseType(Type type, Template enclosing, TemplateType templateType){
		if(type instanceof Class<?>){
			return createByClassType(enclosing, (Class<?>)type, TemplateType.propertyTypeParameters);
		}
		else if(type instanceof ParameterizedType){
			return createByParameterizedType(enclosing, (ParameterizedType)type, templateType);
		}
		else if(type instanceof TypeVariable<?>){
			return createByTypeVariableType(enclosing, (TypeVariable<?>)type, TemplateType.propertyTypeParameters);
		}
		return null;
	}

}
