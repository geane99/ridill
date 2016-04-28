package org.synthe.ridill.stub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.synthe.ridill.generate.ExtValueGenerator;
import org.synthe.ridill.generate.ValueGenerator;
import org.synthe.ridill.reflect.ReflectService;

/**
 * InvocationHandler implementation.
 * see {@link InvocationHandler}
 * @since 2015/01/18
 * @author masahiko.ootsuki
 * @version 1.0.0
 */
class StubProxy implements InvocationHandler{
	/**
	 * {@link ClassLoader}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private ClassLoader _loader;
	
	/**
	 * see {@link ReflectService}
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	private ReflectService _service;
	
	/**
	 * constructor to receive the {@link ValueGenerator} argument.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ValueGenerator}
	 */
	public StubProxy(ClassLoader loader, ValueGenerator generator){
		_loader = loader;
		InternalGenerator _g = new InternalGenerator(generator);
		_service = new ReflectService(_loader, _g);
	}
	/**
	 * constructor to receive the {@link ExtValueGenerator} argument.
	 * @since 2015/01/18
	 * @version 1.0.0
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ExtValueGenerator}
	 */
	public StubProxy(ClassLoader loader, ExtValueGenerator generator){
		_loader = loader;
		InternalGenerator _g = new InternalGenerator(generator);
		_service = new ReflectService(_loader, _g);
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return _service.reflect(proxy, method, args);
	}
}
