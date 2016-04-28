package org.synthe.ridill.scenario.domain;

public class TestGenericsTypeVariable<T> {
	private T fieldTypeVariable;
	private String string;
	public T getFieldTypeVariable() {
		return fieldTypeVariable;
	}
	public void setFieldTypeVariable(T fieldTypeVariable) {
		this.fieldTypeVariable = fieldTypeVariable;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	
}
