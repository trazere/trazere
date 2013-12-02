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

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.annotation.adapters.XmlAdapter;

// TODO: move serialization methods in adapters in trazere
// TODO: handle null correctly in adpaters in trazere

/**
 * The {@link UrlAdapter} class provides JAXB adpaters for URLs.
 */
public class UrlAdapter
extends XmlAdapter<String, URL> {
	@Override
	public URL unmarshal(final String value)
	throws IllegalArgumentException {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final URL value) {
		return null != value ? print(value) : null;
	}
	
	/**
	 * Parses the given url.
	 * 
	 * @param representation Representation to parse.
	 * @return The url.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static URL parse(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		try {
			return new URL(representation);
		} catch (final MalformedURLException exception) {
			throw new IllegalArgumentException("Invalid url representation \"" + representation + "\"", exception);
		}
	}
	
	/**
	 * Formats the given url.
	 * 
	 * @param url Url to print.
	 * @return The representation.
	 */
	public static String print(final URL url) {
		assert null != url;
		
		return url.toExternalForm();
	}
}
