package com.trazere.util.collection;

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link LazyObjectTypeMap} abstract class represents lazy maps from Java class hierarchies to values.
 * 
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 */
public abstract class LazyObjectTypeMap<V, X extends Exception>
extends AbstractLazyTypeMap<Class<?>, V, X>
implements ObjectTypeMap<Maybe<V>, X> {
	/**
	 * Builds a new type map with no upper bounds and defaut values using the given function.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built map.
	 */
	public static <V, X extends Exception> LazyObjectTypeMap<V, X> build(final Function1<Class<?>, Maybe<V>, X> function) {
		return build(function, Maybe.<Class<?>>none(), Maybe.<V>none());
	}
	
	/**
	 * Builds a new type map with no upper bounds and the given default value using the given function.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param defaultValue The default value.
	 * @return The built map.
	 */
	public static <V, X extends Exception> LazyObjectTypeMap<V, X> build(final Function1<Class<?>, Maybe<V>, X> function, final Maybe<V> defaultValue) {
		return build(function, Maybe.<Class<?>>none(), defaultValue);
	}
	
	/**
	 * Builds a new type map with the given upper bound and default value using the given function.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param upperBound The upper bound.
	 * @param defaultValue The default value.
	 * @return The built map.
	 */
	public static <V, X extends Exception> LazyObjectTypeMap<V, X> build(final Function1<Class<?>, Maybe<V>, X> function, final Maybe<Class<?>> upperBound, final Maybe<V> defaultValue) {
		return new LazyObjectTypeMap<V, X>(upperBound, defaultValue) {
			@Override
			protected Maybe<V> computeDiscrete(final Class<?> type)
			throws X {
				return function.evaluate(type);
			}
		};
	}
	
	/**
	 * Instantiates a new type map with no upper bounds and defaut values.
	 */
	public LazyObjectTypeMap() {
		super();
	}
	
	/**
	 * Instantiates a new type map with no upper bounds and the given default value.
	 * 
	 * @param defaultValue The default value.
	 */
	public LazyObjectTypeMap(final Maybe<V> defaultValue) {
		super(defaultValue);
	}
	
	/**
	 * Instantiates a new type map with the given upper bound and default value.
	 * 
	 * @param upperBound The upper bound.
	 * @param defaultValue The default value.
	 */
	public LazyObjectTypeMap(final Maybe<java.lang.Class<?>> upperBound, final Maybe<V> defaultValue) {
		super(upperBound, defaultValue);
	}
	
	@Override
	protected boolean isSubTypeOf(final Class<?> type1, final Class<?> type2) {
		return type2.isAssignableFrom(type1);
	}
	
	@Override
	protected Collection<Class<?>> getSuperTypes(final Class<?> type) {
		final Set<Class<?>> superTypes = new HashSet<Class<?>>();
		CollectionUtils.add(superTypes, Maybe.fromValue(type.getSuperclass()));
		final Class<?>[] interfaces = type.getInterfaces();
		superTypes.addAll(Arrays.asList(interfaces));
		return superTypes;
	}
}
