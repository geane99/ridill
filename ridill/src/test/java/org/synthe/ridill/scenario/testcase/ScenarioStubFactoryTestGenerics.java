package org.synthe.ridill.scenario.testcase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.synthe.ridill.generate.ValueGenerator;
import org.synthe.ridill.scenario.domain.TestEmbed;
import org.synthe.ridill.scenario.domain.TestEntity;
import org.synthe.ridill.scenario.domain.TestEnum;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariable;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariable2;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableImpl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestAsync;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestAsyncImpl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSync;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSyncImpl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSyncImplNest;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSyncImplNest2;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSyncImplNest2Impl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableUsePropertyTypeParameter;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableUsePropertyTypeParameterImpl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableUsePropertyTypeParameterNest;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableUsePropertyTypeParameterNestImpl;
import org.synthe.ridill.scenario.domain.TestInterface;
import org.synthe.ridill.scenario.domain.TestPrimitive;
import org.synthe.ridill.stub.StubFactory;
import org.synthe.ridill.stub.TargetInfo;

public class ScenarioStubFactoryTestGenerics{
	class ValueGeneratorForGenericsTesting implements ValueGenerator{
		@Override
		public Byte getByte(TargetInfo info) {
			return 1;
		}
		@Override
		public Boolean getBoolean(TargetInfo info) {
			return false;
		}
		@Override
		public Float getFloat(TargetInfo info) {
			return 2.1F;
		}
		@Override
		public Double getDouble(TargetInfo info) {
			return 10.5;
		}
		@Override
		public Short getShort(TargetInfo info) {
			return 5;
		}
		@Override
		public Integer getInteger(TargetInfo info) {
			return 20;
		}
		@Override
		public Long getLong(TargetInfo info) {
			return 1500L;
		}
		@Override
		public Character getCharacter(TargetInfo info) {
			return 'c';
		}
		@Override
		public String getString(TargetInfo info) {
			return "test!!";
		}
		@Override
		public Integer getCollectionSize(TargetInfo info) {
			return 7;
		}
		@Override
		public Enum<?> getEnum(TargetInfo info, List<Enum<?>> enums) {
			return enums.get(0);
		}
	}
	private <T> List<?> toList(T[] t){
		return Arrays.asList(t);
	}
	@SuppressWarnings("unchecked")
	private List<Enum<?>> toEnumList(Class<?> e){
		return (List<Enum<?>>)Arrays.asList(e.getEnumConstants());
	}
	private void testGenerateTestEmbed(TestEmbed embed, ValueGeneratorForGenericsTesting generator){
		//test simple pojo (property is primitive wrapper only)
		
		Boolean booleanActual = embed.getFieldBoolean();
		Boolean booleanExpected = generator.getBoolean(null);
		assertThat(booleanActual, is(equalTo(booleanExpected)));
		
		Byte byteActual = embed.getFieldByte();
		Byte byteExpected = generator.getByte(null);
		assertThat(byteActual, is(equalTo(byteExpected)));
		
		Character charActual = embed.getFieldCharacter();
		Character charExpected = generator.getCharacter(null);
		assertThat(charActual, is(equalTo(charExpected)));
		
		Short shortActual = embed.getFieldShort();
		Short shortExpected = generator.getShort(null);
		assertThat(shortActual, is(equalTo(shortExpected)));
		
		Integer intActual = embed.getFieldInteger();
		Integer intExpected = generator.getInteger(null);
		assertThat(intActual, is(equalTo(intExpected)));

		Long longActual = embed.getFieldLong();
		Long longExpected = generator.getLong(null);
		assertThat(longActual, is(equalTo(longExpected)));

		Float floatActual = embed.getFieldFloat();
		Float floatExpected = generator.getFloat(null);
		assertThat(floatActual, is(equalTo(floatExpected)));
		
		Double doubleActual = embed.getFieldDouble();
		Double doubleExpected = generator.getDouble(null);
		assertThat(doubleActual, is(equalTo(doubleExpected)));
	}
	
