package org.synthe.ridill.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.synthe.ridill.core.ExtValueGenerator;
import org.synthe.ridill.core.StubProxy;
import org.synthe.ridill.core.TargetInfo;
import org.synthe.ridill.core.ValueGenerator;

public class ReflectionObject {
	
	private Map<ClassType, _GenerateStrategy> _factoryCache;
	private _ReflectionCache _rcache;
	private ClassLoader _loader;
	private _ValueGeneratorAdapter _generator;
	public ReflectionObject(ClassLoader loader, ValueGenerator generator){
		_loader = loader;
		_generator = new _ValueGeneratorAdapter(generator);
		
		_initialize();
	}
	
	public ReflectionObject(ClassLoader loader, ExtValueGenerator generator){
		_loader = loader;
		_generator = new _ExtValueGeneratorAdapter(generator);
		
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
		_factoryCache.put(ClassType.arrayType, new _ArrayStrategy());
		_factoryCache.put(ClassType.mapType, new _DictionaryStrategy());
		_factoryCache.put(ClassType.domainType, new _DomainStrategy());
		_rcache = new _ReflectionCache();
	}
	
	public Object reflect(Object proxy, Method method, Object[] args){
		Class<?> target = method.getReturnType();
		ReflectionInfo info = !_rcache.has(target) ?
			ReflectionInfoFactory.returnType(proxy, method, args):
			_rcache.get(target);
		return _reflect(info, _generator);
	}

	
	/* --------------------------------------------------------- */
	/* private method                                            */
	/* --------------------------------------------------------- */
	private Object _reflect(ReflectionInfo info, _ValueGeneratorAdapter adapter){
		return _selectFactory(info).create(info, adapter);
	}

	private _GenerateStrategy _selectFactory(ReflectionInfo info){
		return _factoryCache.get(info.classType());
	}
	
	

