package org.synthe.ridill.scenario.testcase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.synthe.ridill.generator.StubFactory;
import org.synthe.ridill.generator.TargetInfo;
import org.synthe.ridill.generator.ValueGenerator;
import org.synthe.ridill.scenario.domain.TestAnnotation;
import org.synthe.ridill.scenario.domain.TestArray;
import org.synthe.ridill.scenario.domain.TestCollections;
import org.synthe.ridill.scenario.domain.TestEmbed;
import org.synthe.ridill.scenario.domain.TestEntity;
import org.synthe.ridill.scenario.domain.TestEnum;
import org.synthe.ridill.scenario.domain.TestInterface;
import org.synthe.ridill.scenario.domain.TestLocalClass;
import org.synthe.ridill.scenario.domain.TestPrimitive;

public class ScenarioStubFactoryTest {
	class ValueGeneratorForTesting implements ValueGenerator{
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
	
	@SuppressWarnings("unchecked")
	private List<Enum<?>> toEnumList(Class<?> e){
		return (List<Enum<?>>)Arrays.asList(e.getEnumConstants());
	}
	
	private <T> List<?> toList(T[] t){
		return Arrays.asList(t);
	}
	
	@Test
	public void testPrimitive(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestPrimitive primitive = test.returnTestPrimitive();

		boolean booleanActual = primitive.isFieldBoolean();
		boolean booleanExpected = generator.getBoolean(null);
		assertThat(booleanActual, is(equalTo(booleanExpected)));
		
		byte byteActual = primitive.getFieldByte();
		byte byteExpected = generator.getByte(null);
		assertThat(byteActual, is(equalTo(byteExpected)));
		
		char charActual = primitive.getFieldCharacter();
		char charExpected = generator.getCharacter(null);
		assertThat(charActual, is(equalTo(charExpected)));
		
		short shortActual = primitive.getFieldShort();
		short shortExpected = generator.getShort(null);
		assertThat(shortActual, is(equalTo(shortExpected)));
		
		int intActual = primitive.getFieldInteger();
		int intExpected = generator.getInteger(null);
		assertThat(intActual, is(equalTo(intExpected)));

		long longActual = primitive.getFieldLong();
		long longExpected = generator.getLong(null);
		assertThat(longActual, is(equalTo(longExpected)));

		float floatActual = primitive.getFieldFloat();
		float floatExpected = generator.getFloat(null);
		assertThat(floatActual, is(equalTo(floatExpected)));
		
		double doubleActual = primitive.getFieldDouble();
		double doubleExpected = generator.getDouble(null);
		assertThat(doubleActual, is(equalTo(doubleExpected)));
	}
	
