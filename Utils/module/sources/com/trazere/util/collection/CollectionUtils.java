package com.trazere.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.trazere.util.Assert;

/**
 * The <code>CollectionUtils</code> class provides various helpers regarding the manipulation of collections and maps.
 */
public class CollectionUtils {
	/**
	 * Build an empty collection of the given type.
	 * 
	 * @param <C> Type of the collection to build.
	 * @param type Class of the collection to build. Must be instanciable with no arguments.
	 * @return The empty collection.
	 * @see Collection
	 */
	public static <C extends Collection<?>> C buildCollection(final Class<C> type) {
		Assert.notNull(type);

		// Instanciate the collection.
		try {
			return type.newInstance();
		} catch (final InstantiationException exception) {
			throw new RuntimeException(exception);
		} catch (final IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Build an empty map of the given type.
	 * 
	 * @param <M> Type of the map to build.
	 * @param type Class of the map to build. Must be instanciable with no arguments.
	 * @return The empty map.
	 * @see Map
	 */
	public static <M extends Map<?, ?>> M buildMap(final Class<M> type) {
		// Checks
		Assert.notNull(type);

		// Instanciate the map.
		try {
			return type.newInstance();
		} catch (final InstantiationException exception) {
			throw new RuntimeException(exception);
		} catch (final IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Build a new list with the given value.
	 * <p>
	 * This method instanciates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param value Value. May be <code>null</code>.
	 * @return The list.
	 * @see ArrayList
	 */
	public static <T> List<T> list(final T value) {
		// Build the list.
		final List<T> list = new ArrayList<T>(1);
		list.add(value);

		return list;
	}

	/**
	 * Build a list with the given values.
	 * <p>
	 * This method instanciates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @return The list.
	 * @see ArrayList
	 */
	public static <T> List<T> list(final T value1, final T value2) {
		// Build the list.
		final List<T> list = new ArrayList<T>(2);
		list.add(value1);
		list.add(value2);

		return list;
	}

	/**
	 * Build a list with the given values.
	 * <p>
	 * This method instanciates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @param value3 Second value. May be <code>null</code>.
	 * @return The list.
	 * @see ArrayList
	 */
	public static <T> List<T> list(final T value1, final T value2, final T value3) {
		// Build the list.
		final List<T> list = new ArrayList<T>(3);
		list.add(value1);
		list.add(value2);
		list.add(value3);

		return list;
	}

	/**
	 * Build a list with the given values.
	 * <p>
	 * This method instanciates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param values Array containing the values.
	 * @return The set.
	 */
	public static <T> List<T> listN(final T[] values) {
		Assert.notNull(values);

		// Build the list.
		final List<T> list = new ArrayList<T>(values.length);
		for (final T value : values) {
			list.add(value);
		}
		return list;
	}

	/**
	 * Build a set with the given value.
	 * <p>
	 * This method instanciates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param value Value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value) {
		// Build the set.
		final Set<T> set = new HashSet(1);
		set.add(value);

		return set;
	}

	/**
	 * Build a set with the given values.
	 * <p>
	 * This method instanciates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value1, final T value2) {
		// Build the set.
		final Set<T> set = new HashSet(2);
		set.add(value1);
		set.add(value2);

		return set;
	}

	/**
	 * Build a set with the given values.
	 * <p>
	 * This method instanciates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @param value3 Third value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value1, final T value2, final T value3) {
		// Build the set.
		final Set<T> set = new HashSet(3);
		set.add(value1);
		set.add(value2);
		set.add(value3);

		return set;
	}

	/**
	 * Build a set with the given values.
	 * <p>
	 * This method instanciates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the elements.
	 * @param values Array containing the values.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> setN(final T[] values) {
		// Checks
		Assert.notNull(values);

		// Build the set.
		final Set<T> set = new HashSet<T>(values.length);
		for (final T value : values) {
			set.add(value);
		}
		return set;
	}

	/**
	 * Build a map with the binding of the given key and value.
	 * <p>
	 * This method instanciates a <code>HashMap</code>.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key Key of the binding. May be <code>null</code>.
	 * @param value Value of the binding. May be <code>null</code>.
	 * @return The map.
	 * @see HashMap
	 */
	public static <K, V> Map<K, V> map(final K key, final V value) {
		// Build the map.
		final Map<K, V> result = new HashMap();
		result.put(key, value);

		return result;
	}

	/**
	 * Build a map with the bindings of the given keys and values.
	 * <p>
	 * This method instanciates a <code>HashMap</code>.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding. May be <code>null</code>.
	 * @param value1 Value of the first binding. May be <code>null</code>.
	 * @param key2 Key of the second binding. May be <code>null</code>.
	 * @param value2 Value of the second binding. May be <code>null</code>.
	 * @return The map.
	 * @see HashMap
	 */
	public static <K, V> Map<K, V> map(final K key1, final V value1, final K key2, final V value2) {
		// Build the map.
		final Map<K, V> result = new HashMap();
		result.put(key2, value2);
		result.put(key1, value1);

		return result;
	}

	/**
	 * Build an iterator over the given value.
	 * 
	 * @param <T> Type of the elements.
	 * @param value Value to iterate. May be <code>null</code>.
	 * @return The iterator.
	 */
	public static <T> Iterator<T> iterator(final T value) {
		// Build the iterator.
		return new Iterator<T>() {
			protected boolean _iterated = false;

			public boolean hasNext() {
				return !_iterated;
			}

			public T next() {
				if (!_iterated) {
					_iterated = true;
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Build an iterator over the given values.
	 * 
	 * @param <T> Type of the elements.
	 * @param values Values to iterate.
	 * @return The iterator.
	 */
	public static <T> Iterator<T> iteratorN(final T[] values) {
		Assert.notNull(values);

		// Build the iterator.
		return new Iterator<T>() {
			protected int _index = 0;

			public boolean hasNext() {
				return _index < values.length;
			}

			public T next() {
				final T value = values[_index];
				_index += 1;

				return value;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Get an objet from the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection Collection to read.
	 * @return Any object from the collection, or <code>null</code> when the collection is empty.
	 */
	public static <T> T any(final Collection<T> collection) {
		Assert.notNull(collection);

		// Read.
		final Iterator<T> iterator = collection.iterator();
		return iterator.hasNext() ? iterator.next() : null;
	}

	/**
	 * Get an entry from the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return Any entry from the map, or <code>null</code> if the map is empty.
	 */
	public static <K, V> Map.Entry<K, V> any(final Map<K, V> map) {
		Assert.notNull(map);

		// Read.
		final Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
		return iterator.hasNext() ? iterator.next() : null;
	}

	/**
	 * Get the last value from the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param list List to read.
	 * @return The last value, or <code>null</code> when the list is empty.
	 */
	public static <T> T last(final List<T> list) {
		Assert.notNull(list);

		// Read.
		final int size = list.size();
		return size > 0 ? list.get(size - 1) : null;
	}

	/**
	 * Build a reversed list of the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param <L> Type of the list to instanciate.
	 * @param list List.
	 * @param type Class of the list to build. Must be instanciable with no arguments.
	 * @return The reversed <code>List</code>.
	 */
	public static <T, L extends List> L reverse(final List<T> list, final Class<L> type) {
		Assert.notNull(list);

		// Build the list.
		final L results = buildCollection(type);
		results.addAll(list);
		Collections.reverse(results);
		return results;
	}

	/**
	 * Build the union of the given collections.
	 * <p>
	 * When the result collection is ordered, the items of the first given collection precede the items of the second one.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection to instanciate.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param type Class of the collection to build. Must be instanciable with no arguments.
	 * @return The union <code>Collection</code>.
	 */
	public static <T, C extends Collection<T>> C union(final Collection<T> collection1, final Collection<T> collection2, final Class<C> type) {
		Assert.notNull(collection1);
		Assert.notNull(collection2);

		// Build the union.
		final C results = buildCollection(type);
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}

	/**
	 * Build the union of the given maps.
	 * <p>
	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to instanciate.
	 * @param map1 First map.
	 * @param map2 Second map.
	 * @param type Class of the map to build. Must be instanciable with no arguments.
	 * @return The union <code>Map</code>.
	 */
	public static <K, V, M extends Map<K, V>> M union(final Map<K, V> map1, final Map<K, V> map2, final Class<M> type) {
		Assert.notNull(map1);
		Assert.notNull(map2);

		// Build the union.
		final M results = buildMap(type);
		results.putAll(map2);
		results.putAll(map1);
		return results;
	}

	/**
	 * Test wether the given collections intersect. Collections instersect when they have some common value.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return <code>true</code> if the given collections intersect, <code>false</code> otherwise.
	 */
	public static <T> boolean intersects(final Collection<T> collection1, final Collection<T> collection2) {
		Assert.notNull(collection1);
		Assert.notNull(collection2);

		// Test.
		for (final Object value : collection1) {
			if (collection2.contains(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Build the intersection of the given collections.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection to instanciate.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param type Class of the collection to build. Must be instanciable with no arguments.
	 * @return The intersection <code>Collection</code>.
	 */
	public static <T, C extends Collection<T>> C intersection(final Collection<T> collection1, final Collection<T> collection2, final Class<C> type) {
		Assert.notNull(collection1);
		Assert.notNull(collection2);

		// Build the intersection.
		final C results = buildCollection(type);
		results.addAll(collection1);
		results.retainAll(collection2);
		return results;
	}

	/**
	 * Build a copy of the first given collection excluding the values of the second given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection to instanciate.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param type Class of the collection to build. Must be instanciable with no arguments.
	 * @return The exclusion collection.
	 */
	public static <T, C extends Collection<T>> C exclusion(final Collection<T> collection1, final Collection<T> collection2, final Class<C> type) {
		// Checks
		Assert.notNull(collection1);
		Assert.notNull(collection2);

		// Build the exclusion.
		final C results = buildCollection(type);
		results.addAll(collection1);
		results.removeAll(collection2);
		return results;
	}

	/**
	 * Build a copy of the given map excluding the bindings with the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to instanciate.
	 * @param map Map to copy.
	 * @param keys Keys of the binding to exclude.
	 * @param type Class of the map to build. Must be instanciable with no arguments.
	 * @return The exclusion <code>Map</code>.
	 */
	public static <K, V, M extends Map<K, V>> M exclusion(final Map<K, V> map, final Collection<K> keys, final Class<M> type) {
		Assert.notNull(map);
		Assert.notNull(keys);

		// Build the exclusion.
		final M results = buildMap(type);
		for (final Map.Entry<K, V> entry : map.entrySet()) {
			final K key = entry.getKey();
			if (!keys.contains(key)) {
				results.put(key, entry.getValue());
			}
		}
		return results;
	}

	/**
	 * Build a copy of the given map containing the bindings with the given keys only.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to instanciate.
	 * @param map Map to copy.
	 * @param keys Keys of the bindings to retain.
	 * @param type Class of the map to build. Must be instanciable with no arguments.
	 * @return The submap.
	 */
	public static <K, V, M extends Map<K, V>> M submap(final Map<K, V> map, final Set<K> keys, final Class<M> type) {
		Assert.notNull(keys);

		// Build the map.
		final M results = buildMap(type);
		for (final K key : keys) {
			if (map.containsKey(key)) {
				final V value = map.get(key);
				results.put(key, value);
			}
		}
		return results;
	}

	private CollectionUtils() {
		// Prevent instanciation.
	}
}
