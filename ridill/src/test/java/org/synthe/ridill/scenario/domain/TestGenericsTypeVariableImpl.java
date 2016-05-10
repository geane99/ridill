package org.synthe.ridill.scenario.domain;

import java.util.List;

public class TestGenericsTypeVariableImpl extends TestGenericsTypeVariable<String>{
	private List<String> listString;
	private TestGenericsTypeVariable<String> genericsClassField;
	public List<String> getListString() {
		return listString;
	}
	public void setListString(List<String> listString) {
		this.listString = listString;
	}
	public TestGenericsTypeVariable<String> getGenericsClassField() {
		return genericsClassField;
	}
	public void setGenericsClassField(
			TestGenericsTypeVariable<String> genericsClassField) {
		this.genericsClassField = genericsClassField;
	}
	
}
