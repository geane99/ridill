package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.synthe.ridill.TargetInfo;

class MethodTemplate extends PropertyTemplate{
	private Method _method;
	public MethodTemplate(Method method){
		_method = method;
		setAccessSecury(_method);
	}
	
	@Override
	public String propertyName() {
		return _method.getName();
	}

	@Override
	public void set(Object instance, Object value) throws IllegalAccessException {
		//nothing to do
	}

	@Override
	public Object get(Object instance) throws IllegalAccessException {
		//nothing to do
		return null;
	}

	@Override
	public Object invoke(Object instance, Object... args) throws InvocationTargetException, IllegalAccessException{
		return _method.invoke(instance, args);
	}

	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return new MethodTargetInfo(this,instance);
	}
	
	class MethodTargetInfo implements TargetInfo{
		private MethodTemplate _info;
		private Object _instance;
		private MethodTargetInfo(MethodTemplate info, Object instance){
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
