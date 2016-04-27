package org.synthe.ridill.scenario.domain;

import java.util.Collection;
import java.util.List;

public class TestCollections {
	private Collection<String> string;
	private Collection<List<String>> listString;
	private Collection<TestEmbed> domain;
	private Collection<TestEntity> nestDomain;
	
	public Collection<String> getString() {
		return string;
	}
	public void setString(Collection<String> string) {
		this.string = string;
	}
	public Collection<List<String>> getListString() {
		return listString;
	}
	public void setListString(Collection<List<String>> listString) {
		this.listString = listString;
	}
	public Collection<TestEmbed> getDomain() {
		return domain;
	}
	public void setDomain(Collection<TestEmbed> domain) {
		this.domain = domain;
	}
	public Collection<TestEntity> getNestDomain() {
		return nestDomain;
	}
	public void setNestDomain(Collection<TestEntity> nestDomain) {
		this.nestDomain = nestDomain;
	}
	
}
