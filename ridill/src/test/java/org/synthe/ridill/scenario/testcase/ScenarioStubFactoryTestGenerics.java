package org.synthe.ridill.scenario.testcase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.synthe.ridill.generate.ValueGenerator;
import org.synthe.ridill.scenario.domain.TestEmbed;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariable;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableImpl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestAsync;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestAsyncImpl;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSync;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariableNestSyncImpl;
import org.synthe.ridill.scenario.domain.TestInterface;
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
}
