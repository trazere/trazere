/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.util.xml;

import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link UriAdapter} class provides JAXB adpaters for URIs.
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
		return null != value ? print(value) : null;
	}
	
	/**
	 * Parses the given uri.
	 * 
	 * @param representation Representation to parse.
	 * @return The uri.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static URI parse(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		try {
			return new URI(representation);
		} catch (final URISyntaxException exception) {
			throw new IllegalArgumentException("Invalid uri representation \"" + representation + "\"", exception);
		}
	}
	
	/**
	 * Formats the given uri.
	 * 
	 * @param value Uri to format.
	 * @return The representation.
	 */
	public static String print(final URI value) {
		assert null != value;
		
		return value.toString();
	}
}
