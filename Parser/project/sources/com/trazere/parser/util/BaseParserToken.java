package com.trazere.parser.util;

/**
 * DOCME
 * 
 * @param <R>
 * @param <V>
 * @param <X>
 */
public abstract class BaseParserToken<R, V, X extends Exception>
implements ParserToken<R, V, X> {
	public BaseParserToken(final R representation) {
		assert null != representation;
		
		// Initialization.
		_representation = representation;
	}
	
	// Representation.
	
	private final R _representation;
	
	public R getRepresentation() {
		return _representation;
	}
	
	// Parse.
	
	public V parse()
	throws X {
		return parse(_representation);
	}
	
	protected abstract V parse(final R representation)
	throws X;
	
	// Function.
	
	public V evaluate()
	throws X {
		return parse();
	}
}
