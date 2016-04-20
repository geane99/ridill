package org.synthe.ridill.generator;

import org.synthe.ridill.reflect.Adapter;
import org.synthe.ridill.reflect.ReflectionInfo;

class GeneratorAdapter implements Adapter{
	private InternalGenerator _generator;
	public GeneratorAdapter(InternalGenerator generator){
		_generator = generator;
	}
	@Override
	public Object getEmbedValue(ReflectionInfo info, Object enclosingInstance, Integer depth) {
		return _generator.getEmbedValue(info, enclosingInstance);
	}

	@Override
	public Object getObjectValue(ReflectionInfo info, Object enclosingInstance, Integer depth) {
		return _generator.getObjectValue(info, enclosingInstance);
	}

	@Override
	public Object getEnumValue(ReflectionInfo info, Object enclosingInstance, Integer depth) {
		return _generator.getEnumValue(info, enclosingInstance);
	}
}
