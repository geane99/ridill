package org.synthe.ridill.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.synthe.ridill.ExtValueGenerator;
import org.synthe.ridill.StubFactory;
import org.synthe.ridill.ValueGenerator;

public class ReflectionObject {
	
	private Map<ClassType, InternalGeneratorStrategy> _factoryCache;
	private _ReflectionCache _rcache;
	private ClassLoader _loader;
	private InternalGenerator _generator;
	private ValueGenerator _valueGenerator;
	private ExtValueGenerator _extValueGenerator;
	
	
	public ReflectionObject(ClassLoader loader, ValueGenerator generator){
		_loader = loader;
		_generator = new InternalGenerator(generator);
		_valueGenerator = generator;
		_initialize();
	}
	
	public ReflectionObject(ClassLoader loader, ExtValueGenerator generator){
		_loader = loader;
		_generator = new InternalGenerator(generator);
		_extValueGenerator = generator;
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
		_rcache = new _ReflectionCache();
	}
	
	public Object reflect(Object proxy, Method method, Object[] args){
		Class<?> target = method.getReturnType();
		ReflectionInfo info = !_rcache.has(target) ?
			ReflectionInfoFactory.returnType(proxy, method, args):
			_rcache.get(target);
		return _reflect(info, _generator, proxy);
	}

	
	/* --------------------------------------------------------- */
	/* private method                                            */
	/* --------------------------------------------------------- */
	private Object _reflect(ReflectionInfo info, InternalGenerator adapter, Object instance){
		return _selectFactory(info).create(info, adapter, instance);
	}

	private InternalGeneratorStrategy _selectFactory(ReflectionInfo info){
		return _factoryCache.get(info.classType());
	}
	
	private void handlingError(Throwable t){
		t.printStackTrace();
	}
	
	/* --------------------------------------------------------- */
	/* strategy for create object & set value                    */
	/* --------------------------------------------------------- */
	
	class _DomainStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			Object target = info.newInstance();
			for(ReflectionInfo each : ReflectionInfoFactory.fieldTypeAll(info)){
				if(each.isImmutable())
					continue;
				Object val = _reflect(each,adapter,target);
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
	
	class _ArrayStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			Object[] arrays = (Object[])info.newInstance();
			for(int i = 0; i < arrays.length; i++)
				//TODO impl
				arrays[i] = _reflect(info, adapter, enclosingInstance);
			return arrays;
		}
	}
	
	class _ProxyStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			StubFactory factory = new StubFactory();
			if(_extValueGenerator == null)
				return factory.create(
					_loader, 
					_valueGenerator,
					info.interfaces()
				);
			return factory.create(
				_loader, 
				_extValueGenerator,
				info.interfaces()
			);
//			return Proxy.newProxyInstance(
//				_loader, 
//				info.interfaces(), 
//				new  ReflectionObjectAdapter(_loader, adapter._adptGenerator)
//			);
		}
	}
	
	class _AbstractStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			return info.newInstance();
		}
	}
	
	class _EnumStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			return adapter.generateEnumValue(info,enclosingInstance);
		}
	}
	
	class _ObjectStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			return adapter.generateObjectValue(info,enclosingInstance);
		}
	}
	
	class _EmbedStrategy implements InternalGeneratorStrategy{
		@Override
		public Object create(ReflectionInfo info, InternalGenerator adapter, Object enclosingInstance) {
			return adapter.generateEmbedValue(info,enclosingInstance);
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
	class _ReflectionCache{
		volatile Map<String,ReflectionInfo> _store;
		public _ReflectionCache(){
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
