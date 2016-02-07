package com.trazere.core.reflect;

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.InternalException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

public abstract class GenType<T> {
	public GenType() {
		// Get the super type.
		final Type superType = getClass().getGenericSuperclass();
		if (!(superType instanceof ParameterizedType)) {
			throw new RuntimeException("Invalid gen type " + this);
		}
		final ParameterizedType parameterizedSuperType = (ParameterizedType) superType;
		
		// Get the type.
		if (!parameterizedSuperType.getRawType().equals(GenType.class)) {
			throw new RuntimeException("Invalid gen type " + this);
		}
		_type = parameterizedSuperType.getActualTypeArguments()[0];
		
		// Compute the raw type.
		@SuppressWarnings("unchecked")
		final Class<? super T> rawType = (Class<? super T>) computeRawType(_type);
		_rawType = rawType;
	}
	
	// Type.
	
	protected final Type _type;
	
	public Type getType() {
		return _type;
	}
	
	// Raw type.
	
	protected final Class<? super T> _rawType;
	
	public Class<? super T> getRawType() {
		return _rawType;
	}
	
	private static Class<?> computeRawType(final Type type) {
		if (type instanceof Class) {
			// Class.
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			// Parameterized type.
			return computeRawType(((ParameterizedType) type).getRawType());
		} else if (type instanceof TypeVariable) {
			// Type variable.
			final Type[] bounds = ((TypeVariable<?>) type).getBounds();
			if (bounds.length > 0) {
				return computeRawType(bounds[0]);
			} else {
				throw new InternalException("No raw type for type " + type + " (no bounds)");
			}
		} else if (type instanceof WildcardType) {
			// Wildcard type.
			final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
			if (upperBounds.length > 0) {
				return computeRawType(upperBounds[0]);
			} else {
				throw new InternalException("No raw type for type " + type + " (no upper bounds)");
			}
		} else if (type instanceof GenericArrayType) {
			// Generic array type.
			return Array.newInstance(computeRawType(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
		} else {
			throw new InternalException("Invalid type " + type);
		}
	}
	
	//
	
	public boolean isSubTypeOf() {
	
	}
	
	private static boolean isSubType(final Type type, final Type refType) {
		if (refType instanceof Class) {
			// Class.
		} else if (refType instanceof ParameterizedType) {
			// Parameterized type.
		} else if (refType instanceof TypeVariable) {
			// Type variable.
		} else if (refType instanceof WildcardType) {
			// Wildcard type.
		} else if (refType instanceof GenericArrayType) {
			// Generic array type.
		} else {
			throw new InternalException("Invalid reference type " + refType);
		}
	}
	
	private static boolean isSubType(final Type type, final Class<?> refType) {
		if (type instanceof Class) {
			// Class.
			
		} else if (type instanceof ParameterizedType) {
			// Parameterized type.
		} else if (type instanceof TypeVariable) {
			// Type variable.
		} else if (type instanceof WildcardType) {
			// Wildcard type.
		} else if (type instanceof GenericArrayType) {
			// Generic array type.
		} else {
			throw new InternalException("Invalid type " + type);
		}
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_type);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && object instanceof GenType) {
			final GenType<?> type = (GenType<?>) object;
			return _type.equals(type._type);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _type.toString();
	}
	
	public static void main(final String[] args) {
		p(new GenType<String>() { /**/ });
		p(new GenType<List<String>>() { /**/ });
		p(new GenType<ArrayList<String>>() { /**/ });
		p(new GenType<List<?>>() { /**/ });
		p(param1());
		p(param2());
		p(param3());
		p(param4());
		p(new GenType<int[]>() { /**/ });
		p(new GenType<String[]>() { /**/ });
		p(new GenType<List<?>[]>() { /**/ });
	}
	
	private static <T> GenType<T> param1() {
		return new GenType<T>() { /**/ };
	}
	
	private static <T extends Iterable<?>> GenType<T> param2() {
		return new GenType<T>() { /**/ };
	}
	
	private static <T extends Iterable<String>> GenType<T> param3() {
		return new GenType<T>() { /**/ };
	}
	
	private static <T extends Thread & Comparable<?>> GenType<T> param4() {
		return new GenType<T>() { /**/ };
	}
	
	private static void p(final GenType<?> type) {
		System.out.println("-> " + type.getType() + " - " + type.getRawType());
	}
}
