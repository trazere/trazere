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
import com.trazere.util.function.ApplicationException;
import com.trazere.util.function.Function;
import com.trazere.util.type.Tuple2;

/**
 * The <code>CollectionUtils</code> class provides various helpers regarding the manipulation of collections and maps.
 */
public class CollectionUtils {
	/**
	 * Build a new list with the given value.
	 * <p>
	 * This method instanciates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the values.
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
	 * @param <T> Type of the the values.
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
	 * @param <T> Type of the the values.
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
	 * @param <T> Type of the the values.
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
	 * @param <T> Type of the the values.
	 * @param value Value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value) {
		// Build the set.
		final Set<T> set = new HashSet<T>(1);
		set.add(value);

		return set;
	}

	/**
	 * Build a set with the given values.
	 * <p>
	 * This method instanciates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value1, final T value2) {
		// Build the set.
		final Set<T> set = new HashSet<T>(2);
		set.add(value1);
		set.add(value2);

		return set;
	}

	/**
	 * Build a set with the given values.
	 * <p>
	 * This method instanciates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @param value3 Third value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value1, final T value2, final T value3) {
		// Build the set.
		final Set<T> set = new HashSet<T>(3);
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
	 * @param <T> Type of the the values.
	 * @param values Array containing the values.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> setN(final T[] values) {
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
		final Map<K, V> result = new HashMap<K, V>();
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
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key2, value2);
		result.put(key1, value1);

		return result;
	}

	/**
	 * Build an iterator over the given value.
	 * 
	 * @param <T> Type of the the values.
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
	 * @param <T> Type of the the values.
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
	 * Build a list containing the given list in the reverse order.
	 * 
	 * @param <T> Type of the elements.
	 * @param list List to reverse. It is not modified by the method.
	 * @return The reversed <code>List</code>.
	 */
	public static <T> ArrayList<T> reverse(final List<? extends T> list) {
		Assert.notNull(list);

		// Build the list.
		final ArrayList<T> results = new ArrayList<T>(list);
		Collections.reverse(results);
		return results;
	}

	/**
	 * Sort the given values topologically.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed value must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to sort. The given collection is not modified by the method.
	 * @param dependencyFunction Function which computes the dependencies.
	 * @return The sorted values.
	 * @throws CollectionException When some dependencies are invalid or cyclic.
	 */
	public static <T> ArrayList<T> topologicalSort(final Collection<? extends T> values, final Function<T, ? extends Collection<T>> dependencyFunction)
	throws CollectionException {
		return topologicalSort(values, dependencyFunction, new ArrayList<T>(values.size()));
	}

	/**
	 * Sort the given values topologically.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed value must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param <L> Type of the list to instanciate.
	 * @param values Values to sort. The given collection is not modified by the method.
	 * @param dependencyFunction Function which computes the dependencies.
	 * @param factory Factory to use to build the result list.
	 * @return The sorted values.
	 * @throws CollectionException When some dependencies are invalid or cyclic.
	 */
	public static <T, L extends List<T>> L topologicalSort(final Collection<? extends T> values, final Function<? super T, ? extends Collection<T>> dependencyFunction, final CollectionFactory<? super T, ? extends L> factory)
	throws CollectionException {
		return topologicalSort(values, dependencyFunction, factory.build(values.size()));
	}

	/**
	 * Sort the given values topologically and populate the given list with the results.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed value must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param <L> Type of the populated list.
	 * @param values Values to sort. The given collection is not modified by the method.
	 * @param dependencyFunction Function which computes the dependencies.
	 * @param results List to populate with the results.
	 * @return The populated list.
	 * @throws CollectionException When some dependencies are invalid or cyclic.
	 */
	public static <T, L extends List<T>> L topologicalSort(final Collection<? extends T> values, final Function<? super T, ? extends Collection<T>> dependencyFunction, final L results)
	throws CollectionException {
		Assert.notNull(values);
		Assert.notNull(dependencyFunction);
		Assert.notNull(results);

		// Compute the dependencies.
		final Collection<Tuple2<T, T>> dependencies = new ArrayList<Tuple2<T, T>>();
		for (final T value : values) {
			try {
				for (final T dependencyValue : dependencyFunction.apply(value)) {
					if (values.contains(dependencyValue)) {
						dependencies.add(new Tuple2<T, T>(value, dependencyValue));
					} else {
						throw new CollectionException("Invalid dependency " + dependencyValue + " for value " + value);
					}
				}
			} catch (final ApplicationException exception) {
				throw new CollectionException("Failed computing dependencies for value " + value, exception);
			}
		}

		// Sort the values.
		final Collection<T> pendingValues = new ArrayList<T>(values);
		while (!pendingValues.isEmpty()) {
			// Find the leaves.
			final Set<T> leafValues = new HashSet<T>(pendingValues);
			for (final Tuple2<T, T> dependency : dependencies) {
				leafValues.remove(dependency.getFirst());
			}

			if (leafValues.isEmpty()) {
				throw new CollectionException("Cyclic dependencies for values " + pendingValues);
			}

			// Add the leaves to the result.
			// Pending values are iterated instead of leaf values to keep the sort stable.
			final Iterator<T> pendingValuesIt = pendingValues.iterator();
			while (pendingValuesIt.hasNext()) {
				final T value = pendingValuesIt.next();
				if (leafValues.contains(value)) {
					results.add(value);
					pendingValuesIt.remove();
				}
			}

			// Clean the dependencies.
			final Iterator<Tuple2<T, T>> dependenciesIt = dependencies.iterator();
			while (dependenciesIt.hasNext()) {
				final Tuple2<T, T> dependency = dependenciesIt.next();
				if (leafValues.contains(dependency.getSecond())) {
					dependenciesIt.remove();
				}
			}
		}

		return results;
	}

