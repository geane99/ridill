package org.synthe.ridill.scenario.domain;

public interface TestInterfaceGenericsTypeVariableAsync<T> extends TestInterfaceGenericsTypeVariable<String>{
	public T returnAsyncTypeVariable();
}
