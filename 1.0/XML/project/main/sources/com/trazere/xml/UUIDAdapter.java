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

import java.util.UUID;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link UUIDAdapter} class implements JAXB adpaters for UUIDs.
 * 
 * @since 1.0
 */
public class UUIDAdapter
extends XmlAdapter<String, UUID> {
	@Override
	public UUID unmarshal(final String value) {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final UUID value) {
		return null != value ? format(value) : null;
	}
	
	/**
	 * Parses the given UUID.
	 * 
	 * @param representation Representation to parse.
	 * @return The parsed UUID.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @since 1.0
	 */
	public static UUID parse(final String representation)
	throws IllegalArgumentException {
		return UUID.fromString(representation);
	}
	
	/**
	 * Formats the given UUID.
	 * 
	 * @param value UUID to format.
	 * @return The formatted representation.
	 * @since 1.0
	 */
	public static String format(final UUID value) {
		return value.toString();
	}
}
