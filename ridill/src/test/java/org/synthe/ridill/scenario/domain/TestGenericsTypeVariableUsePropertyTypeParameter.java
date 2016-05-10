package org.synthe.ridill.scenario.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TestGenericsTypeVariableUsePropertyTypeParameter<K,V> {
	private Map<K,V> map;
	private Set<K> set;
	private List<K> list;
	private Queue<K> queue;
	private Collection<K> collection;
	private K[] array1;
	private K[][] array2;
	private K[][][] array3;
	private TestGenericsTypeVariable<K> domain;
	public Map<K, V> getMap() {
		return map;
	}
	public void setMap(Map<K, V> map) {
		this.map = map;
	}
	public Set<K> getSet() {
		return set;
	}
	public void setSet(Set<K> set) {
		this.set = set;
	}
	public List<K> getList() {
		return list;
	}
	public void setList(List<K> list) {
		this.list = list;
	}
	public Queue<K> getQueue() {
		return queue;
	}
	public void setQueue(Queue<K> queue) {
		this.queue = queue;
	}
	public Collection<K> getCollection() {
		return collection;
	}
	public void setCollection(Collection<K> collection) {
		this.collection = collection;
	}
	public K[] getArray1() {
		return array1;
	}
	public void setArray1(K[] array1) {
		this.array1 = array1;
	}
	public K[][] getArray2() {
		return array2;
	}
	public void setArray2(K[][] array2) {
		this.array2 = array2;
	}
	public K[][][] getArray3() {
		return array3;
	}
	public void setArray3(K[][][] array3) {
		this.array3 = array3;
	}
	public TestGenericsTypeVariable<K> getDomain() {
		return domain;
	}
	public void setDomain(TestGenericsTypeVariable<K> domain) {
		this.domain = domain;
	}
}