	private void testGenerateTestPrimitive(TestPrimitive primitive, ValueGeneratorForGenericsTesting generator){
		//test simple pojo (property is primitive only)
		
		boolean booleanActual2 = primitive.isFieldBoolean();
		boolean booleanExpected2 = generator.getBoolean(null);
		assertThat(booleanActual2, is(equalTo(booleanExpected2)));
		
		byte byteActual2 = primitive.getFieldByte();
		byte byteExpected2 = generator.getByte(null);
		assertThat(byteActual2, is(equalTo(byteExpected2)));
		
		char charActual2 = primitive.getFieldCharacter();
		char charExpected2 = generator.getCharacter(null);
		assertThat(charActual2, is(equalTo(charExpected2)));
		
		short shortActual2 = primitive.getFieldShort();
		short shortExpected2 = generator.getShort(null);
		assertThat(shortActual2, is(equalTo(shortExpected2)));
		
		int intActual2 = primitive.getFieldInteger();
		int intExpected2 = generator.getInteger(null);
		assertThat(intActual2, is(equalTo(intExpected2)));

		long longActual2 = primitive.getFieldLong();
		long longExpected2 = generator.getLong(null);
		assertThat(longActual2, is(equalTo(longExpected2)));

		float floatActual2 = primitive.getFieldFloat();
		float floatExpected2 = generator.getFloat(null);
		assertThat(floatActual2, is(equalTo(floatExpected2)));
		
		double doubleActual2 = primitive.getFieldDouble();
		double doubleExpected2 = generator.getDouble(null);
		assertThat(doubleActual2, is(equalTo(doubleExpected2)));
	}	
	
	private void testGenerateTestEntity(TestEntity entity, ValueGeneratorForGenericsTesting generator){
		//test entity that has pojo and primitive(wrapper), enumeration properties
		
		TestEmbed embed = entity.getDomain1();
		testGenerateTestEmbed(embed, generator);
		
		TestPrimitive primitive = entity.getDomain2();
		testGenerateTestPrimitive(primitive, generator);

		String stringActual = entity.getString();
		String stringExpected = generator.getString(null);
		assertThat(stringActual, is(equalTo(stringExpected)));
		
		Integer integerActual = entity.getInteger();
		Integer integerExpected = generator.getInteger(null);
		assertThat(integerActual, is(equalTo(integerExpected)));

		TestEnum enumActual = entity.getFieldEnum();
		TestEnum enumExpected = (TestEnum)generator.getEnum(null, toEnumList(TestEnum.class));
		assertThat(enumActual, is(equalTo(enumExpected)));
	}	
	
	@Test
	public void testGenericsTypeVariable(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		
		TestGenericsTypeVariable<String> instance = test.returnTestGenericsTypeVariable();
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		//直接指定されたTypeParameterは取れないのでnullになるはず
		assertThat(instance.getFieldTypeVariable(), is(equalTo(null)));
	}
	
	@Test
	public void testGenericsTypeVariable2(){
		//testGenericsTypeVariable2はtestGenericsTypeVariableと一緒なので何もしない
	}
	
