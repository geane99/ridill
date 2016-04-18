package org.synthe.ridill.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.synthe.ridill.reflect.ReflectionObject;

public class StubProxy implements InvocationHandler{
	/* --------------------------------------------------------- */
	/* properties                                                */
	/* --------------------------------------------------------- */
	private ClassLoader _loader;
	private ValueGenerator _generator;
	private ExtValueGenerator _extGenerator;
	
	/* --------------------------------------------------------- */
	/* constructors & init method                                */
	/* --------------------------------------------------------- */
	public StubProxy(ClassLoader loader, ValueGenerator generator){
		_loader = loader;
		_generator = generator;
	}
	
	public StubProxy(ClassLoader loader, ExtValueGenerator generator){
		_loader = loader;
		_extGenerator = generator;
	}

	/* --------------------------------------------------------- */
	/* implemant InvocationHandler                               */
	/* --------------------------------------------------------- */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ReflectionObject obj = _extGenerator != null ? 
			new ReflectionObject(_loader, _extGenerator) :
			new ReflectionObject(_loader, _generator);
		return obj.reflect(proxy, method, args);
	}

}
