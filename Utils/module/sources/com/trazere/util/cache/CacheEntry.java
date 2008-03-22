package com.trazere.util.cache;

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The <code>CacheEntry</code> class represents cache entries, which are associated key/value couples.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class CacheEntry<K, V>
implements Describable {
	/** Key of the entry. May be <code>null</code>. */
	protected final K _key;
	
	/** Value of the entry. May be <code>null</code>. */
	protected final V _value;
	
	/**
	 * Instantiate an new entry with the given key and value.
	 * 
	 * @param key Key of the entry. May be <code>null</code>.
	 * @param value Value of the entry. May be <code>null</code>.
	 */
	protected CacheEntry(final K key, final V value) {
		// Initialization.
		_key = key;
		_value = value;
	}
	
	/**
	 * Get the key of the receiver entry.
	 * 
	 * @return The key object. May be <code>null</code>.
	 */
	public K getKey() {
		return _key;
	}
	
	/**
	 * Get the value of the entry.
	 * 
	 * @return The value object. May be <code>null</code>.
	 */
	public V getValue() {
		return _value;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Key = ").append(null != _key ? _key.toString() : "n/a");
		builder.append(" - Value = ").append(null != _value ? _value.toString() : "n/a");
	}
}
