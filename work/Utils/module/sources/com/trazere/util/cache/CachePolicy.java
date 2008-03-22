package com.trazere.util.cache;

/**
 * The <code>CachePolicies</code> enum represents the various supported cache policies.
 */
public enum CachePolicy {
	/** Policy for no cache. The cache will contain no objets. */
	NONE,
	
	/** Policy for a complete cache. The cache will contain all objets. */
	COMPLETE,
	
	/** Policy for a progressive cache. The cache fill progressively when the objets are used. */
	PROGRESSIVE,
}
