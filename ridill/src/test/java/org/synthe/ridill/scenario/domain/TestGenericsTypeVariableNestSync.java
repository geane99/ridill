package org.synthe.ridill.scenario.domain;

public class TestGenericsTypeVariableNestSync<T> extends TestGenericsTypeVariable<T> {
	private T parentFieldTypeVariable;
	private Double fieldDouble;
	public Double getFieldDouble() {
		return fieldDouble;
	}
	public void setFieldDouble(Double fieldDouble) {
		this.fieldDouble = fieldDouble;
	}
	public T getParentFieldTypeVariable() {
		return parentFieldTypeVariable;
	}
	public void setParentFieldTypeVariable(T parentFieldTypeVariable) {
		this.parentFieldTypeVariable = parentFieldTypeVariable;
	}
}
