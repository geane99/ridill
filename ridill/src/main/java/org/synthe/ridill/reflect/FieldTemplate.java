package org.synthe.ridill.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.synthe.ridill.stub.TargetInfo;

/**
 * Class that provides type information.<br/>
 * FieldTemplate provides {@link Field} information.
 * @author masahiko.ootsuki
 * @since 2015/01/18
 * @version 1.0.0
 */
class FieldTemplate extends PropertyTemplate{
	/**
	 * type of the field
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private Field _field;
	
	/**
	 * constructor
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param field {@link Field}
	 */
	public FieldTemplate(Field field, ClassTemplate template){
		super(template.template());
		override(template);
		_field = field;
		setAccessControl(_field);
		_templateType = TemplateType.property;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#propertyName()
	 */
	@Override
	public String propertyName() {
		return _field.getName();
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(Object instance, Object value) throws IllegalAccessException {
		_field.set(instance, value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#get(java.lang.Object)
	 */
	@Override
	public Object get(Object instance) throws IllegalAccessException {
		return _field.get(instance);
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#invoke(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object instance, Object... args) throws InvocationTargetException, IllegalAccessException{
		//nothing to do
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.synthe.ridill.reflect.PropertyTemplate#toTargetInfo(java.lang.Object)
	 */
	@Override
	public TargetInfo toTargetInfo(Object instance) {
		return new FieldTargetInfo(this,instance);
	}
	/**
	 * Implementation class of {@link TargetInfo}
	 * @author masahiko.ootsuki
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	class FieldTargetInfo implements TargetInfo{
		/**
		 * {@link FieldTemplate}
		 * @since 2015/01/18
		 * @version 1.0.0
		 */
		private FieldTemplate _info;
		/**
		 * instance of target class
		 * @since 2015/01/18
		 * @version 1.0.0
		 */
		private Object _instance;
		/**
		 * constructor
		 * @param info {@link FieldTemplate}
		 * @param instance instance of target class
		 */
		private FieldTargetInfo(FieldTemplate info, Object instance){
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