	@Test
	public void testGenericsTypeVariableImpl(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableImpl instance = test.returnTestGenericsTypeVariableImpl();
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		assertThat(instance.getFieldTypeVariable(), is(equalTo(generator.getString(null))));
		assertThat(instance.getGenericsClassField().getString(), is(equalTo(generator.getString(null))));
		assertThat(instance.getGenericsClassField().getFieldTypeVariable(), is(equalTo(generator.getString(null))));
		
		assertThat(instance.getListString().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getListString().forEach((each)->{
			assertThat(each, is(equalTo(generator.getString(null))));
		});
	}
	
	@Test
	public void testGenericsTypeVariableNestAsync(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestAsync<String> instance = test.returnTestGenericsTypeVariableNestAsync();
		
		assertThat(instance.getFieldTypeVariableAsync(), is(equalTo(null)));
		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		
		testGenerateTestEmbed(instance.getFieldTypeVariable(), generator);
	}
	
	@Test
	public void testGenericsTypeVariableNestAsyncImpl(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestAsyncImpl instance = test.returnTestGenericsTypeVariableNestAsyncImpl();

		assertThat(instance.getFieldTypeVariableAsync(), is(equalTo(generator.getString(null))));
		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		
		testGenerateTestEmbed(instance.getFieldTypeVariable(), generator);
		
		assertThat(instance.getImplString(), is(equalTo(generator.getString(null))));
		assertThat(instance.getImplListString().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getImplListString().forEach((each)->{
			assertThat(each, is(equalTo(generator.getString(null))));
		});
	}
	
	@Test
	public void testGenericsTypeVariableNestSync(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestSync<String> instance = test.returnTestGenericsTypeVariableNestSync();

		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		assertThat(instance.getFieldTypeVariable(), is(equalTo(null)));
		assertThat(instance.getParentFieldTypeVariable(), is(equalTo(null)));
	}
	
	@Test
	public void testGenericsTypeVariableNestSyncImpl(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestSyncImpl instance = test.returnTestGenericsTypeVariableNestSyncImpl();
		
		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		testGenerateTestEmbed(instance.getFieldTypeVariable(), generator);
		testGenerateTestEmbed(instance.getParentFieldTypeVariable(), generator);
	}
	
	@Test
	public void testGenericsTypeVariableNestSyncImplNest(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestSyncImplNest<TestEmbed> instance = test.returnTestGenericsTypeVariableNestSyncImplNest();
		
		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		testGenerateTestEmbed(instance.getFieldTypeVariable(), generator);
		testGenerateTestEmbed(instance.getParentFieldTypeVariable(), generator);
		assertThat(instance.getNestTypeParameter1(), is(equalTo(null)));
	}
	
	@Test
	public void testGenericsTypeVariableNestSyncImplNest2(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestSyncImplNest2<TestEmbed> instance = test.returnTestGenericsTypeVariableNestSyncImplNest2();
		
		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		testGenerateTestEmbed(instance.getFieldTypeVariable(), generator);
		testGenerateTestEmbed(instance.getParentFieldTypeVariable(), generator);
		assertThat(instance.getNestTypeParameter1(), is(equalTo(null)));
		assertThat(instance.getNestTypeParameter2(), is(equalTo(null)));
	}
	
	@Test
	public void testGenericsTypeVariableNestSyncImplNest2Impl(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableNestSyncImplNest2Impl instance = test.returnTestGenericsTypeVariableNestSyncImplNest2Impl();
		
		assertThat(instance.getFieldDouble(), is(equalTo(generator.getDouble(null))));
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		testGenerateTestEmbed(instance.getFieldTypeVariable(), generator);
		testGenerateTestEmbed(instance.getParentFieldTypeVariable(), generator);
		testGenerateTestPrimitive(instance.getNestTypeParameter1(), generator);
		testGenerateTestPrimitive(instance.getNestTypeParameter2(), generator);
	}
	
	@Test
	public void testGenericsTypeVariableUsePropertyTypeParameter(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableUsePropertyTypeParameter<String,String> instance = test.returnTestGenericsTypeVariableUsePropertyTypeParameter();
		
		assertThat(instance.getList().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getList().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});
		
		assertThat(instance.getCollection().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getCollection().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});

		assertThat(instance.getQueue().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getQueue().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});

