/*
 *  Copyright 2006-2015 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.util.net;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.ThrowableFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The {@link NetFunctions} class provides factories of functions related to the net.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class NetFunctions {
	/**
	 * Builds a function that build URL from their string representation.
	 * 
	 * @param <X> Type of the thrown exceptions.
	 * @param throwableFactory Factory that builds the thrown exceptions.
	 * @return A function that makes a URL from its string representation.
	 * @deprecated Use {@link com.trazere.core.net.NetFunctions#url(com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static <X extends Exception> Function1<String, URL, X> url(final ThrowableFactory<? extends X> throwableFactory) {
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
	 * @deprecated Use {@link com.trazere.core.net.NetFunctions#uri(com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static <X extends Exception> Function1<String, URI, X> uri(final ThrowableFactory<? extends X> throwableFactory) {
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
