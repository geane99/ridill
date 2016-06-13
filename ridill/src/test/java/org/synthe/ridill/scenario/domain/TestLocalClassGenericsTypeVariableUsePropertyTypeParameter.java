package org.synthe.ridill.scenario.domain;

public class TestLocalClassGenericsTypeVariableUsePropertyTypeParameter<T>{
	private LocalClass local;
	
	public class LocalClass{
		private T generics;

		public T getGenerics() {
			return generics;
		}

		public void setGenerics(T generics) {
			this.generics = generics;
		}
	}

	public LocalClass getLocal() {
		return local;
	}

	public void setLocal(LocalClass local) {
		this.local = local;
	}
}
