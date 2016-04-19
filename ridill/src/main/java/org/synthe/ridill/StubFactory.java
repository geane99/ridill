package org.synthe.ridill;

import java.lang.reflect.Proxy;
import java.util.Collection;

/**
 * create a dynamic proxy to set the return value in the auto.<br/>
 * usage:
 * <pre>
 * {@code
 * public interface AInterfaceOfAPI{
 *   public Member getMemberById(String id);
 * }
 * AInterfaceOfAPI remoteapi = StubFactory.create(AInterfaceOfAPI.class);
 * //member value is random generate.
 * Member member = remoteapi.getMemberById("xxxxxx");
 * }
 * </pre>
 * 値を自動で生成して返す動的プロキシを作成するファクトリクラス。<br/>
 * @since 2015/01/18
 * @author masahiko.ootsuki
 * @version 1.0.0
 */
public class StubFactory {
	/**
	 * When dont specify in the argument, used as the default.
	 * see link {@link ValueGenerator}
	 * @since 2015/01/18
	 */
	private ValueGenerator defaultValueGenerator;
	/**
	 * When dont specify in the argument, used as the default.
	 * see link {@link ClassLoader}
	 * @since 2015/01/18
	 */
	private ClassLoader defaultClassLoader = Thread.currentThread().getContextClassLoader();
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(Collection<String> classNames) throws ClassNotFoundException{
		return create(defaultClassLoader, defaultValueGenerator, (String[])classNames.toArray());
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param generator {@link ValueGenerator}
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(ValueGenerator generator, Collection<String> classNames) throws ClassNotFoundException{
		return create(defaultClassLoader, generator, (String[])classNames.toArray());
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(ClassLoader loader, Collection<String> classNames) throws ClassNotFoundException{
		return create(loader, defaultValueGenerator, (String[])classNames.toArray());
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ValueGenerator}
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(ClassLoader loader, ValueGenerator generator, Collection<String> classNames) throws ClassNotFoundException{
		return create(loader, generator, (String[])classNames.toArray());
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(String...classNames) throws ClassNotFoundException{
		return create(defaultClassLoader, defaultValueGenerator, classNames);
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param generator {@link ValueGenerator}
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(ValueGenerator generator, String...classNames) throws ClassNotFoundException{
		return create(defaultClassLoader, generator, classNames);
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist.
	 */
	public <T> T create(ClassLoader loader, String...classNames) throws ClassNotFoundException{
		return create(loader, defaultValueGenerator, classNames);
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ValueGenerator}
	 * @param classNames interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 * @throws ClassNotFoundException classname of arguments doesnt exist. 引数のクラス名が存在しないとき
	 */
	public <T> T create(ClassLoader loader, ValueGenerator generator, String...classNames) throws ClassNotFoundException{
		Class<?>[] classes = new Class[classNames.length];
		for(int i=0; i < classNames.length ;i++){
			Class<?> clazz = loader.loadClass(classNames[i]);
			classes[i]=clazz;
		}
		return create(loader, generator, classes);
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param generator {@link ValueGenerator}
	 * @param classes interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 */
	public <T> T create(ValueGenerator generator, Class<?>...classes){
		return create(defaultClassLoader, generator, classes);
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param classes interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 */
	public <T> T create(ClassLoader loader, Class<?>...classes){
		return create(loader, defaultValueGenerator, classes);
	}
	
	/**
	 * see link {@link #create(ClassLoader,ValueGenerator,Class)}
	 * @since 2015/01/18
	 * @param classes interface to become proxy.
	 * @return {@link java.lang.reflect.Proxy}
	 */
	public <T> T create(Class<?>...classes){
		return create(defaultClassLoader, defaultValueGenerator, classes);
	}
	
	/**
	 * create a dynamic proxy to set the return value in the auto.
	 * <br/>
	 * 自動で戻り値を返す、動的Proxyを生成して返します
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ValueGenerator}
	 * @param classes interface to become proxy. 動的Proxyにするインターフェース
	 * @return {@link java.lang.reflect.Proxy}
	 */
	@SuppressWarnings("unchecked")
	public <T> T create(ClassLoader loader, ValueGenerator generator, Class<?>...classes){
		return (T)Proxy.newProxyInstance(
			loader, 
			classes, 
			new StubProxy(
				loader == null ? defaultClassLoader : loader,
				generator == null ? defaultValueGenerator : generator 
			)
		);
	}
	/**
	 * create a dynamic proxy to set the return value in the auto.
	 * <br/>
	 * 自動で戻り値を返す、動的Proxyを生成して返します
	 * @since 2015/01/18
	 * @param loader {@link ClassLoader}
	 * @param generator {@link ExtValueGenerator}
	 * @param classes interface to become proxy. 動的Proxyにするインターフェース
	 * @return {@link java.lang.reflect.Proxy}
	 */
	@SuppressWarnings("unchecked")
	public <T> T create(ClassLoader loader, ExtValueGenerator generator, Class<?>...classes){
		if(generator == null)
			return create(loader, defaultValueGenerator, classes);
		return (T)Proxy.newProxyInstance(
			loader, 
			classes, 
			new StubProxy(
				loader == null ? defaultClassLoader : loader,
				generator
			)
		);
	}	
}
