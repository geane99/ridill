package org.synthe.ridill.scenario.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@SuppressWarnings("unused")
public class TestGenericsTypeVariableUsePropertyTypeParameter<K,V> {
	private Map<K,V> _map;
	private Set<K> _set;
	private List<K> _list;
	private Queue<K> _queue;
	private Collection<K> _collection;
	private K[] _array1;
	private K[][] _array2;
	private K[][][] _array3;
	private TestGenericsTypeVariable<K> _domain;
}
