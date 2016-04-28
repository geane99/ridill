package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;
import org.synthe.ridill.stub.TargetInfo;
public class ClassInfoTest {
	class LocalClass{
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((localField1 == null) ? 0 : localField1.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LocalClass other = (LocalClass) obj;
			if (localField1 == null) {
				if (other.localField1 != null)
					return false;
			} else if (!localField1.equals(other.localField1))
				return false;
			return true;
		}
		private String localField1;
		public void localMethod1(){
		}
		private ClassInfoTest getOuterType() {
			return ClassInfoTest.this;
		}
	}
	
	@Test
	public void type() throws Exception {
		assertThat(ClassInfo.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate _target = new ClassTemplate(target_);
		Class<?> enclosing = ClassInfoTest.class;
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		_target.enclosing(enclosingTemplate);
		ClassInfo target = _target.toClassInfo();
		assertThat(target, notNullValue());
	}

	@Test
	public void className_A$() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate _target = new ClassTemplate(target_);
		Class<?> enclosing = ClassInfoTest.class;
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		_target.enclosing(enclosingTemplate);
		ClassInfo target = _target.toClassInfo();
		String actual = target.className();
		String expected = target_.getName();
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void classType_A$() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate _target = new ClassTemplate(target_);
		Class<?> enclosing = ClassInfoTest.class;
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		_target.enclosing(enclosingTemplate);
		ClassInfo target = _target.toClassInfo();
		Object actual = target.classType();
		Object expected = _target.classType();
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isImmutable_A$() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate _target = new ClassTemplate(target_);
		Class<?> enclosing = ClassInfoTest.class;
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		_target.enclosing(enclosingTemplate);
		ClassInfo target = _target.toClassInfo();
		boolean actual = target.isImmutable();
		boolean expected = _target.isImmutable();
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void toTargetInfo_A$Object() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate _target = new ClassTemplate(target_);
		Class<?> enclosing = ClassInfoTest.class;
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		_target.enclosing(enclosingTemplate);
		ClassInfo target = _target.toClassInfo();
		LocalClass instance = new LocalClass();
		TargetInfo actual = target.toTargetInfo(instance);
		TargetInfo expected = _target.toTargetInfo(instance);
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void newInstance_A$() throws Exception {
		Class<?> target_ = LocalClass.class;
		ClassTemplate _target = new ClassTemplate(target_);
		Class<?> enclosing = ClassInfoTest.class;
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		_target.enclosing(enclosingTemplate);
		ClassInfo target = _target.toClassInfo();
		LocalClass actual = (LocalClass)target.newInstance();
		LocalClass expected = (LocalClass)_target.newInstance();
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void set_A$Object$Object() throws Exception {
		Template enclosingF = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getDeclaredField("localField1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		Class<?> enclosing = ClassInfoTest.class;
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		ClassTemplate enclosingTemplate = new ClassTemplate(enclosing);
		f1.enclosing(enclosingTemplate);
		f1.enclosing(enclosingF);
		ClassInfo target = f1.toClassInfo();
		LocalClass instance = new LocalClass();
		target.set(instance, "test");
		Object expected = "test";
		assertThat(instance.localField1, is(equalTo(expected)));
	}

	@Test
	public void set_A$Object$Object_T$IllegalAccessException() throws Exception {
		Template enclosing = new ClassTemplate(LocalClass.class);
		Field field1 = LocalClass.class.getDeclaredField("localField1");
		ClassTemplate ftarget = new ClassTemplate(String.class);
		FieldTemplate f1 = new FieldTemplate(field1, ftarget);
		f1.enclosing(enclosing);
		ClassInfo target = f1.toClassInfo();
		LocalClass instance = new LocalClass();
		try {
		target.set(instance, "test");
		} catch (IllegalAccessException e) {
			fail("Expected exception was not thrown!");
		}
	}

}
