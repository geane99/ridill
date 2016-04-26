package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Reflect {
	
	private Map<ClassType, InternalAdapterStrategy> _factoryCache;
	private _Cache _rcache;
	private ClassLoader _loader;
	private ReflectAdapter _adapter;
	
	
	public Reflect(ClassLoader loader, ReflectAdapter adapter){
		_loader = loader;
		_adapter = adapter;
		_initialize();
	}
	
	private void _initialize(){
		_factoryCache = new HashMap<>();
		_factoryCache.put(ClassType.objectType, new _ObjectStrategy());
		_EmbedStrategy primitiveFactory = new _EmbedStrategy();
		_factoryCache.put(ClassType.byteType, primitiveFactory);
		_factoryCache.put(ClassType.booleanType, primitiveFactory);
		_factoryCache.put(ClassType.floatType, primitiveFactory);
		_factoryCache.put(ClassType.doubleType, primitiveFactory);
		_factoryCache.put(ClassType.shortType, primitiveFactory);
		_factoryCache.put(ClassType.integerType, primitiveFactory);
		_factoryCache.put(ClassType.longType, primitiveFactory);
		_factoryCache.put(ClassType.characterType, primitiveFactory);
		_factoryCache.put(ClassType.stringType, primitiveFactory);
		_factoryCache.put(ClassType.enumType, new _EnumStrategy());
		_ProxyStrategy proxyFactory = new _ProxyStrategy();
		_factoryCache.put(ClassType.interfaceType,proxyFactory);
		_factoryCache.put(ClassType.annotationType, proxyFactory);
		_factoryCache.put(ClassType.abstractType, new _AbstractStrategy());
		_factoryCache.put(ClassType.listType, new _ListStrategy());
		_factoryCache.put(ClassType.setType,  new _SetStrategy());
		_factoryCache.put(ClassType.queueType, new _QueueStrategy());
		_factoryCache.put(ClassType.mapType, new _DictionaryStrategy());
		_factoryCache.put(ClassType.arrayType, new _ArrayStrategy());
		_factoryCache.put(ClassType.domainType, new _DomainStrategy());
		_rcache = new _Cache();
	}
	
	public Object reflect(Object proxy, Method method, Object[] args){
		return reflect(proxy, method, 0, args);
	}
	
	public Object reflect(Object instance, Method method, Integer depth, Object...args){
		Class<?> target = method.getReturnType();
		ReflectInfo info = !_rcache.has(target) ?
			new ReflectInfo(TemplateFactory.createByReturnType(method, instance, args)):
			_rcache.get(target);
		return _reflect(info, _adapter, instance, depth);
	}

	
	/* --------------------------------------------------------- */
	/* private method                                            */
	/* --------------------------------------------------------- */
	private Object _reflect(ReflectInfo info, ReflectAdapter adapter, Object instance, Integer depth){
		return _selectFactory(info).command(info, adapter, instance, depth);
	}

	private InternalAdapterStrategy _selectFactory(ReflectInfo info){
		return _factoryCache.get(info.classType());
	}
	
	private void handlingError(Throwable t){
		t.printStackTrace();
	}
	
	/* --------------------------------------------------------- */
	/* strategy for create object & set value                    */
	/* --------------------------------------------------------- */
	/**
	 * It is InternalGeneratorStrategy for performing the process of {@link ClassType} by {@link ReflectAdapter}
	 * @author masahiko.ootsuki
	 * @since 2015/01/18
	 * @version 1.0.0
	 */
	interface InternalAdapterStrategy {
		/**
		 * Generate a value from the argument
		 * @param info {@link ReflectInfo}
		 * @param adapter {@link ReflectAdapter}
		 * @param instance instance of enclosing class
		 * @param depth depth of recursive processing
		 * @return generated value
		 */
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object instance, Integer depth);
	}
	
	
	class _DomainStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			try{
				Object target = info.newInstance();
				for(ReflectInfo each : info.properties()){
					if(each.isImmutable())
						continue;
					Object val = _reflect(each,adapter,target, depth + 1);
					try{
						each.set(target, val);
					}
					catch(IllegalAccessException iae){
						handlingError(iae);
					}
				}
				return target;
			}
			catch(IllegalAccessException e){
				return null;
			}
			catch(InstantiationException e){
				return null;
			}
		}
	}
	
	class _ArrayStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			try{
				Object[] arrays = (Object[])info.newInstance();
				for(int i = 0; i < arrays.length; i++)
					//TODO impl
					arrays[i] = _reflect(info, adapter, enclosingInstance, depth + 1);
				return arrays;
			}
			catch(IllegalAccessException e){
				return null;
			}
			catch(InstantiationException e){
				return null;
			}
		}
	}
	
	class _ProxyStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return Proxy.newProxyInstance(
				_loader, 
				info.interfaces(), 
				new _InnerInvocationHandlerImpl(adapter, depth)
			);
		}
		
		class _InnerInvocationHandlerImpl implements InvocationHandler{
			private ReflectAdapter __adapter;
			private Integer __depth;
			_InnerInvocationHandlerImpl(ReflectAdapter adapter, Integer depth){
				__adapter = adapter;
				__depth = depth;
			}
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Reflect obj = new Reflect(_loader, __adapter);
				return obj.reflect(proxy, method, __depth, args);
			}
		}
	}
	
	class _AbstractStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			try{
				return info.newInstance();
			}
			catch(IllegalAccessException e){
				return null;
			}
			catch(InstantiationException e){
				return null;
			}
		}
	}
	
	class _EnumStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getEnumValue(info,enclosingInstance,depth);
		}
	}
	
	class _ObjectStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getObjectValue(info,enclosingInstance,depth);
		}
	}
	
	class _EmbedStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getEmbedValue(info,enclosingInstance,depth);
		}
	}
		
	class _DictionaryStrategy implements InternalAdapterStrategy{
		@SuppressWarnings("unchecked")
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			Map<Object,Object> collection = (Map<Object,Object>)adapter.getMap(info, enclosingInstance, depth);

			int size = adapter.getCollectionSize(info,enclosingInstance,depth);
			
			ReflectInfo typeKeyParamInfo = info.typeParameterAt(0);
			ReflectInfo typeValueParamInfo = info.typeParameterAt(1);
			for(int idx = 0; idx < size; idx++){
				Object key = _reflect(typeKeyParamInfo, adapter, enclosingInstance, depth+1);
				Object value = _reflect(typeValueParamInfo, adapter, enclosingInstance, depth+1);
				collection.put(key, value);
			}
			
			return collection;
		}
	}

	class _QueueStrategy extends _LinearCollectionStrategy<Queue<?>>{
		@Override
		Queue<?> instance(ReflectInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth) {
			return adapter.getQueue(info, enclosingInstance, depth);
		}
	}
	
	class _SetStrategy extends _LinearCollectionStrategy<Set<?>>{
		@Override
		Set<?> instance(ReflectInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth) {
			return adapter.getSet(info, enclosingInstance, depth);
		}
	}
	
	class _ListStrategy extends _LinearCollectionStrategy<List<?>>{
		@Override
		List<?> instance(ReflectInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth) {
			return adapter.getList(info, enclosingInstance, depth);
		}
	}
	
	abstract class _LinearCollectionStrategy<T extends Collection<?>> implements InternalAdapterStrategy{
		abstract T instance(ReflectInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth);
		@SuppressWarnings("unchecked")
		@Override
		public Object command(ReflectInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			Collection<Object> collection = (Collection<Object>)instance(info, enclosingInstance, adapter, depth);
			
			int size = adapter.getCollectionSize(info,enclosingInstance, depth);
			
			ReflectInfo typeParamInfo = info.typeParameterAt(0);
			for(int idx = 0; idx < size; idx++)
				collection.add(_reflect(typeParamInfo, adapter, adapter, depth+1));
			
			return collection;
		}
	}
	
	/* --------------------------------------------------------- */
	/* reflection cache mecanism                                 */
	/* --------------------------------------------------------- */
	class _Cache{
		volatile Map<String,ReflectInfo> _store;
		public _Cache(){
			_store = new HashMap<>();
		}
		public boolean has(Class<?> clazz){
			return _store.containsKey(clazz.getName());
		}
		public ReflectInfo get(Class<?> clazz){
			return _store.get(clazz.getName());
		}
		public void set(ReflectInfo info){
			_store.put(info.className(), info);
		}
	}	
}
