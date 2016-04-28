package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.synthe.ridill.stub.TargetInfo;
public class FieldTemplateTest {
	class LocalClass{
		public String field1 = "test1";
		@SuppressWarnings("unused")
		private String field2 = "test2";
	}
	
	@Test
	public void type() throws Exception {
		assertThat(FieldTemplate.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		assertThat(f1, notNullValue());
	}

	@Test
	public void propertyName_A$() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		String actual = f1.propertyName();
		String expected = "field1";
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void set_A$Object$Object() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		Object value = "test1-invoke";
		f1.set(instance, value);
	}

	@Test
	public void set_A$Object$Object_T$IllegalAccessException() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		Object value = "test1-invoke";
		try {
			f1.set(instance, value);
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void get_A$Object() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		Object actual = f1.get(instance);
		Object expected = "test1";
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void get_A$Object_T$IllegalAccessException() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		try {
			f1.get(instance);
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void invoke_A$Object$ObjectArray() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		Object[] args = new Object[] {};
		Object actual = f1.invoke(instance, args);
		Object expected = null;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void invoke_A$Object$ObjectArray_T$InvocationTargetException() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		Object[] args = new Object[] {};
		try {
			f1.invoke(instance, args);
		} catch (InvocationTargetException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void invoke_A$Object$ObjectArray_T$IllegalAccessException() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getField("field1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		Object instance = new LocalClass();
		Object[] args = new Object[] {};
		try {
			f1.invoke(instance, args);
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

	@Test
	public void toTargetInfo_A$Object() throws Exception {
		Field field = LocalClass.class.getField("field1");
		ClassTemplate local = new ClassTemplate(String.class);
		FieldTemplate target = new FieldTemplate(field,local);
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