	/* --------------------------------------------------------- */
	/* strategy for create object & set value                    */
	/* --------------------------------------------------------- */
	interface _GenerateStrategy {
		Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter);
	}
	
	class _DomainStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			Object instance = info.newInstance();
			for(ReflectionInfo each : ReflectionInfoFactory.fieldTypeAll(info)){
				if(each.isImmutable())
					continue;
				Object val = _reflect(each,adapter);
				info.set(instance, val);
			}
			return instance;
		}
	}
	
	class _ArrayStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			Object[] arrays = (Object[])info.newInstance();
			for(int i = 0; i < arrays.length; i++)
				//TODO impl
				arrays[i] = _reflect(info, adapter);
			return arrays;
		}
	}
	
	class _DictionaryStrategy implements _GenerateStrategy{
		@SuppressWarnings("unchecked")
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			TargetInfo gi = info.getTargetInfo();
			Map<Object,Object> collection = (Map<Object,Object>)adapter.generateMap(info, gi);

			int size = adapter.generateCollectionSize(info,gi);
			
			ReflectionInfo typeKeyParamInfo = ReflectionInfoFactory.fieldTypeParameterType(info, 0);
			ReflectionInfo typeValueParamInfo = ReflectionInfoFactory.fieldTypeParameterType(info, 1);
			for(int idx = 0; idx < size; idx++){
				Object key = _reflect(typeKeyParamInfo, adapter);
				Object value = _reflect(typeValueParamInfo, adapter);
				collection.put(key, value);
			}
			
			return collection;
		}
	}

	class _QueueStrategy extends _LinearCollectionStrategy<Queue<?>>{
		@Override
		Queue<?> newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter) {
			return adapter.generateQueue(info, gi);
		}
	}
	
	class _SetStrategy extends _LinearCollectionStrategy<Set<?>>{
		@Override
		Set<?> newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter) {
			return adapter.generateSet(info, gi);
		}
	}
	
	class _ListStrategy extends _LinearCollectionStrategy<List<?>>{
		@Override
		List<?> newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter) {
			return adapter.generateList(info, gi);
		}
	}
	
	abstract class _LinearCollectionStrategy<T extends Collection<?>> implements _GenerateStrategy{
		abstract T newInstance(ReflectionInfo info, TargetInfo gi, _ValueGeneratorAdapter adapter);
		@SuppressWarnings("unchecked")
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			TargetInfo gi = info.getTargetInfo();
			Collection<Object> collection = (Collection<Object>)newInstance(info, gi, adapter);
			
			int size = adapter.generateCollectionSize(info,gi);
			
			//TODO impl
			ReflectionInfo typeParamInfo = ReflectionInfoFactory.fieldTypeParameterType(info,0);
			for(int idx = 0; idx < size; idx++)
				collection.add(_reflect(typeParamInfo, adapter));
			
			return collection;
		}
	}
	
	class _ProxyStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			return Proxy.newProxyInstance(
				_loader, 
				info.interfaces(), 
				new StubProxy(_loader, adapter._adptGenerator)
			);
		}
	}
	
	class _AbstractStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			return info.newInstance();
		}
	}
	
	class _EnumStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			return adapter.generateEnumValue(info);
		}
	}
	
	class _ObjectStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			return new Object();
		}
	}
	
	class _EmbedStrategy implements _GenerateStrategy{
		@Override
		public Object create(ReflectionInfo info, _ValueGeneratorAdapter adapter) {
			return adapter.generateEmbedValue(info);
		}
	}
	
	
	/* --------------------------------------------------------- */
	/* ValueGenerator adapter                                    */
	/* --------------------------------------------------------- */
	class _ExtValueGeneratorAdapter extends _ValueGeneratorAdapter{
		private ExtValueGenerator _adptExtGenerator;
		public _ExtValueGeneratorAdapter(ExtValueGenerator extGenerator){
			_adptExtGenerator = extGenerator;
		}
		@Override
		public Map<?,?> generateMap(ReflectionInfo info, TargetInfo gi){
			return _adptExtGenerator.dictionary(info);
		}
		@Override
		public Queue<?> generateQueue(ReflectionInfo info, TargetInfo gi){
			return _adptExtGenerator.queue(info);
		}
		@Override
		public Set<?> generateSet(ReflectionInfo info, TargetInfo gi){
			return _adptExtGenerator.set(info);
		}
		@Override
		public List<?> generateList(ReflectionInfo info, TargetInfo gi){
			return _adptExtGenerator.list(info);
		}
		@Override
		public Object generateEmbedValue(ReflectionInfo info){
			return _adptExtGenerator.get(info);
		}
		@Override
		public Enum<?> generateEnumValue(ReflectionInfo info){
			return (Enum<?>)_adptExtGenerator.get(info);
		}
		@Override
		public Integer generateCollectionSize(ReflectionInfo info, TargetInfo gi){
			return _adptExtGenerator.size(info);
		}

	}
	
	class _ValueGeneratorAdapter{
		private ValueGenerator _adptGenerator;
		public _ValueGeneratorAdapter(){}
		public _ValueGeneratorAdapter(ValueGenerator generator){
			_adptGenerator = generator;
		}
		@SuppressWarnings("unused")
		public Map<?,?> generateMap(ReflectionInfo info, TargetInfo gi){
			return new ConcurrentSkipListMap<>();
		}
		
		@SuppressWarnings("unused")
		public Queue<?> generateQueue(ReflectionInfo info, TargetInfo gi){
			return new LinkedBlockingDeque<>();
		}
		
		@SuppressWarnings("unused")
		public Set<?> generateSet(ReflectionInfo info, TargetInfo gi){
			return new TreeSet<>();
		}
		
		@SuppressWarnings("unused")
		public List<?> generateList(ReflectionInfo info, TargetInfo gi){
			return new LinkedList<>();
		}
		
		@SuppressWarnings("unused")
		public Integer generateCollectionSize(ReflectionInfo info, TargetInfo gi){
			return _adptGenerator.getCollectionSize(gi);
		}
		
		@SuppressWarnings("unchecked")
		public Enum<?> generateEnumValue(ReflectionInfo info){
			TargetInfo gi = info.getTargetInfo();
			List<Enum<?>> enums = (List<Enum<?>>)info.newInstance();
			return _adptGenerator.getEnum(gi, enums);
		}
		
		public Object generateEmbedValue(ReflectionInfo info){
			TargetInfo gi = info.getTargetInfo();

			if(info.classType() == ClassType.byteType)
				return _adptGenerator.getByte(gi);
			if(info.classType() == ClassType.booleanType)
				return _adptGenerator.getBoolean(gi);
			if(info.classType() == ClassType.floatType)
				return _adptGenerator.getFloat(gi);
			if(info.classType() == ClassType.doubleType)
				return _adptGenerator.getDouble(gi);
			if(info.classType() == ClassType.shortType)
				return _adptGenerator.getShort(gi);
			if(info.classType() == ClassType.integerType)
				return _adptGenerator.getInteger(gi);
			if(info.classType() == ClassType.longType)
				return _adptGenerator.getLong(gi);
			if(info.classType() == ClassType.characterType)
				return _adptGenerator.getCharacter(gi);
			if(info.classType() == ClassType.stringType)
				return _adptGenerator.getString(gi);
			
			return _factoryCache.get(ClassType.objectType).create(info, this);
		}
	}
	
	
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
