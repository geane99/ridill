package org.synthe.ridill.scenario.domain;

import java.util.List;

public interface TestInterface {
	public TestAbstract returnTestAbstract();
	public TestAnnotation returnTestAnnotation();
	public TestArray returnTestArray();
	public TestCollections returnTestCollections();
	public TestEmbed returnTestEmbed();
	public TestEntity returnTestEntity();
	public TestEnum returnTestEnum();
	public <T> TestGenericsTypeVariable<T> returnTestGenericsTypeVariable();
	public TestGenericsTypeVariableImpl returnTestGenericsTypeVariableImpl();
	public <T> TestGenericsTypeVariableNestAsync<T> returnTestGenericsTypeVariableNestAsync();
	public TestGenericsTypeVariableNestAsyncImpl returnTestGenericsTypeVariableNestAsyncImpl();
	public <T> TestGenericsTypeVariableNestSync<T> returnTestGenericsTypeVariableNestSync();
	public TestGenericsTypeVariableNestSyncImpl returnTestGenericsTypeVariableNestSyncImpl();
	public <T> TestGenericsTypeVariableNestSyncImplNest<T> returnTestGenericsTypeVariableNestSyncImplNest();
	public <T> TestGenericsTypeVariableNestSyncImplNest2<T> returnTestGenericsTypeVariableNestSyncImplNest2();
	public TestGenericsTypeVariableNestSyncImplNest2Impl returnTestGenericsTypeVariableNestSyncImplNest2Impl();
	public <K,V> TestGenericsTypeVariableUsePropertyTypeParameter<K,V> returnTestGenericsTypeVariableUsePropertyTypeParameter();
	public TestGenericsTypeVariableUsePropertyTypeParameterImpl returnTestGenericsTypeVariableUsePropertyTypeParameterImpl();
	public <K,V> TestGenericsTypeVariableUsePropertyTypeParameterNest<K,V> returnTestGenericsTypeVariableUsePropertyTypeParameterNest();
	public TestGenericsTypeVariableUsePropertyTypeParameterNestImpl returnTestGenericsTypeVariableUsePropertyTypeParameterNestImpl();
	public TestInterface returnTestInterface();
	public TestLocalClass returnTestLocalClass();
	public <T> TestLocalClassGenericsTypeVariableUsePropertyTypeParameter<T> returnTestLocalClassGenericsTypeVariableUsePropertyTypeParameter();
	public TestLocalClassGenericsTypeVariableUsePropertyTypeParameterImpl returnTestLocalClassGenericsTypeVariableUsePropertyTypeParameterImpl();
	public TestPrimitive returnTestPrimitive();
	public TestRecursiveStructure returnTestRecursiveStructure();
	
	public <T> TestInterfaceGenericsTypeVariable<T> returnTestInterfaceGenericsTypeVariable();
	public <T> TestInterfaceGenericsTypeVariableAsync<T> returnTestInterfaceGenericsTypeVariableAsync();
	public TestInterfaceGenericsTypeVariableAsyncImpl returnTestInterfaceGenericsTypeVariableAsyncImpl();
	public TestInterfaceGenericsTypeVariableImpl returnTestInterfaceGenericsTypeVariableImpl();
	
	public List<String> returnGenericsParameterizedType();
	public <T> T returnGenerics();
}
