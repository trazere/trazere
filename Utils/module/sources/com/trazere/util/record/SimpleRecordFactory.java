package com.trazere.util.record;

import java.util.Map;

/**
 * The {@link SimpleRecordFactory} class implements factories of {@link SimpleRecord simple records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecord
 */
public class SimpleRecordFactory<K, V>
implements RecordFactory<K, V, SimpleRecord<K, V>> {
	private static final SimpleRecordFactory<?, ?> _FACTORY = new SimpleRecordFactory<Object, Object>();
	
	/**
	 * Build a simple record factory.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> SimpleRecordFactory<K, V> factory() {
		return (SimpleRecordFactory<K, V>) _FACTORY;
	}
	
	public SimpleRecord<K, V> build()
	throws RecordException {
		return SimpleRecord.build();
	}
	
	public SimpleRecord<K, V> build(final Map<K, V> fields)
	throws RecordException {
		return SimpleRecord.build(fields);
	}
}
