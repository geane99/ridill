package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;

import org.junit.Test;
import org.synthe.ridill.generator.TargetInfo;
public class TypeParameterTemplateTest {
	
	class LocalGenericsClass<T>{
		@SuppressWarnings("unused")
		private T tInstance;
	}
	
	class LocalGenericExtendedClass extends LocalGenericsClass<String>{
	}
	
	@Test
	public void type() throws Exception {
		assertThat(TypeParameterTemplate.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		assertThat(target, notNullValue());
		
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);
		assertThat(extTarget, notNullValue());
	}

	@Test
	public void hasReal_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		
		Boolean actual = target.hasReal();
		Boolean expected = false;
		assertThat(actual, is(equalTo(expected)));
		
		Boolean actual2 = extTarget.hasReal();
		Boolean expected2 = true;
		assertThat(actual2, is(equalTo(expected2)));
	}

	@Test
	public void isTypeVariableParameter_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		Boolean actual = target.isTypeVariableParameter();
		Boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
		Boolean actual2 = extTarget.isTypeVariableParameter();
		Boolean expected2 = false;
		assertThat(actual2, is(equalTo(expected2)));
	}

	@Test
	public void isRealParameter_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		Boolean actual = target.isRealParameter();
		Boolean expected = false;
		assertThat(actual, is(equalTo(expected)));

		Boolean actual2 = extTarget.isRealParameter();
		Boolean expected2 = true;
		assertThat(actual2, is(equalTo(expected2)));
	
	}

	@Test
	public void templateName_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		String actual = target.templateName();
		String expected = types[0].getName();
		assertThat(actual, is(equalTo(expected)));

		String actual2 = extTarget.templateName();
		String expected2 = extTypes.getName();
		assertThat(actual2, is(equalTo(expected2)));
	}

	@Test
	public void isLocalClass_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		Boolean actual = target.isLocalClass();
		Boolean expected = false;
		assertThat(actual, is(equalTo(expected)));
		Boolean actual2 = extTarget.isLocalClass();
		Boolean expected2 = false;
		assertThat(actual2, is(equalTo(expected2)));

	}

	@Test
	public void isMemberClass_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		Boolean actual = target.isMemberClass();
		Boolean expected = false;
		assertThat(actual, is(equalTo(expected)));
		Boolean actual2 = extTarget.isMemberClass();
		Boolean expected2 = false;
		assertThat(actual2, is(equalTo(expected2)));
	}

	@Test
	public void isAnonymousClass_A$() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);
		Class<?> extTemplateClass = LocalGenericExtendedClass.class;
		Class<?> extTypes = (Class<?>)((ParameterizedType)LocalGenericExtendedClass.class.getGenericSuperclass()).getActualTypeArguments()[0];
		ClassTemplate extEnclosing = new ClassTemplate(extTemplateClass);
		TypeParameterTemplate extTarget = new TypeParameterTemplate(extTypes, extEnclosing);

		Boolean actual = target.isAnonymousClass();
		Boolean expected = false;
		assertThat(actual, is(equalTo(expected)));
		Boolean actual2 = extTarget.isAnonymousClass();
		Boolean expected2 = false;
		assertThat(actual2, is(equalTo(expected2)));
	}

	@Test
	public void toTargetInfo_A$Object() throws Exception {
		Class<?> templateClass = LocalGenericsClass.class;
		TypeVariable<?>[] types = templateClass.getTypeParameters();
		ClassTemplate enclosing = new ClassTemplate(templateClass);
		TypeParameterTemplate target = new TypeParameterTemplate(TemplateType.itsetfTypeParameters, types[0], enclosing);

		TargetInfo actual = target.toTargetInfo(new LocalGenericsClass<String>());
		TargetInfo expected = null;
		assertThat(actual, is(equalTo(expected)));
	}

}
