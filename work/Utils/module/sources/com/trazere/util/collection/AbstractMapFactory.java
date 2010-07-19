package com.trazere.util.collection;

import com.trazere.util.lang.AbstractFactory;
import java.util.Map;

/**
 * The {@link AbstractMapFactory} class implements skeletons of {@link MapFactory map factories}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <M> Type of the maps.
 */
public abstract class AbstractMapFactory<K, V, M extends Map<? super K, ? super V>>
extends AbstractFactory<M, RuntimeException>
implements MapFactory<K, V, M> {
	// Nothing to do.
}
