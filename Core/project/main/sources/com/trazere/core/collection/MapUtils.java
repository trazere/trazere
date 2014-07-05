package com.trazere.core.collection;

import com.trazere.core.util.Tuple2;
import java.util.Map;

/**
 * The {@link MapUtils} class provides various utilities regarding {@link Map maps}.
 * 
 * @see Map
 */
public class MapUtils {
	/**
	 * Puts all given bindings into the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to populate.
	 * @param bindings Bindings to put.
	 */
	public static <K, V> void putAll(final Map<? super K, ? super V> map, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			map.put(binding.get1(), binding.get2());
		}
	}
	
	private MapUtils() {
		// Prevent instantiation.
	}
}
