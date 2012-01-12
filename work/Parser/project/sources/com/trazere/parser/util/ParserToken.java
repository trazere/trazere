package com.trazere.parser.util;

import com.trazere.util.function.Function0;

/**
 * DOCME
 * 
 * @param <R>
 * @param <V>
 * @param <X>
 */
public interface ParserToken<R, V, X extends Exception>
extends Function0<V, X> {
	public R getRepresentation();
	
	public V parse()
	throws X;
}
