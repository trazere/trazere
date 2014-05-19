package com.trazere.util.record;

import com.trazere.util.lang.InverseComparator;
import com.trazere.util.lang.SequenceComparator;
import com.trazere.util.type.Tuple2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link RecordComparators} class provides various factories of record comparators.
 */
public class RecordComparators {
	/**
	 * Build a record comparator according to the values of the fields identified by the given keys and the given order using the given comparator.
	 * <p>
	 * The given order is applied to every compared field.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the records.
	 * @param criterion List of the keys of the fields used for compararison in the order of importance.
	 * @param ascending <code>true</code> to sort in the ascending order, <code>false</code> to sort in the descending order.
	 * @param comparator Comparator of the values of the fields.
	 * @return The comparator.
	 */
	public static <K, V, R extends Record<? super K, ? extends V>> Comparator<R> criterion(final List<K> criterion, final boolean ascending, final Comparator<V> comparator) {
		assert null != criterion;
		assert null != comparator;
		
		// Build the comparator.
		final List<Comparator<R>> comparators = new ArrayList<Comparator<R>>();
		for (final K criteria : criterion) {
			comparators.add(InverseComparator.build(new RecordComparator<K, V, R>(criteria, comparator), ascending));
		}
		return new SequenceComparator<R>(comparators);
	}
	
	/**
	 * Build a record comparator according to the values of the fields identified by the given keys and the given orders using the given comparator.
	 * <p>
	 * A specific order is applied to every compared field.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the records.
	 * @param criterion List of the keys of the fields used for compararison and of their order in the order of importance.
	 * @param comparator Comparator of the values of the fields.
	 * @return The comparator.
	 */
	public static <K, V, R extends Record<? super K, ? extends V>> Comparator<R> criterion(final List<Tuple2<K, Boolean>> criterion, final Comparator<V> comparator) {
		assert null != criterion;
		
		// Build the comparator.
		final List<Comparator<R>> comparators = new ArrayList<Comparator<R>>();
		for (final Tuple2<K, Boolean> criteria : criterion) {
			comparators.add(InverseComparator.build(new RecordComparator<K, V, R>(criteria.getFirst(), comparator), criteria.getSecond().booleanValue()));
		}
		return new SequenceComparator<R>(comparators);
	}
	
	private RecordComparators() {
		// Prevents instantiation.
	}
}
