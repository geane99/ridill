package org.synthe.ridill.generate;

import java.util.List;

import org.synthe.ridill.stub.TargetInfo;

public class DefaultValueGenerator implements ValueGenerator{
	@Override
	public Byte getByte(TargetInfo info) {
		return 1;
	}

	@Override
	public Boolean getBoolean(TargetInfo info) {
		return true;
	}

	@Override
	public Float getFloat(TargetInfo info) {
		return (float)2.1;
	}

	@Override
	public Double getDouble(TargetInfo info) {
		return 3.1;
	}

	@Override
	public Short getShort(TargetInfo info) {
		return 1;
	}

	@Override
	public Integer getInteger(TargetInfo info) {
		return 2;
	}

	@Override
	public Long getLong(TargetInfo info) {
		return (long)3;
	}

	@Override
	public Character getCharacter(TargetInfo info) {
		return 'c';
	}

	@Override
	public String getString(TargetInfo info) {
		return "string";
	}

	@Override
	public Integer getCollectionSize(TargetInfo info) {
		return 4;
	}

	@Override
	public Enum<?> getEnum(TargetInfo info, List<Enum<?>> enums) {
		return enums.get(0);
	}

}
