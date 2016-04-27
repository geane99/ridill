package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ReflectService {
	private Map<ClassType, InternalAdapterStrategy> _factoryCache;
	private _Cache _rcache;
	private ClassLoader _loader;
	private ReflectAdapter _adapter;
	private TemplateFactory _templateFactory = new TemplateFactory();
	
	
	public ReflectService(ClassLoader loader, ReflectAdapter adapter){
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
		_factoryCache.put(ClassType.collectionType, new _ListStrategy());
		_factoryCache.put(ClassType.setType,  new _SetStrategy());
		_factoryCache.put(ClassType.queueType, new _QueueStrategy());
		_factoryCache.put(ClassType.mapType, new _DictionaryStrategy());
		_factoryCache.put(ClassType.arrayType, new _ArrayStrategy());
		_factoryCache.put(ClassType.domainType, new _DomainStrategy());
		_factoryCache.put(ClassType.typeVariable, new _ObjectStrategy());
		_rcache = new _Cache();
	}
	
	public Object reflect(Object proxy, Method method, Object[] args){
		return reflect(proxy, method, 0, args);
	}
	
	public Object reflect(Object instance, Method method, Integer depth, Object...args){
		Class<?> target = method.getReturnType();
		ClassInfo info = !_rcache.has(target) ?
			_templateFactory.createByReturnType(method, instance, args).toClassInfo():
			_rcache.get(target);
		return _reflect(info, _adapter, instance, depth);
	}

	
	/* --------------------------------------------------------- */
	/* private method                                            */
	/* --------------------------------------------------------- */
	private Object _reflect(ClassInfo info, ReflectAdapter adapter, Object instance, Integer depth){
		return _selectFactory(info).command(info, adapter, instance, depth);
	}

	private InternalAdapterStrategy _selectFactory(ClassInfo info){
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
		 * @param info {@link ClassInfo}
		 * @param adapter {@link ReflectAdapter}
		 * @param instance instance of enclosing class
		 * @param depth depth of recursive processing
		 * @return generated value
		 */
		public Object command(ClassInfo info, ReflectAdapter adapter, Object instance, Integer depth);
	}
	
	
	class _DomainStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			try{
				Object target = info.newInstance();
				for(ClassInfo each : info.properties()){
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
			catch(InvocationTargetException e){
				return null;
			}
		}
	}
	
	class _ArrayStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			Integer[] size = new Integer[info.dimensions()];
			for(int i = 0; i < size.length; i++)
				size[i] = adapter.getCollectionSize(info, enclosingInstance, depth+i);
			
			Object[] arrays = info.componentNewInstance(size);
			
			ClassInfo typeParam = info.typeParameterAt(0);
			return processArray(arrays, typeParam, adapter, depth);
		}
		
		private Object processArray(Object[] array, ClassInfo typeParam, ReflectAdapter adapter, Integer depth){
			for(int i = 0; i < array.length; i++){
				array[i] = isArray(array[i]) ?
					processArray((Object[])array[i], typeParam, adapter, depth + 1) :
					_reflect(typeParam, adapter, array, depth + 1);
			}
			return array;
		}
		private Boolean isArray(Object target){
			return target instanceof Object[];
		}
	}
	
	class _ProxyStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
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
				ReflectService obj = new ReflectService(_loader, __adapter);
				return obj.reflect(proxy, method, __depth, args);
			}
		}
	}
	
	class _AbstractStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			try{
				return info.newInstance();
			}
			catch(IllegalAccessException e){
				return null;
			}
			catch(InstantiationException e){
				return null;
			}
			catch(InvocationTargetException e){
				return null;
			}
		}
	}
	
	class _EnumStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getEnumValue(info,enclosingInstance,depth);
		}
	}
	
	class _ObjectStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getObjectValue(info,enclosingInstance,depth);
		}
	}
	
	class _EmbedStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getEmbedValue(info,enclosingInstance,depth);
		}
	}
		
	class _DictionaryStrategy implements InternalAdapterStrategy{
		@SuppressWarnings("unchecked")
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			Map<Object,Object> collection = (Map<Object,Object>)adapter.getMap(info, enclosingInstance, depth);

			int size = adapter.getCollectionSize(info,enclosingInstance,depth);
			
			ClassInfo typeKeyParamInfo = info.typeParameterAt(0);
			ClassInfo typeValueParamInfo = info.typeParameterAt(1);
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
		Queue<?> instance(ClassInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth) {
			return adapter.getQueue(info, enclosingInstance, depth);
		}
	}
	
	class _SetStrategy extends _LinearCollectionStrategy<Set<?>>{
		@Override
		Set<?> instance(ClassInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth) {
			return adapter.getSet(info, enclosingInstance, depth);
		}
	}
	
	class _ListStrategy extends _LinearCollectionStrategy<List<?>>{
		@Override
		List<?> instance(ClassInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth) {
			return adapter.getList(info, enclosingInstance, depth);
		}
	}
	
	abstract class _LinearCollectionStrategy<T extends Collection<?>> implements InternalAdapterStrategy{
		abstract T instance(ClassInfo info, Object enclosingInstance, ReflectAdapter adapter, Integer depth);
		@SuppressWarnings("unchecked")
		@Override
		public Object command(ClassInfo info, ReflectAdapter adapter, Object enclosingInstance, Integer depth) {
			Collection<Object> collection = (Collection<Object>)instance(info, enclosingInstance, adapter, depth);
			
			int size = adapter.getCollectionSize(info,enclosingInstance, depth);
			
			ClassInfo typeParamInfo = info.typeParameterAt(0);
			for(int idx = 0; idx < size; idx++)
				collection.add(_reflect(typeParamInfo, adapter, collection, depth+1));
			
			return collection;
		}
	}
	
	/* --------------------------------------------------------- */
	/* reflection cache mecanism                                 */
	/* --------------------------------------------------------- */
	class _Cache{
		volatile Map<String,ClassInfo> _store;
		public _Cache(){
			_store = new HashMap<>();
		}
		public boolean has(Class<?> clazz){
			return _store.containsKey(clazz.getName());
		}
		public ClassInfo get(Class<?> clazz){
			return _store.get(clazz.getName());
		}
		public void set(ClassInfo info){
			_store.put(info.className(), info);
		}
	}	
}
