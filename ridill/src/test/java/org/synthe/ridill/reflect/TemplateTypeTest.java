package org.synthe.ridill.reflect;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TemplateTypeTest {

	@Test
	public void type() throws Exception {
		assertThat(TemplateType.class, notNullValue());
	}

}
