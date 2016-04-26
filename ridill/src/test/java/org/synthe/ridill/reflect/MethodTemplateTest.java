package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.synthe.ridill.generator.TargetInfo;
public class MethodTemplateTest {
	
	class LocalClass{
		String field = "test";
		public LocalClass local;
		
		public LocalClass testMethod1(){
			return new LocalClass();
		}
		public String testMethod2(){
			return field;
		}
	}
	
	@Test
	public void type() throws Exception {
		assertThat(MethodTemplate.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		assertThat(target, notNullValue());
	}

	@Test
	public void propertyName_A$() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		String actual = target.propertyName();
		String expected = method.getName();
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void set_A$Object$Object() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		String value = "test";
		target.set(instance, value);
	}

	@Test
	public void set_A$Object$Object_T$IllegalAccessException() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		String value = "test";
		try {
			target.set(instance, value);
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void get_A$Object() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		Object actual = target.get(instance);
		Object expected = null;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void get_A$Object_T$IllegalAccessException() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		try {
			target.get(instance);
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void invoke_A$Object$ObjectArray() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod2");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		LocalClass instance = new LocalClass();
		Object[] args = new Object[] {};
		Object actual = target.invoke(instance, args);
		Object expected = instance.field;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void invoke_A$Object$ObjectArray_T$InvocationTargetException() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		Object[] args = new Object[] {};
		try {
			target.invoke(instance, args);
		} catch (InvocationTargetException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void invoke_A$Object$ObjectArray_T$IllegalAccessException() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		Object[] args = new Object[] {};
		try {
			target.invoke(instance, args);
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void toTargetInfo_A$Object() throws Exception {
		Method method = LocalClass.class.getMethod("testMethod1");
		ClassTemplate local = new ClassTemplate(LocalClass.class);
		MethodTemplate target = new MethodTemplate(method,local);
		Template enclosing = new ClassTemplate(LocalClass.class);
		target.enclosing(enclosing);
		Object instance = new LocalClass();
		TargetInfo actual = target.toTargetInfo(instance);
		assertThat(actual.name(), is(equalTo(target.propertyName())));
		assertThat(actual.classname(), is(equalTo(target.templateName())));
		assertThat(actual.enclosingClassName(), is(equalTo(target.enclosing().templateName())));
		assertThat(actual.isProperty(), is(equalTo(false)));
		assertThat(actual.isMethod(), is(equalTo(true)));
		assertThat(actual.target(), is(equalTo(instance)));
	}

}
