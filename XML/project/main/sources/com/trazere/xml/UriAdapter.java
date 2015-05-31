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
package com.trazere.xml;

import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link UriAdapter} class implements JAXB adpaters for URIs.
 * 
 * @since 1.0
 */
public class UriAdapter
extends XmlAdapter<String, URI> {
	@Override
	public URI unmarshal(final String value)
	throws IllegalArgumentException {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final URI value) {
		return null != value ? format(value) : null;
	}
	
	/**
	 * Parses the given URI.
	 * 
	 * @param representation Representation to parse.
	 * @return The parsed URI.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @since 1.0
	 */
	public static URI parse(final String representation)
	throws IllegalArgumentException {
		try {
			return new URI(representation);
		} catch (final URISyntaxException exception) {
			throw new IllegalArgumentException("Invalid URI representation \"" + representation + "\"", exception);
		}
	}
	
	/**
	 * Formats the given URI.
	 * 
	 * @param value URI to format.
	 * @return The formatted representation.
	 * @since 1.0
	 */
	public static String format(final URI value) {
		return value.toString();
	}
}
