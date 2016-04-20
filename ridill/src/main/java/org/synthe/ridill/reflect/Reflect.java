package org.synthe.ridill.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Reflect {
	
	private Map<ClassType, InternalAdapterStrategy> _factoryCache;
	private _Cache _rcache;
	private ClassLoader _loader;
	private Adapter _adapter;
	
	
	public Reflect(ClassLoader loader, Adapter adapter){
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
//		_factoryCache.put(ClassType.listType, new _ListStrategy());
//		_factoryCache.put(ClassType.setType,  new _SetStrategy());
//		_factoryCache.put(ClassType.queueType, new _QueueStrategy());
//		_factoryCache.put(ClassType.mapType, new _DictionaryStrategy());
		_factoryCache.put(ClassType.arrayType, new _ArrayStrategy());
		_factoryCache.put(ClassType.domainType, new _DomainStrategy());
		_rcache = new _Cache();
	}
	
	public Object reflect(Object proxy, Method method, Object[] args){
		return reflect(proxy, method, 0, args);
	}
	
	public Object reflect(Object instance, Method method, Integer depth, Object...args){
		Class<?> target = method.getReturnType();
		ReflectionInfo info = !_rcache.has(target) ?
			ReflectionInfoFactory.returnType(instance, method, args):
			_rcache.get(target);
		return _reflect(info, _adapter, instance, depth);
	}

	
	/* --------------------------------------------------------- */
	/* private method                                            */
	/* --------------------------------------------------------- */
	private Object _reflect(ReflectionInfo info, Adapter adapter, Object instance, Integer depth){
		return _selectFactory(info).command(info, adapter, instance, depth);
	}

	private InternalAdapterStrategy _selectFactory(ReflectionInfo info){
		return _factoryCache.get(info.classType());
	}
	
	private void handlingError(Throwable t){
		t.printStackTrace();
	}
	
	/* --------------------------------------------------------- */
	/* strategy for create object & set value                    */
	/* --------------------------------------------------------- */
	
	class _DomainStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			Object target = info.newInstance();
			for(ReflectionInfo each : ReflectionInfoFactory.fieldTypeAll(info)){
				if(each.isImmutable())
					continue;
				Object val = _reflect(each,adapter,target, depth + 1);
				try{
					info.set(target, val);
				}
				catch(IllegalAccessException iae){
					handlingError(iae);
				}
			}
			return target;
		}
	}
	
	class _ArrayStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			Object[] arrays = (Object[])info.newInstance();
			for(int i = 0; i < arrays.length; i++)
				//TODO impl
				arrays[i] = _reflect(info, adapter, enclosingInstance, depth + 1);
			return arrays;
		}
	}
	
	class _ProxyStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			return Proxy.newProxyInstance(
				_loader, 
				info.interfaces(), 
				new _InnerInvocationHandlerImpl(adapter, depth)
			);
		}
		
		class _InnerInvocationHandlerImpl implements InvocationHandler{
			private Adapter __adapter;
			private Integer __depth;
			_InnerInvocationHandlerImpl(Adapter adapter, Integer depth){
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
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			return info.newInstance();
		}
	}
	
	class _EnumStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getEnumValue(info,enclosingInstance,depth);
		}
	}
	
	class _ObjectStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getObjectValue(info,enclosingInstance,depth);
		}
	}
	
	class _EmbedStrategy implements InternalAdapterStrategy{
		@Override
		public Object command(ReflectionInfo info, Adapter adapter, Object enclosingInstance, Integer depth) {
			return adapter.getEmbedValue(info,enclosingInstance,depth);
		}
	}
		
//	
//	class _DictionaryStrategy implements _GenerateStrategy{
//		@SuppressWarnings("unchecked")
//		@Override
//		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
//			TargetInfo gi = info.getTargetInfo();
//			Map<Object,Object> collection = (Map<Object,Object>)adapter.generateMap(info, gi);
//
//			int size = adapter.generateCollectionSize(info,gi);
//			
//			ReflectionInfo typeKeyParamInfo = ReflectionInfoFactory.fieldTypeParameterType(info, 0);
//			ReflectionInfo typeValueParamInfo = ReflectionInfoFactory.fieldTypeParameterType(info, 1);
//			for(int idx = 0; idx < size; idx++){
//				Object key = _reflect(typeKeyParamInfo, adapter);
//				Object value = _reflect(typeValueParamInfo, adapter);
//				collection.put(key, value);
//			}
//			
//			return collection;
//		}
//	}
//
//	class _QueueStrategy extends _LinearCollectionStrategy<Queue<?>>{
//		@Override
//		Queue<?> newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter) {
//			return adapter.generateQueue(info, gi);
//		}
//	}
//	
//	class _SetStrategy extends _LinearCollectionStrategy<Set<?>>{
//		@Override
//		Set<?> newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter) {
//			return adapter.generateSet(info, gi);
//		}
//	}
//	
//	class _ListStrategy extends _LinearCollectionStrategy<List<?>>{
//		@Override
//		List<?> newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter) {
//			return adapter.generateList(info, gi);
//		}
//	}
//	
//	abstract class _LinearCollectionStrategy<T extends Collection<?>> implements _GenerateStrategy{
//		abstract T newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter);
//		@SuppressWarnings("unchecked")
//		@Override
//		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
//			TargetInfo gi = info.toTargetInfo();
//			Collection<Object> collection = (Collection<Object>)newInstance(info, gi, adapter);
//			
//			int size = adapter.generateCollectionSize(info,gi);
//			
//			//TODO impl
//			ReflectionInfo typeParamInfo = ReflectionInfoFactory.fieldTypeParameterType(info,0);
//			for(int idx = 0; idx < size; idx++)
//				collection.add(_reflect(typeParamInfo, adapter));
//			
//			return collection;
//		}
//	}
	
	/* --------------------------------------------------------- */
	/* reflection cache mecanism                                 */
	/* --------------------------------------------------------- */
	class _Cache{
		volatile Map<String,ReflectionInfo> _store;
		public _Cache(){
			_store = new HashMap<>();
		}
		public boolean has(Class<?> clazz){
			return _store.containsKey(clazz.getName());
		}
		public ReflectionInfo get(Class<?> clazz){
			return _store.get(clazz.getName());
		}
		public void set(ReflectionInfo info){
			_store.put(info.className(), info);
		}
	}	
}
