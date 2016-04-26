package org.synthe.ridill.domain;

public class GenericsClassTest <T>{
	@SuppressWarnings("unused")
	private T genericsArgPrivateProperty;
	public T genericsArgPublicProperty;
	
	public T returnGenericsClassParameter(){
		return genericsArgPublicProperty;
	}
	
	public <V> V returnGenericsMthodParameter(V arg){
		return arg;
	}
	
	public class GenericsClassInnerSameArgTest{
		@SuppressWarnings("unused")
		private T innerGenericsArgPrivateProperty;
	}
}
