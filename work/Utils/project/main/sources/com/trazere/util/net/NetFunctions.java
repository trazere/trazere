package com.trazere.util.net;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.ThrowableFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The {@link NetFunctions} class provides factories of functions related to the net.
 */
public class NetFunctions {
	/**
	 * Builds a function that build URL from their string representation.
	 * 
	 * @param <X> Type of the thrown exceptions.
	 * @param throwableFactory Factory that builds the thrown exceptions.
	 * @return A function that makes a URL from its string representation.
	 */
	public static <X extends Exception> Function1<String, URL, X> url(final ThrowableFactory<X> throwableFactory) {
		assert null != throwableFactory;
		
		return new Function1<String, URL, X>() {
			@Override
			public URL evaluate(final String representation)
			throws X {
				try {
					return new URL(representation);
				} catch (final MalformedURLException exception) {
					throw throwableFactory.build("Invalid URL \"" + representation + "\"", exception);
				}
			}
		};
	}
	
	/**
	 * Builds a function that build URI from their string representation.
	 * 
	 * @param <X> Type of the thrown exceptions.
	 * @param throwableFactory Factory that builds the thrown exceptions.
	 * @return A function that makes a URI from its string representation.
	 */
	public static <X extends Exception> Function1<String, URI, X> uri(final ThrowableFactory<X> throwableFactory) {
		assert null != throwableFactory;
		
		return new Function1<String, URI, X>() {
			@Override
			public URI evaluate(final String representation)
			throws X {
				try {
					return new URI(representation);
				} catch (final URISyntaxException exception) {
					throw throwableFactory.build("Invalid URI \"" + representation + "\"", exception);
				}
			}
		};
	}
	
	private NetFunctions() {
		// Prevents instantiation.
	}
}
