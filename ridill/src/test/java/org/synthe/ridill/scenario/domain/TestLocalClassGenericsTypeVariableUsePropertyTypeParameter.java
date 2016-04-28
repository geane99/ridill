package org.synthe.ridill.scenario.domain;

@SuppressWarnings("unused")
public class TestLocalClassGenericsTypeVariableUsePropertyTypeParameter<T>{
	private LocalClass local;
	
	class LocalClass{
		T generics;
	}
}