		//hashがnullしかないので1になる
		assertThat(instance.getSet().size(), is(equalTo(1)));
		instance.getSet().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});
		
		//keyがnullしかないので1になる
		assertThat(instance.getMap().entrySet().size(), is(equalTo(1)));
		instance.getMap().entrySet().forEach((each)->{
			assertThat(each.getKey(), is(equalTo(null)));
			assertThat(each.getValue(), is(equalTo(null)));
		});
		
		try{
			assertThat(instance.getArray1().length, is(equalTo(generator.getCollectionSize(null))));
			fail("Expected exception thrown!");
		}
		catch(ClassCastException cce){
		}
		Object[] array1 = instance.getArray1();
		assertThat(array1.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array1).forEach((Object each) -> {
			assertThat(each, is(equalTo(null)));
		});
		
		Object[][] array2 = instance.getArray2();
		assertThat(array2.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array2).forEach((i) -> {
			Object[] i2 = (Object[])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				assertThat(i3, is(equalTo(null)));
			});
		});

		Object[][][] array3 = instance.getArray3();
		assertThat(array3.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array3).forEach((i) -> {
			Object[][] i2 = (Object[][])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				Object[] i4 = (Object[])i3;
				assertThat(i4.length, is(equalTo(generator.getCollectionSize(null))));
				toList(i4).forEach((Object i5) -> {
					assertThat(i5, is(equalTo(null)));
				});
			});
		});
		TestGenericsTypeVariable<String> instance2 = instance.getDomain();
		assertThat(instance2.getString(), is(equalTo(generator.getString(null))));
		//直接指定されたTypeParameterは取れないのでnullになるはず
		assertThat(instance2.getFieldTypeVariable(), is(equalTo(null)));

	}
	
	@Test
	public void testGenericsTypeVariableUsePropertyTypeParameterImpl(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableUsePropertyTypeParameterImpl instance = test.returnTestGenericsTypeVariableUsePropertyTypeParameterImpl();

		assertThat(instance.getList().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getList().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});
		
		assertThat(instance.getCollection().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getCollection().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});

		assertThat(instance.getQueue().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getQueue().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});

		assertThat(instance.getSet().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getSet().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});
		
		assertThat(instance.getMap().entrySet().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getMap().entrySet().forEach((each)->{
			testGenerateTestEntity(each.getKey(), generator);
			testGenerateTestEmbed(each.getValue(), generator);
		});
		
		TestEntity[] array1 = instance.getArray1();
		assertThat(array1.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array1).forEach(each -> {
			TestEntity e = (TestEntity)each;
			testGenerateTestEntity(e, generator);
		});
		
		TestEntity[][] array2 = instance.getArray2();
		assertThat(array2.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array2).forEach((i) -> {
			TestEntity[] i2 = (TestEntity[])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				TestEntity e = (TestEntity)i3;
				testGenerateTestEntity(e, generator);
			});
		});

		TestEntity[][][] array3 = instance.getArray3();
		assertThat(array3.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array3).forEach((i) -> {
			TestEntity[][] i2 = (TestEntity[][])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				TestEntity[] i4 = (TestEntity[])i3;
				assertThat(i4.length, is(equalTo(generator.getCollectionSize(null))));
				toList(i4).forEach((Object i5) -> {
					TestEntity e = (TestEntity)i5;
					testGenerateTestEntity(e, generator);
				});
			});
		});
		TestGenericsTypeVariable<TestEntity> instance2 = instance.getDomain();
		assertThat(instance2.getString(), is(equalTo(generator.getString(null))));
		testGenerateTestEntity(instance2.getFieldTypeVariable(), generator);
	}
	
	@Test
	public void testGenericsTypeVariableUsePropertyTypeParameterNest(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableUsePropertyTypeParameterNest<String,String> instance = test.returnTestGenericsTypeVariableUsePropertyTypeParameterNest();
		
		assertThat(instance.getList().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getList().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});
		
		assertThat(instance.getCollection().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getCollection().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});

		assertThat(instance.getQueue().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getQueue().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});

		//hashがnullしかないので1になる
		assertThat(instance.getSet().size(), is(equalTo(1)));
		instance.getSet().forEach((each)->{
			assertThat(each, is(equalTo(null)));
		});
		
		//keyがnullしかないので1になる
		assertThat(instance.getMap().entrySet().size(), is(equalTo(1)));
		instance.getMap().entrySet().forEach((each)->{
			assertThat(each.getKey(), is(equalTo(null)));
			assertThat(each.getValue(), is(equalTo(null)));
		});
		
		try{
			assertThat(instance.getArray1().length, is(equalTo(generator.getCollectionSize(null))));
			fail("Expected exception thrown!");
		}
		catch(ClassCastException cce){
		}
		Object[] array1 = instance.getArray1();
		assertThat(array1.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array1).forEach((Object each) -> {
			assertThat(each, is(equalTo(null)));
		});
		
		Object[][] array2 = instance.getArray2();
		assertThat(array2.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array2).forEach((i) -> {
			Object[] i2 = (Object[])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				assertThat(i3, is(equalTo(null)));
			});
		});

		Object[][][] array3 = instance.getArray3();
		assertThat(array3.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array3).forEach((i) -> {
			Object[][] i2 = (Object[][])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				Object[] i4 = (Object[])i3;
				assertThat(i4.length, is(equalTo(generator.getCollectionSize(null))));
				toList(i4).forEach((Object i5) -> {
					assertThat(i5, is(equalTo(null)));
				});
			});
		});
		TestGenericsTypeVariable<String> instance2 = instance.getDomain();
		assertThat(instance2.getString(), is(equalTo(generator.getString(null))));
		//直接指定されたTypeParameterは取れないのでnullになるはず
		assertThat(instance2.getFieldTypeVariable(), is(equalTo(null)));
		
		assertThat(instance.getList2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getList2().forEach((each)->{
			//setなのでnullキーのサイズ1になるはず
			assertThat(each.size(), is(equalTo(1)));
			each.forEach(e2 -> {
				assertThat(e2, is(equalTo(null)));
			});
		});
		
		assertThat(instance.getCollection2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getCollection2().forEach((each)->{
			assertThat(each.size(), is(equalTo(generator.getCollectionSize(null))));
			each.forEach(e2 -> {
				assertThat(e2, is(equalTo(null)));
			});
		});

		assertThat(instance.getQueue2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getQueue2().forEach((each)->{
			assertThat(each.size(), is(equalTo(generator.getCollectionSize(null))));
			each.forEach(e2 -> {
				assertThat(e2, is(equalTo(null)));
			});
		});

		//hashがnullしかないので1になる
		assertThat(instance.getSet2().size(), is(equalTo(1)));
		instance.getSet2().forEach((each)->{
			assertThat(each.size(), is(equalTo(generator.getCollectionSize(null))));
			each.forEach(e2 -> {
				assertThat(e2, is(equalTo(null)));
			});
		});
		
		//keyがnullしかないので1になる
		assertThat(instance.getMap2().entrySet().size(), is(equalTo(1)));
		instance.getMap2().entrySet().forEach((each)->{
			assertThat(each.getKey().size(), is(equalTo(generator.getCollectionSize(null))));
			each.getKey().forEach(e2 -> {
				assertThat(e2, is(equalTo(null)));
			});
			
			//keyがnullしかないので1になる
			assertThat(each.getValue().size(), is(equalTo(1)));
			each.getValue().forEach(e2 -> {
				assertThat(e2, is(equalTo(null)));
			});
		});
		
		TestGenericsTypeVariable<TestGenericsTypeVariable2<String>> instance3 = instance.getDomain2();
		assertThat(instance3.getString(), is(equalTo(generator.getString(null))));
		TestGenericsTypeVariable2<String> instance4 = instance3.getFieldTypeVariable();
		assertThat(instance4.getInteger(), is(equalTo(generator.getInteger(null))));
		//直接指定されたTypeParameterは取れないのでnullになるはず
		assertThat(instance4.getFieldTypeVariable(), is(equalTo(null)));
	}
	
	@Test
	public void testGenericsTypeVariableUsePropertyTypeParameterNestImpl(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestGenericsTypeVariableUsePropertyTypeParameterNestImpl instance = test.returnTestGenericsTypeVariableUsePropertyTypeParameterNestImpl();

		assertThat(instance.getList().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getList().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});
		
		assertThat(instance.getCollection().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getCollection().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});

		assertThat(instance.getQueue().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getQueue().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});

		assertThat(instance.getSet().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getSet().forEach((each)->{
			testGenerateTestEntity(each, generator);
		});
		
		assertThat(instance.getMap().entrySet().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getMap().entrySet().forEach((each)->{
			testGenerateTestEntity(each.getKey(), generator);
			testGenerateTestEmbed(each.getValue(), generator);
		});
		
		TestEntity[] array1 = instance.getArray1();
		assertThat(array1.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array1).forEach(each -> {
			TestEntity e = (TestEntity)each;
			testGenerateTestEntity(e, generator);
		});
		
		TestEntity[][] array2 = instance.getArray2();
		assertThat(array2.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array2).forEach((i) -> {
			TestEntity[] i2 = (TestEntity[])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				TestEntity e = (TestEntity)i3;
				testGenerateTestEntity(e, generator);
			});
		});

		TestEntity[][][] array3 = instance.getArray3();
		assertThat(array3.length, is(equalTo(generator.getCollectionSize(null))));
		toList(array3).forEach((i) -> {
			TestEntity[][] i2 = (TestEntity[][])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((Object i3) -> {
				TestEntity[] i4 = (TestEntity[])i3;
				assertThat(i4.length, is(equalTo(generator.getCollectionSize(null))));
				toList(i4).forEach((Object i5) -> {
					TestEntity e = (TestEntity)i5;
					testGenerateTestEntity(e, generator);
				});
			});
		});
		TestGenericsTypeVariable<TestEntity> instance2 = instance.getDomain();
		assertThat(instance2.getString(), is(equalTo(generator.getString(null))));
		testGenerateTestEntity(instance2.getFieldTypeVariable(), generator);
		
		
		assertThat(instance.getList2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getList2().forEach((each)->{
			//setなので生成された同じ値しか入らないので1つのはず
			assertThat(each.size(), is(equalTo(1)));
			each.forEach(e2 -> {
				testGenerateTestEntity(e2, generator);
			});
		});
		
		assertThat(instance.getCollection2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getCollection2().forEach((each)->{
			assertThat(each.size(), is(equalTo(generator.getCollectionSize(null))));
			each.forEach(e2 -> {
				testGenerateTestEntity(e2, generator);
			});
		});

		assertThat(instance.getQueue2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getQueue2().forEach((each)->{
			assertThat(each.size(), is(equalTo(generator.getCollectionSize(null))));
			each.forEach(e2 -> {
				testGenerateTestEntity(e2, generator);
			});
		});

		assertThat(instance.getSet2().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getSet2().forEach((each)->{
			assertThat(each.size(), is(equalTo(generator.getCollectionSize(null))));
			each.forEach(e2 -> {
				testGenerateTestEntity(e2, generator);
			});
		});
		
		assertThat(instance.getMap2().entrySet().size(), is(equalTo(generator.getCollectionSize(null))));
		instance.getMap2().entrySet().forEach((each)->{
			assertThat(each.getKey().size(), is(equalTo(generator.getCollectionSize(null))));
			each.getKey().forEach(e2 -> {
				testGenerateTestEntity(e2, generator);
			});
			
			assertThat(each.getValue().size(), is(equalTo(generator.getCollectionSize(null))));
			each.getValue().forEach(e2 -> {
				testGenerateTestEmbed(e2, generator);
			});
		});
		
		TestGenericsTypeVariable<TestGenericsTypeVariable2<TestEntity>> instance3 = instance.getDomain2();
		assertThat(instance3.getString(), is(equalTo(generator.getString(null))));
		TestGenericsTypeVariable2<TestEntity> instance4 = instance3.getFieldTypeVariable();
		assertThat(instance4.getInteger(), is(equalTo(generator.getInteger(null))));
		testGenerateTestEntity(instance4.getFieldTypeVariable(), generator);
	}
}
