package org.synthe.ridill.scenario.domain;

import java.util.List;

public class TestArray {
	private String[] stringArray1;
	private String[][] stringArray2;
	private Integer[] integerArray1;
	private Integer[][] integerArray2;
	private List<String>[] listStringArray1;
	private List<Integer>[][] listIntegerArray2;
	private List<TestEmbed>[] listDomainArray1;
	private TestEmbed[] domainArray1;
	private TestEmbed[][] domainArray2;
	private TestEntity[] nestDomainArray1;
	private TestEntity[][] nestDomainArray2;
	private List<String[]>[] complexList;
	public String[] getStringArray1() {
		return stringArray1;
	}
	public void setStringArray1(String[] stringArray1) {
		this.stringArray1 = stringArray1;
	}
	public String[][] getStringArray2() {
		return stringArray2;
	}
	public void setStringArray2(String[][] stringArray2) {
		this.stringArray2 = stringArray2;
	}
	public Integer[] getIntegerArray1() {
		return integerArray1;
	}
	public void setIntegerArray1(Integer[] integerArray1) {
		this.integerArray1 = integerArray1;
	}
	public Integer[][] getIntegerArray2() {
		return integerArray2;
	}
	public void setIntegerArray2(Integer[][] integerArray2) {
		this.integerArray2 = integerArray2;
	}
	public List<String>[] getListStringArray1() {
		return listStringArray1;
	}
	public void setListStringArray1(List<String>[] listStringArray1) {
		this.listStringArray1 = listStringArray1;
	}
	public List<Integer>[][] getListIntegerArray2() {
		return listIntegerArray2;
	}
	public void setListIntegerArray2(List<Integer>[][] listIntegerArray2) {
		this.listIntegerArray2 = listIntegerArray2;
	}
	public TestEmbed[] getDomainArray1() {
		return domainArray1;
	}
	public void setDomainArray1(TestEmbed[] domainArray1) {
		this.domainArray1 = domainArray1;
	}
	public TestEmbed[][] getDomainArray2() {
		return domainArray2;
	}
	public void setDomainArray2(TestEmbed[][] domainArray2) {
		this.domainArray2 = domainArray2;
	}
	public TestEntity[] getNestDomainArray1() {
		return nestDomainArray1;
	}
	public void setNestDomainArray1(TestEntity[] nestDomainArray1) {
		this.nestDomainArray1 = nestDomainArray1;
	}
	public TestEntity[][] getNestDomainArray2() {
		return nestDomainArray2;
	}
	public void setNestDomainArray2(TestEntity[][] nestDomainArray2) {
		this.nestDomainArray2 = nestDomainArray2;
	}
	public List<TestEmbed>[] getListDomainArray1() {
		return listDomainArray1;
	}
	public void setListDomainArray1(List<TestEmbed>[] listDomainArray1) {
		this.listDomainArray1 = listDomainArray1;
	}
	public List<String[]>[] getComplexList() {
		return complexList;
	}
	public void setComplexList(List<String[]>[] complexList) {
		this.complexList = complexList;
	}
}
