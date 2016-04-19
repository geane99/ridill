package org.synthe.ridill.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

class ClassInfo<T extends AccessibleObject> {
	protected Class<?> _type;
	protected ClassType _classType;
	protected T _owner;
	protected ClassInfo<?>[] _parameters;
	protected ClassOwnerType _ownerType;
	
	public ClassInfo(T owner, ClassOwnerType ownerType){
		_owner = owner;
		_ownerType = ownerType;
	}
	
	public T owner(){
		return _owner;
	}
	
	public Class<?> type(){
		return _type; 
	}
	
	public ClassType classType(){
		return _classType;
	}
	
	public boolean isImmutable(){
		return 
			_ownerType == ClassOwnerType.property && 
			_owner instanceof Member && 
			Modifier.isFinal(((Member)_owner).getModifiers());
	}
	public Object newInstance(){
		//TODO impl
		return null;
	}
	
	public boolean isProperty(){
		return _ownerType == ClassOwnerType.property;
	}
	public boolean isReturnValue(){
		return _ownerType == ClassOwnerType.returnValue;
	}
	public boolean isTypeParameter(){
		return _ownerType == ClassOwnerType.typeParameter;
	}
	public boolean isNormalClass(){
		return _ownerType == ClassOwnerType.itself;
	}
	public boolean isInterface(){
		return _classType == ClassType.interfaceType;
	}
	public boolean isAbstract(){
		return _classType == ClassType.abstractType;
	}
	public boolean isAnnotation(){
		return _classType == ClassType.annotationType;
	}
	public boolean isObject(){
		return _classType == ClassType.objectType;
	}
	public boolean isList(){
		return _classType == ClassType.listType;
	}
	public boolean isMap(){
		return _classType == ClassType.mapType;
	}
	public boolean isQueue(){
		return _classType == ClassType.queueType;
	}
	public boolean isSet(){
		return _classType == ClassType.setType;
	}
	public boolean isCollection(){
		return _classType == ClassType.collectionType;
	}
	public boolean isArray(){
		return _classType == ClassType.arrayType;
	}
	public boolean isEnum(){
		return _classType == ClassType.enumType;
	}
	public boolean isLocalClass(){
		return _type.isLocalClass();
	}
	public boolean isMemberClass(){
		return _type.isMemberClass();
	}
	public boolean isAnonymousClass(){
		return _type.isAnonymousClass();
	}
}