	/**
	 * Build a list containing the union of the given collections.
	 * <p>
	 * The items of the first given collection precede the items of the second one.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return The union list.
	 */
	public static <T> ArrayList<T> unionList(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		Assert.notNull(collection1);
		Assert.notNull(collection2);

		// Build the union.
		return union(collection1, collection2, new ArrayList<T>(collection1.size() + collection2.size()));
	}

	/**
	 * Build a set containing the union of the given collections.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return The union set.
	 */
	public static <T> HashSet<T> unionSet(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		// Build the union.
		return union(collection1, collection2, new HashSet<T>());
	}

	/**
	 * Build a collection containing the union of the given collections.
	 * <p>
	 * When the populated collection is ordered, the items of the first given collection precede the items of the second one.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the collection to instanciate.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param factory Factory to use to build the result collection.
	 * @return The union collection.
	 */
	public static <T, C extends Collection<T>> C union(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final CollectionFactory<? super T, ? extends C> factory) {
		Assert.notNull(collection1);
		Assert.notNull(factory);

		// Build the union.
		return union(collection1, collection2, factory.build(collection1.size()));
	}

	/**
	 * Build the union of the given collections and populate the given result collection with it.
	 * <p>
	 * When the populated collection is ordered, the items of the first given collection precede the items of the second one.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the populated collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param results The populated collection.
	 * @return The populated collection.
	 */
	public static <T, C extends Collection<T>> C union(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		Assert.notNull(collection1);
		Assert.notNull(collection2);
		Assert.notNull(results);

		// Build the union.
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}

	/**
	 * Build a map containing the union of the given maps.
	 * <p>
	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map1 First map.
	 * @param map2 Second map.
	 * @return The union map.
	 */
	public static <K, V> HashMap<K, V> unionMap(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2) {
		// Build the union.
		return union(map1, map2, new HashMap<K, V>());
	}

	/**
	 * Build a map containing the union of the given maps.
	 * <p>
	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to instanciate.
	 * @param map1 First map.
	 * @param map2 Second map.
	 * @param factory Factory to use to build the result map.
	 * @return The union map.
	 */
	public static <K, V, M extends Map<K, V>> M union(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final MapFactory<? super K, ? super V, ? extends M> factory) {
		Assert.notNull(map1);
		Assert.notNull(factory);

		// Build the union.
		return union(map1, map2, factory.build(map1.size()));
	}

