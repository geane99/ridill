package org.synthe.ridill.scenario.domain;

import java.util.List;

public class TestGenericsTypeVariableUsePropertyTypeParameterImpl extends TestGenericsTypeVariableUsePropertyTypeParameter<TestEntity, TestEmbed>{
	private TestEntity domain2;
	private List<TestEntity> listDomain;
	public TestEntity getDomain2() {
		return domain2;
	}
	public void setDomain2(TestEntity domain2) {
		this.domain2 = domain2;
	}
	public List<TestEntity> getListDomain() {
		return listDomain;
	}
	public void setListDomain(List<TestEntity> listDomain) {
		this.listDomain = listDomain;
	}
}
