package org.synthe.ridill.scenario.domain;

public class TestGenericsTypeVariableNestAsync<T> extends TestGenericsTypeVariable<TestEmbed> {
	private T fieldTypeVariableAsync;
	private Double fieldDouble;
	public Double getFieldDouble() {
		return fieldDouble;
	}
	public void setFieldDouble(Double fieldDouble) {
		this.fieldDouble = fieldDouble;
	}
	public T getFieldTypeVariableAsync() {
		return fieldTypeVariableAsync;
	}
	public void setFieldTypeVariableAsync(T fieldTypeVariableAsync) {
		this.fieldTypeVariableAsync = fieldTypeVariableAsync;
	}
}