package org.synthe.ridill.scenario.domain;

public class TestEntity {
	private TestEmbed domain1;
	private TestPrimitive domain2;
	private String string;
	private Integer integer;
	private TestEnum fieldEnum;
	public TestEmbed getDomain1() {
		return domain1;
	}
	public void setDomain1(TestEmbed domain1) {
		this.domain1 = domain1;
	}
	public TestPrimitive getDomain2() {
		return domain2;
	}
	public void setDomain2(TestPrimitive domain2) {
		this.domain2 = domain2;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public Integer getInteger() {
		return integer;
	}
	public void setInteger(Integer integer) {
		this.integer = integer;
	}
	public TestEnum getFieldEnum() {
		return fieldEnum;
	}
	public void setFieldEnum(TestEnum fieldEnum) {
		this.fieldEnum = fieldEnum;
	}
}