	@Test
	public void testEmbed(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestEmbed embed = test.returnTestEmbed();

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
	public void testEnum(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestEnum enumeration = test.returnTestEnum();

		TestEnum enumActual = enumeration;
		TestEnum enumExpected = (TestEnum)generator.getEnum(null, toEnumList(TestEnum.class));
		assertThat(enumActual, is(equalTo(enumExpected)));
	}
	
	@Test
	public void testEntity(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestEntity entity = test.returnTestEntity();
		
		TestEmbed embed = entity.getDomain1();

		Boolean booleanActual1 = embed.getFieldBoolean();
		Boolean booleanExpected1 = generator.getBoolean(null);
		assertThat(booleanActual1, is(equalTo(booleanExpected1)));
		
		Byte byteActual1 = embed.getFieldByte();
		Byte byteExpected1 = generator.getByte(null);
		assertThat(byteActual1, is(equalTo(byteExpected1)));
		
		Character charActual1 = embed.getFieldCharacter();
		Character charExpected1 = generator.getCharacter(null);
		assertThat(charActual1, is(equalTo(charExpected1)));
		
		Short shortActual1 = embed.getFieldShort();
		Short shortExpected1 = generator.getShort(null);
		assertThat(shortActual1, is(equalTo(shortExpected1)));
		
		Integer intActual1 = embed.getFieldInteger();
		Integer intExpected1 = generator.getInteger(null);
		assertThat(intActual1, is(equalTo(intExpected1)));

		Long longActual1 = embed.getFieldLong();
		Long longExpected1 = generator.getLong(null);
		assertThat(longActual1, is(equalTo(longExpected1)));

		Float floatActual1 = embed.getFieldFloat();
		Float floatExpected1 = generator.getFloat(null);
		assertThat(floatActual1, is(equalTo(floatExpected1)));
		
		Double doubleActual1 = embed.getFieldDouble();
		Double doubleExpected1 = generator.getDouble(null);
		assertThat(doubleActual1, is(equalTo(doubleExpected1)));
		
		TestPrimitive primitive = entity.getDomain2();

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
	public void testAbstract(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		//TODO impl
	}
	
	@Test
	public void testAnnotation(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestAnnotation annotation = test.returnTestAnnotation();
		
		String actual = annotation.annotation();
		String expected = generator.getString(null);
		assertThat(actual, is(equalTo(expected)));
	}
	
	@Test
	public void testLocalClass(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestLocalClass localClass = test.returnTestLocalClass();
		
		String actual = localClass.getString();
		String expected = generator.getString(null);
		assertThat(actual, is(equalTo(expected)));
		
		String innerActual = localClass.getLocal().getInnerString();
		String innerExpected = generator.getString(null);
		assertThat(innerActual, is(equalTo(innerExpected)));
	}
	
	@Test
	public void testAnonymous(){
		//nothing to do
	}
	
	@Test
	public void testMemberClass(){
		//nothing to do
	}
	
	@Test
	public void testCollections(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestCollections collections = test.returnTestCollections();

		assertThat(collections.getString().size(), is(equalTo(generator.getCollectionSize(null))));
		collections.getString().forEach((instance)->{
			assertThat(instance, is(equalTo(generator.getString(null))));
		});
		
		assertThat(collections.getDomain().size(), is(equalTo(generator.getCollectionSize(null))));
		collections.getDomain().forEach((embed)->{
			Boolean booleanActual1 = embed.getFieldBoolean();
			Boolean booleanExpected1 = generator.getBoolean(null);
			assertThat(booleanActual1, is(equalTo(booleanExpected1)));
			
			Byte byteActual1 = embed.getFieldByte();
			Byte byteExpected1 = generator.getByte(null);
			assertThat(byteActual1, is(equalTo(byteExpected1)));
			
			Character charActual1 = embed.getFieldCharacter();
			Character charExpected1 = generator.getCharacter(null);
			assertThat(charActual1, is(equalTo(charExpected1)));
			
			Short shortActual1 = embed.getFieldShort();
			Short shortExpected1 = generator.getShort(null);
			assertThat(shortActual1, is(equalTo(shortExpected1)));
			
			Integer intActual1 = embed.getFieldInteger();
			Integer intExpected1 = generator.getInteger(null);
			assertThat(intActual1, is(equalTo(intExpected1)));

			Long longActual1 = embed.getFieldLong();
			Long longExpected1 = generator.getLong(null);
			assertThat(longActual1, is(equalTo(longExpected1)));

			Float floatActual1 = embed.getFieldFloat();
			Float floatExpected1 = generator.getFloat(null);
			assertThat(floatActual1, is(equalTo(floatExpected1)));
			
			Double doubleActual1 = embed.getFieldDouble();
			Double doubleExpected1 = generator.getDouble(null);
			assertThat(doubleActual1, is(equalTo(doubleExpected1)));
		});
		
		
		assertThat(collections.getListString().size(), is(equalTo(generator.getCollectionSize(null))));
		collections.getListString().forEach((i)->{
			assertThat(i.size(), is(equalTo(generator.getCollectionSize(null))));
			i.forEach((j)->{
				assertThat(j, is(equalTo(generator.getString(null))));
			});
		});
		
		assertThat(collections.getNestDomain().size(), is(equalTo(generator.getCollectionSize(null))));
		collections.getNestDomain().forEach((entity)->{
			TestEmbed embed = entity.getDomain1();

			Boolean booleanActual1 = embed.getFieldBoolean();
			Boolean booleanExpected1 = generator.getBoolean(null);
			assertThat(booleanActual1, is(equalTo(booleanExpected1)));
			
			Byte byteActual1 = embed.getFieldByte();
			Byte byteExpected1 = generator.getByte(null);
			assertThat(byteActual1, is(equalTo(byteExpected1)));
			
			Character charActual1 = embed.getFieldCharacter();
			Character charExpected1 = generator.getCharacter(null);
			assertThat(charActual1, is(equalTo(charExpected1)));
			
			Short shortActual1 = embed.getFieldShort();
			Short shortExpected1 = generator.getShort(null);
			assertThat(shortActual1, is(equalTo(shortExpected1)));
			
			Integer intActual1 = embed.getFieldInteger();
			Integer intExpected1 = generator.getInteger(null);
			assertThat(intActual1, is(equalTo(intExpected1)));

			Long longActual1 = embed.getFieldLong();
			Long longExpected1 = generator.getLong(null);
			assertThat(longActual1, is(equalTo(longExpected1)));

			Float floatActual1 = embed.getFieldFloat();
			Float floatExpected1 = generator.getFloat(null);
			assertThat(floatActual1, is(equalTo(floatExpected1)));
			
			Double doubleActual1 = embed.getFieldDouble();
			Double doubleExpected1 = generator.getDouble(null);
			assertThat(doubleActual1, is(equalTo(doubleExpected1)));
			
			TestPrimitive primitive = entity.getDomain2();

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
			
			String stringActual = entity.getString();
			String stringExpected = generator.getString(null);
			assertThat(stringActual, is(equalTo(stringExpected)));
			
			Integer integerActual = entity.getInteger();
			Integer integerExpected = generator.getInteger(null);
			assertThat(integerActual, is(equalTo(integerExpected)));

			TestEnum enumActual = entity.getFieldEnum();
			TestEnum enumExpected = (TestEnum)generator.getEnum(null, toEnumList(TestEnum.class));
			assertThat(enumActual, is(equalTo(enumExpected)));
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testArray(){
		ValueGeneratorForTesting generator = new ValueGeneratorForTesting();
		StubFactory factory = new StubFactory();
		TestInterface test = factory.create(generator, TestInterface.class);
		TestArray instance = test.returnTestArray();
		
		
		assertThat(instance.getIntegerArray1().length, is(equalTo(generator.getCollectionSize(null))));
		toList(instance.getIntegerArray1()).forEach((i)->{
			assertThat((Integer)i, is(equalTo(generator.getInteger(null))));
		});
		
		assertThat(instance.getIntegerArray2().length, is(equalTo(generator.getCollectionSize(null))));
		toList(instance.getIntegerArray2()).forEach((i)->{
			Integer[] i2 = (Integer[])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((i3)->{
				assertThat((Integer)i3, is(equalTo(generator.getInteger(null))));
			});
		});
		
		assertThat(instance.getStringArray1().length, is(equalTo(generator.getCollectionSize(null))));
		toList(instance.getStringArray1()).forEach((i)->{
			assertThat((String)i, is(equalTo(generator.getString(null))));
		});
		
		assertThat(instance.getStringArray2().length, is(equalTo(generator.getCollectionSize(null))));
		toList(instance.getStringArray2()).forEach((i)->{
			String[] i2 = (String[])i;
			assertThat(i2.length, is(equalTo(generator.getCollectionSize(null))));
			toList(i2).forEach((i3)->{
				assertThat((String)i3, is(equalTo(generator.getString(null))));
			});
		});
		
		assertThat(instance.getListStringArray1().length, is(equalTo(generator.getCollectionSize(null))));
		toList(instance.getListStringArray1()).forEach((i)->{
			List<String> i2 = (List<String>)i;
			assertThat(i2.size(), is(equalTo(generator.getCollectionSize(null))));
			i2.forEach((i3)->{
				assertThat((String)i3, is(equalTo(generator.getString(null))));
			});
		});
	}
}
