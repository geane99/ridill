package org.synthe.ridill.scenario.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TestGenericsTypeVariableUsePropertyTypeParameterNest<K,V> extends TestGenericsTypeVariableUsePropertyTypeParameter<K,V>{
	private Map<List<K>,Set<V>> map2;
	private Set<List<K>> set2;
	private List<Set<K>> list2;
	private Queue<Collection<K>> queue2;
	private Collection<Queue<K>> collection2;
	private TestGenericsTypeVariable<TestGenericsTypeVariableUsePropertyTypeParameter<K,V>> domain2;
	public Map<List<K>, Set<V>> getMap2() {
		return map2;
	}
	public void setMap2(Map<List<K>, Set<V>> map2) {
		this.map2 = map2;
	}
	public Set<List<K>> getSet2() {
		return set2;
	}
	public void setSet2(Set<List<K>> set2) {
		this.set2 = set2;
	}
	public List<Set<K>> getList2() {
		return list2;
	}
	public void setList2(List<Set<K>> list2) {
		this.list2 = list2;
	}
	public Queue<Collection<K>> getQueue2() {
		return queue2;
	}
	public void setQueue2(Queue<Collection<K>> queue2) {
		this.queue2 = queue2;
	}
	public Collection<Queue<K>> getCollection2() {
		return collection2;
	}
	public void setCollection2(Collection<Queue<K>> collection2) {
		this.collection2 = collection2;
	}
	public TestGenericsTypeVariable<TestGenericsTypeVariableUsePropertyTypeParameter<K,V>> getDomain2() {
		return domain2;
	}
	public void setDomain2(TestGenericsTypeVariable<TestGenericsTypeVariableUsePropertyTypeParameter<K,V>> domain2) {
		this.domain2 = domain2;
	}
	
}
