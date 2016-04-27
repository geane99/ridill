package org.synthe.ridill.scenario.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@SuppressWarnings("unused")
public class TestGenericsTypeVariableUsePropertyTypeParameterNest<K,V> {
	private Map<List<K>,Set<V>> _map;
	private Set<List<K>> _set;
	private List<Set<K>> _list;
	private Queue<Collection<K>> _queue;
	private Collection<Queue<K>> _collection;
	private TestGenericsTypeVariable<TestGenericsTypeVariable2<K>> _domain;
}
