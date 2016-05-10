package org.synthe.ridill.scenario.domain;

public class TestGenericsTypeVariableNestSyncImplNest<T> extends TestGenericsTypeVariableNestSyncImpl{
	private T nestTypeParameter1;

	public T getNestTypeParameter1() {
		return nestTypeParameter1;
	}

	public void setNestTypeParameter1(T nestTypeParameter1) {
		this.nestTypeParameter1 = nestTypeParameter1;
	}
}
