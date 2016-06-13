package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ProxyTemplate extends ClassTemplate implements InvocationHandler{
	private ReflectAdapter _adapter;
	private Integer _depth;
	private ClassLoader _loader;
	private Class<?>[] _interfaces;
	
	public ProxyTemplate(ReflectAdapter adapter, Integer depth, ClassLoader loader, Template template){
		_adapter = adapter;
		_depth = depth;
		_loader = loader;
		_interfaces = template.interfaces();
		real(template);
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ReflectService obj = new ReflectService(_loader, _adapter);
		return obj.reflect(proxy, method, _depth, args);
	}
	public Class<?> findDefineByMethod(Method m){
		if(_interfaces == null)
			return null;
		for(Class<?> clazz : _interfaces){
			try{
				Method r = clazz.getMethod(m.getName(), m.getParameterTypes());
				if(r != null)
					return clazz;
			}
			catch(Throwable t){
			}
			try{
				Method r = clazz.getDeclaredMethod(m.getName(), m.getParameterTypes());
				if(r != null)
					return clazz;
			}
			catch(Throwable t){
			}
		}
		return null;
	}
}
