package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
public class ClassTypeTest {

	@Test
	public void type() throws Exception {
		assertThat(ClassType.class, notNullValue());
	}

	@Test
	public void get_A$Class() throws Exception {
		Class<?> clazz = null;
		Object actual = ClassType.get(clazz);
		Object expected = null;
		assertThat(actual, is(equalTo(expected)));
	}

}
