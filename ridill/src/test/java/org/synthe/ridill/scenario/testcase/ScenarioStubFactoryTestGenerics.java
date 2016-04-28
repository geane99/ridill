package org.synthe.ridill.scenario.testcase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.synthe.ridill.generate.ValueGenerator;
import org.synthe.ridill.scenario.domain.TestGenericsTypeVariable;
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
	
	@Test
	public void testGenericsTypeVariable(){
		ValueGeneratorForGenericsTesting generator = new ValueGeneratorForGenericsTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		
		TestGenericsTypeVariable<String> instance = test.returnTestGenericsTypeVariable();
		assertThat(instance.getString(), is(equalTo(generator.getString(null))));
		
		//when enclosing meshod difine generics type parameter, cant get reflection.
		//メソッド内で型パラメータを定義した場合、リフレクションでは取れないのでobjectを放り込むが、取れないのでエラーになる
		try{
			instance.getFieldTypeVariable();
		}
		catch(Exception e){
			fail("Expected exception was thrown!");
		}
	}
}
