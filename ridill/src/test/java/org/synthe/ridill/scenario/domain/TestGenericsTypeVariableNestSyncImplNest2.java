package org.synthe.ridill.scenario.domain;

public class TestGenericsTypeVariableNestSyncImplNest2<T> extends TestGenericsTypeVariableNestSyncImplNest<T>{
	private T nestTypeParameter2;

	public T getNestTypeParameter2() {
		return nestTypeParameter2;
	}

	public void setNestTypeParameter2(T nestTypeParameter2) {
		this.nestTypeParameter2 = nestTypeParameter2;
	}
}
