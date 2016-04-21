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

import org.junit.Test;

public class ReflectionInfoFactoryTest {
	@Test
	public void clsssTypeTest() throws Exception{
//		class SimpleInMethodClassTest{
//		}
//		SimpleInterfaceTest implementation = new SimpleInterfaceTest(){
//		};
//		printClassInfo(SimpleInMethodClassTest.class);
//		printClassInfo(SimpleClassTest.class);
//		printClassInfo(SimpleClassInnerTest.class);
//		printClassInfo(SimpleClassStaticInnerTest.class);
//		printClassInfo(implementation.getClass());
//		class GenericInMethodClassTest<V>{
//			V v;
//		}
//		//取れない？
//		GenericInMethodClassTest<String> genericInMethodClassTest = new GenericInMethodClassTest<>();
//		printClassInfo(genericInMethodClassTest.getClass());
//		//取れない
//		GnericsClassTest<String> genericsClassTest = new GnericsClassTest<>();
//		printClassInfo(genericsClassTest.getClass());
//
//		GenericInnerClassTest<HashMap<String,Object>,Integer> genericInnerClassTest = new GenericInnerClassTest<>();
//		printClassInfo(genericInnerClassTest.getClass());
//		
		ExtGenericInnerClassTest extGenericInnerClassTest = new ExtGenericInnerClassTest();
		printClassInfo(extGenericInnerClassTest.getClass());
		
		Template t = TemplateFactory.createByClassType(ExtGenericInnerClassTest.class);
		System.out.println("end");
	}
	
	private void printClassInfo(Class<?> clazz){
		Class<?> now = clazz;
		Class<?> before = null;
		System.out.println("----------------------------------------------");
		while(now != null){
			System.out.println(className(now));
			TypeVariable<?>[] parameters = now.getTypeParameters();
			outTypeVariables(parameters);
			if(before!=null)
				outType(before.getGenericSuperclass());
//			System.out.println("\tisLocalClass : "+now.isLocalClass());
//			System.out.println("\tisAnonymous : "+now.isAnonymousClass());
//			System.out.println("\tisMemberClass : "+now.isMemberClass());
//			System.out.println(" ");
//			Class<?> enclosing = now.getEnclosingClass();
//			Class<?> declaring = now.getDeclaringClass();
//			Class<?>[] declareds = now.getDeclaredClasses();
//			System.out.println("\tenclosing class : "+(enclosing==null?"null":className(enclosing)));
//			System.out.println("\tdeclaring class : "+(declaring==null?"null":className(declaring)));
//			if(declareds!=null){
//				int i = 0;
//				for(Class<?> each : declareds){
//					System.out.println("\tdeclared class("+i+++") : "+(each==null?"null":each.getName()));
//				}
//			}
//			System.out.println(" ");
			
			
			List<Field> fields = new ArrayList<>();
			List<Field> privateFields = Arrays.asList(now.getDeclaredFields());
			if(privateFields != null)
				fields.addAll(privateFields);
			List<Field> publicFields = Arrays.asList(now.getFields());
			if(publicFields != null)
				fields.addAll(publicFields);
			
			for(Field each : fields){
				if(each.isSynthetic())
					continue;
				Class<?> fieldClass = each.getType();
				System.out.println("\t"+each.getName()+" : "+className(fieldClass));
				Type genericsType = each.getGenericType();
				outType(genericsType);
//				outType(fieldClass.getGenericInterfaces());
				
				outTypeVariables(fieldClass.getTypeParameters());
			}
			
			List<Method> methods = new ArrayList<>();
			List<Method> privateMethods = Arrays.asList(now.getDeclaredMethods());
			if(privateMethods != null)
				methods.addAll(privateMethods);
			List<Method> publicMethods = Arrays.asList(now.getMethods());
			if(publicMethods != null)
				methods.addAll(publicMethods);
			
			before = now;
			now = now.getSuperclass();
		}
		
		System.out.println(" ");
	}
	private String className(Class<?> clazz){
		return clazz.getSimpleName();
	}
	
	private void outType(Type...parameters){
		if(parameters != null){
			int i = 0;
			for(Type parameter : parameters){
				if(parameter instanceof ParameterizedType){
					ParameterizedType p = (ParameterizedType)parameter;
					outType(p.getRawType());
					
					outType(p.getActualTypeArguments());
				}
				else if(parameter instanceof Class<?>){
					Class<?> c = (Class<?>)parameter;
					System.out.println("\t"+className(c));
				}
				else{
					System.out.println("\ttype("+i+++") : "+parameter.getTypeName());
				}
			}
			System.out.println(" ");
		}
	}
	
	private void outTypeVariables(TypeVariable<?>[] parameters){
		if(parameters != null){
			int i = 0;
			for(TypeVariable<?> parameter : parameters){
				System.out.println("\tgenerics parameter("+i+++") : "+parameter.getName());
			}
			System.out.println(" ");
		}
	}

	
	class InnerClassTest{
		private String f;
		class InnerInnerClassTest{
			private String g;
		}
	}
	
	class GenericInnerClassTest<K extends Map<String,Object>, V>{
		private K a;
		private V b;
		private Long c;
		private List<Integer> d;
		private InnerClassTest e;
	}
	
	class ExtGenericInnerClassTest extends GenericInnerClassTest<HashMap<String,Object>, String>{
	}
}
