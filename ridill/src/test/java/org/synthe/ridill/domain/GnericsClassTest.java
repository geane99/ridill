package org.synthe.ridill.domain;

public class GnericsClassTest <T>{
	private T genericArgProperty;
	
	public class GenericsClassInnerSameArgTest<T>{
	}
	public class GenericsClassInnerOtherArgTest<V>{
	}
	static class GenericsClassStaticInnerTest{
	}
}
