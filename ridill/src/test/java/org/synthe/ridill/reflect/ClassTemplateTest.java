package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.synthe.ridill.stub.TargetInfo;


public class ClassTemplateTest {
	class LocalClass{
		@SuppressWarnings("unused")
		private String localField1;
		public void localMethod1(){
		}
	}
	
	@Test
	public void type() throws Exception {
		assertThat(ClassTemplate.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		assertThat(target, notNullValue());
	}

	@Test
	public void toTargetInfo_A$Object() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Object instance = null;
		TargetInfo actual = target.toTargetInfo(instance);
		TargetInfo expected = null;
		assertThat(actual, is(equalTo(expected)));
	}
	@Test
	public void addProperty_A$() throws Exception{
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Field f = LocalClass.class.getDeclaredField("localField1");
		
		ClassTemplate ftarget = new ClassTemplate(f.getType());
		FieldTemplate ft = new FieldTemplate(f, ftarget);
		target.addProperty(ft);
	}

	@Test
	public void properties_A$() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Field f = LocalClass.class.getDeclaredField("localField1");
		ClassTemplate ftarget = new ClassTemplate(f.getType());
		FieldTemplate ft = new FieldTemplate(f, ftarget);
		target.addProperty(ft);
		List<Template> actual = target.properties();
		List<Template> expected = new ArrayList<>();
		expected.add(ft);
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void properties_A$List() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Field f = LocalClass.class.getDeclaredField("localField1");
		ClassTemplate ftarget = new ClassTemplate(f.getType());
		FieldTemplate ft = new FieldTemplate(f, ftarget);
		List<Template> properties = new ArrayList<>();
		properties.add(ft);
		target.properties(properties);
	}

	@Test
	public void methods_A$() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Method m = LocalClass.class.getDeclaredMethod("localMethod1");
		ClassTemplate mtarget = new ClassTemplate(m.getReturnType());
		MethodTemplate mt = new MethodTemplate(m, mtarget);
		target.addMethod(mt);
		List<Template> actual = target.methods();
		List<Template> expected = new ArrayList<>();
		expected.add(mt);
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void methods_A$List() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Method m = LocalClass.class.getDeclaredMethod("localMethod1");
		ClassTemplate mtarget = new ClassTemplate(m.getReturnType());
		MethodTemplate mt = new MethodTemplate(m, mtarget);
		List<Template> methods = new ArrayList<>();
		methods.add(mt);
		target.methods(methods);
	}
	
	@Test
	public void addMethod_A$() throws Exception{
		Class<?> target_ = LocalClass.class;
		ClassTemplate target = new ClassTemplate(target_);
		Method m = LocalClass.class.getDeclaredMethod("localMethod1");
		ClassTemplate mtarget = new ClassTemplate(m.getReturnType());
		MethodTemplate mt = new MethodTemplate(m, mtarget);
		target.addMethod(mt);
	}
}
