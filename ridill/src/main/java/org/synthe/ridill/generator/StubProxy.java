package org.synthe.ridill.generator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.synthe.ridill.reflect.Reflect;

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
	 * @author masahiko.ootsuki
	 * @version 1.0.0
	 */
	private ClassLoader _loader;
	/**
	 * see {@link ValueGenerator}
	 * @since 2015/01/18
	 * @author masahiko.ootsuki
	 * @version 1.0.0
	 */
	private ValueGenerator _generator;
	/**
	 * see {@link ExtValueGenerator}
	 * @since 2015/01/18
	 * @author masahiko.ootsuki
	 * @version 1.0.0
	 */
	private ExtValueGenerator _extGenerator;
	/**
	 * constructor to receive the {@link ValueGenerator} argument.
	 * @since 2015/01/18
	 * @author masahiko.ootsuki
	 * @version 1.0.0
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ValueGenerator}
	 */
	public StubProxy(ClassLoader loader, ValueGenerator generator){
		_loader = loader;
		_generator = generator;
	}
	/**
	 * constructor to receive the {@link ExtValueGenerator} argument.
	 * @since 2015/01/18
	 * @author masahiko.ootsuki
	 * @version 1.0.0
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ExtValueGenerator}
	 */
	public StubProxy(ClassLoader loader, ExtValueGenerator generator){
		_loader = loader;
		_extGenerator = generator;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		InternalGenerator generator = _extGenerator != null ? 
			new InternalGenerator(_extGenerator):
			new InternalGenerator(_generator);
		Reflect obj = new Reflect(_loader, generator);
		return obj.reflect(proxy, method, args);
	}
}
