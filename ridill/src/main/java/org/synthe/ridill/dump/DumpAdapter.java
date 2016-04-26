package org.synthe.ridill.dump;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.synthe.ridill.reflect.ReflectAdapter;
import org.synthe.ridill.reflect.ClassInfo;

public class DumpAdapter implements ReflectAdapter{
	@Override
	public Object getEmbedValue(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectValue(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getEnumValue(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Map<?, ?>> T getMap(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends List<?>> T getList(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Set<?>> T getSet(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Collection<?>> T getCollection(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Queue<?>> T getQueue(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCollectionSize(ClassInfo info, Object enclosingInstance, Integer depth) {
		// TODO Auto-generated method stub
		return null;
	}
}