	/**
	 * Build the union of the given maps and populate the given result map with it.
	 * <p>
	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the populated map.
	 * @param map1 First map.
	 * @param map2 Second map.
	 * @param results Map to populate with the results.
	 * @return The populated map.
	 */
	public static <K, V, M extends Map<K, V>> M union(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final M results) {
		Assert.notNull(map1);
		Assert.notNull(map2);
		Assert.notNull(results);

		// Build the union.
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
	public static <T> boolean intersects(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
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
	 * Build a list containing the intersection of the given collections.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return The intersection list.
	 */
	public static <T> ArrayList<T> intersectionList(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		// Build the intersection.
		return intersection(collection1, collection2, new ArrayList<T>());
	}

	/**
	 * Build a set containing the intersection of the given collections.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return The intersection set.
	 */
	public static <T> HashSet<T> intersectionSet(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		// Build the intersection.
		return intersection(collection1, collection2, new HashSet<T>());
	}

	/**
	 * Build a collection containing the intersection of the given collections.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the collection to instanciate.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param factory Factory to use to build the result collection.
	 * @return The intersection collection.
	 */
	public static <T, C extends Collection<T>> C intersection(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final CollectionFactory<? super T, ? extends C> factory) {
		Assert.notNull(factory);

		// Build the intersection.
		return intersection(collection1, collection2, factory.build());
	}

	/**
	 * Build the intersection of the given collections and populate the given result collection with it.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the populated collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param results Collection to populate with the results.
	 * @return The populated collection.
	 */
	public static <T, C extends Collection<T>> C intersection(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		Assert.notNull(collection1);
		Assert.notNull(collection2);
		Assert.notNull(results);

		// Build the intersection.
		for (final T value : collection1) {
			if (collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}

	/**
	 * Build a list containing the exclusion of the second given collection from the first given collection.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return The exclusion list.
	 */
	public static <T> ArrayList<T> exclusionList(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		// Build the exclusion.
		return exclusion(collection1, collection2, new ArrayList<T>());
	}

	/**
	 * Build a set containing the exclusion of the given collections (first minus second).
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return The exclusion list.
	 */
	public static <T> HashSet<T> exclusionSet(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		// Build the exclusion.
		return exclusion(collection1, collection2, new HashSet<T>());
	}

	/**
	 * Build a collection containing the exclusion of the given collections (first minus second).
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection to instanciate.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param factory Factory to use to build the result collection.
	 * @return The exclusion collection.
	 */
	public static <T, C extends Collection<T>> C exclusion(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final CollectionFactory<? super T, ? extends C> factory) {
		Assert.notNull(factory);

		// Build the exclusion.
		return exclusion(collection1, collection2, factory.build());
	}

	/**
	 * Build the exclusion of given collections (first minus second) and populate the given results collection with it.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the populated collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param results The collection to populate with the results.
	 * @return The populated collection.
	 */
	public static <T, C extends Collection<T>> C exclusion(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		// Checks
		Assert.notNull(collection1);
		Assert.notNull(collection2);
		Assert.notNull(results);

		// Build the exclusion.
		for (final T value : collection1) {
			if (!collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}

	/**
	 * Build a map containing the bindings of the given map with the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to copy.
	 * @param keys Keys of the bindings to retain.
	 * @return The sub map.
	 */
	public static <K, V> HashMap<K, V> subMap(final Map<? extends K, ? extends V> map, final Set<? extends K> keys) {
		Assert.notNull(keys);

		// Build the map.
		return subMap(map, keys, new HashMap<K, V>(keys.size()));
	}

	/**
	 * Build a map containing the bindings of the given map with the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to instanciate.
	 * @param map Map to copy.
	 * @param keys Keys of the bindings to retain.
	 * @param factory Factory to use to build the result map.
	 * @return The sub map.
	 */
	public static <K, V, M extends Map<K, V>> M subMap(final Map<? extends K, ? extends V> map, final Set<? extends K> keys, final MapFactory<? super K, ? super V, ? extends M> factory) {
		Assert.notNull(factory);

		// Build the map.
		return subMap(map, keys, factory.build());
	}

	/**
	 * Get the bindings of the given map with the given keys and populate the given result map with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the populated map.
	 * @param map Map to copy.
	 * @param keys Keys of the bindings to retain.
	 * @param results Map to populate with the bindings.
	 * @return The populated map.
	 */
	public static <K, V, M extends Map<K, V>> M subMap(final Map<? extends K, ? extends V> map, final Set<? extends K> keys, final M results) {
		Assert.notNull(map);
		Assert.notNull(keys);
		Assert.notNull(results);

		// Build the map.
		for (final K key : keys) {
			if (map.containsKey(key)) {
				final V value = map.get(key);
				results.put(key, value);
			}
		}
		return results;
	}

	/**
	 * Build a map containing the bindings of the given map whose keys do not belong to the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to copy.
	 * @param keys Keys of the binding to exclude.
	 * @return The exclusion map.
	 */
	public static <K, V> HashMap<K, V> retainMap(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys) {
		// Build the exclusion.
		return retainMap(map, keys, new HashMap<K, V>());
	}

	/**
	 * Build a map containing the bindings of the given map whose keys do not belong to the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to instanciate.
	 * @param map Map to copy.
	 * @param keys Keys of the binding to exclude.
	 * @param factory Factory to use to build the result map.
	 * @return The exclusion map.
	 */
	public static <K, V, M extends Map<K, V>> M retainMap(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys, final MapFactory<? super K, ? super V, ? extends M> factory) {
		Assert.notNull(factory);

		// Build the exclusion.
		return retainMap(map, keys, factory.build());
	}

	/**
	 * Get the bindings of the given map whose keys do not belong to the given keys and populate the given result map with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the populated map.
	 * @param map Map to copy.
	 * @param keys Keys of the binding to exclude.
	 * @param results Map to populate with the results.
	 * @return The populated map.
	 */
	public static <K, V, M extends Map<K, V>> M retainMap(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys, final M results) {
		Assert.notNull(map);
		Assert.notNull(keys);
		Assert.notNull(results);

		// Build the exclusion.
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			if (!keys.contains(key)) {
				results.put(key, entry.getValue());
			}
		}
		return results;
	}

	private CollectionUtils() {
		// Prevent instantiation.
	}
}
