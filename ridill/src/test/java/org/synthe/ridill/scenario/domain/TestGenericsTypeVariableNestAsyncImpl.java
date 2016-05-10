package org.synthe.ridill.scenario.domain;

import java.util.List;

public class TestGenericsTypeVariableNestAsyncImpl extends TestGenericsTypeVariableNestAsync<String>{
	private String implString;
	private List<String> implListString;
	public String getImplString() {
		return implString;
	}
	public void setImplString(String implString) {
		this.implString = implString;
	}
	public List<String> getImplListString() {
		return implListString;
	}
	public void setImplListString(List<String> implListString) {
		this.implListString = implListString;
	}
}
