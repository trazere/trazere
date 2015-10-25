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
package com.trazere.core.net;

import com.trazere.core.functional.Function;
import com.trazere.core.lang.ThrowableFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The {@link NetFunctions} class provides various factories of {@link Function functions} related to networks.
 * 
 * @see Function
 * @since 2.0
 */
public class NetFunctions {
	/**
	 * Builds a function that builds URLs from string representations.
	 * 
	 * @param invalidFactory Factory of the exceptions for the invalid URLs.
	 * @return The built function.
	 * @since 2.0
	 */
	public static Function<String, URL> url(final ThrowableFactory<? extends RuntimeException> invalidFactory) {
		assert null != invalidFactory;
		
		return (final String representation) -> {
			try {
				return new URL(representation);
			} catch (final MalformedURLException exception) {
				throw invalidFactory.build("Invalid URL \"" + representation + "\"", exception);
			}
		};
	}
	
	/**
	 * Builds a function that builds URIs from string representations.
	 * 
	 * @param invalidFactory Factory of the exceptions for the invalid URIs.
	 * @return The built function.
	 * @since 2.0
	 */
	public static Function<String, URI> uri(final ThrowableFactory<? extends RuntimeException> invalidFactory) {
		assert null != invalidFactory;
		
		return (final String representation) -> {
			try {
				return new URI(representation);
			} catch (final URISyntaxException exception) {
				throw invalidFactory.build("Invalid URI \"" + representation + "\"", exception);
			}
		};
	}
	
	private NetFunctions() {
		// Prevents instantiation.
	}
}
