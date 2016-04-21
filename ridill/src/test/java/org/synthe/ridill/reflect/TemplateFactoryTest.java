package org.synthe.ridill.reflect;

import org.synthe.ridill.reflect.TemplateFactory.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

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
public class TemplateFactoryTest {

	@Test
	public void type() throws Exception {
		assertThat(TemplateFactory.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		TemplateFactory target = new TemplateFactory();
		assertThat(target, notNullValue());
	}

	@Test
	public void returnType_A$Object$Method$ObjectArray() throws Exception {
//		Object proxy = null;
//		Method method = null;
//		Object[] params = new Object[] {};
//		Object actual = TemplateFactory.returnType(proxy, method, params);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void fieldType_A$Object$Field() throws Exception {
//		Object info = null;
//		Field field = null;
//		Object actual = TemplateFactory.fieldType(info, field);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void fieldTypeAll_A$Object() throws Exception {
//		Object owner = null;
//		List<Object> actual = TemplateFactory.fieldTypeAll(owner);
//		List<Object> expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void fieldTypeParameterType_A$Object$Integer() throws Exception {
//		Object info = null;
//		Integer genericsIndex = null;
//		Object actual = TemplateFactory.fieldTypeParameterType(info, genericsIndex);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void createByClassType_A$Class() throws Exception {
//		Class<?> clazz = null;
//		Object actual = TemplateFactory.createByClassType(clazz);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void createByTypeVariableType_A$Object$TypeVariable$Object() throws Exception {
//		Object enclosing = null;
//		TypeVariable<?> parameterType = null;
//		Object templateType = null;
//		Object actual = TemplateFactory.createByTypeVariableType(enclosing, parameterType, templateType);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void createByParameterizedType_A$Object$ParameterizedType$Object() throws Exception {
//		Object enclosing = null;
//		ParameterizedType parameterType = null;
//		Object templateType = null;
//		Object actual = TemplateFactory.createByParameterizedType(enclosing, parameterType, templateType);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void createByClassType_A$Object$Class$Object() throws Exception {
//		Object enclosing = null;
//		Class<?> clazz = null;
//		Object tempalteType = null;
//		Object actual = TemplateFactory.createByClassType(enclosing, clazz, tempalteType);
//		Object expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void createFieldTypeAll_A$Object() throws Exception {
//		Object enclosing = null;
//		List<Object> actual = TemplateFactory.createFieldTypeAll(enclosing);
//		List<Object> expected = null;
//		assertThat(actual, is(equalTo(expected)));
	}

}
