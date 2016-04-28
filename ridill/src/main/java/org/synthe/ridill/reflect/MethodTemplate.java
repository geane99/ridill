package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.synthe.ridill.stub.TargetInfo;

/**
 * Class that provides type information.<br/>
 * MethodTemplate provides {@link Method} information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
class MethodTemplate extends PropertyTemplate{
	/**
	 * target method
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private Method _method;
	/**
	 * constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param method {@link Method}
	 * @param returnTemplate {@link ClassTemplate}
	 */
	public MethodTemplate(Method method, ClassTemplate returnTemplate){
		super(returnTemplate.template());
		_method = method;
		setAccessControl(_method);
		override(returnTemplate);
	}
	
	/**
	 * constructor 
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param method {@link Method}
	 * @param returnTemplate {@link TypeParameterTemplate}
	 */
	public MethodTemplate(Method method, TypeParameterTemplate returnTemplate){
		super(returnTemplate.template());
		_method = method;
		setAccessControl(_method);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#propertyName()
	 */
	@Override
	public String propertyName() {
		return _method.getName();
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(Object instance, Object value) throws IllegalAccessException {
		//nothing to do
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#get(java.lang.Object)
	 */
	@Override
	public Object get(Object instance) throws IllegalAccessException {
		//nothing to do
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#invoke(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object instance, Object... args) throws InvocationTargetException, IllegalAccessException{
		return _method.invoke(instance, args);
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#toTargetInfo(java.lang.Object)
	 */
	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return new MethodTargetInfo(this,instance);
	}
	
	/**
	 * Implementation class of {@link TargetInfo}
	 * @author masahiko.ootsuki
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	class MethodTargetInfo implements TargetInfo{
		/**
		 * {@link MethodTemplate}
		 * @since 2015/01/18
		 * @version 1.0.0
		 */
		private MethodTemplate _info;
		/**
		 * instance of target class
		 * @since 2015/01/18
		 * @version 1.0.0
		 */
		private Object _instance;
		/**
		 * constructor
		 * @param info {@link MethodTemplate}
		 * @param instance instance of target class
		 */
		private MethodTargetInfo(MethodTemplate info, Object instance){
			_info = info;
			_instance = instance;
		}
		/*
		 * (non-Javadoc)
		 * @see org.synthe.ridill.TargetInfo#name()
		 */
		@Override
		public String name() {
			return _info.propertyName();
		}
		/*
		 * (non-Javadoc)
		 * @see org.synthe.ridill.TargetInfo#classname()
		 */
		@Override
		public String classname() {
			return _info.templateName();
		}
		/*
		 * (non-Javadoc)
		 * @see org.synthe.ridill.TargetInfo#eclosingClassName()
		 */
		@Override
		public String enclosingClassName() {
			return _info.enclosing().templateName();
		}
		/*
		 * (non-Javadoc)
		 * @see org.synthe.ridill.TargetInfo#isProperty()
		 */
		@Override
		public Boolean isProperty() {
			return false;
		}
		/*
		 * (non-Javadoc)
		 * @see org.synthe.ridill.TargetInfo#isMethod()
		 */
		@Override
		public Boolean isMethod() {
			return true;
		}
		/*
		 * (non-Javadoc)
		 * @see org.synthe.ridill.TargetInfo#target()
		 */
		@Override
		public Object target() {
			return _instance;
		}
	}
}
