package org.synthe.ridill.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.synthe.ridill.TargetInfo;

class FieldTemplate extends PropertyTemplate{
	private Field _field;
	public FieldTemplate(Field method){
		_field = method;
		setAccessSecury(_field);
	}
	
	@Override
	public String propertyName() {
		return _field.getName();
	}

	@Override
	public void set(Object instance, Object value) throws IllegalAccessException {
		_field.set(instance, value);
	}

	@Override
	public Object get(Object instance) throws IllegalAccessException {
		return _field.get(instance);
	}

	@Override
	public Object invoke(Object instance, Object... args) throws InvocationTargetException, IllegalAccessException{
		//nothing to do
		return null;
	}

	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return new FieldTargetInfo(this,instance);
	}
	
	class FieldTargetInfo implements TargetInfo{
		private FieldTemplate _info;
		private Object _instance;
		private FieldTargetInfo(FieldTemplate info, Object instance){
			_info = info;
			_instance = instance;
		}
		
		@Override
		public String name() {
			return _info.propertyName();
		}

		@Override
		public String classname() {
			return _info.templateName();
		}

		@Override
		public String eclosingClassName() {
			return _info.enclosing().templateName();
		}

		@Override
		public Boolean isProperty() {
			return false;
		}

		@Override
		public Boolean isMethod() {
			return true;
		}

		@Override
		public Object target() {
			return _instance;
		}
	}

}
