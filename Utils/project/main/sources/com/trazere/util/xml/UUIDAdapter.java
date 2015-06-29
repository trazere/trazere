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
package com.trazere.util.xml;

import java.util.UUID;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link UUIDAdapter} class provides JAXB adpaters for UUIDs.
 * 
 * @deprecated Use {@link com.trazere.xml.UUIDAdapter}.
 */
@Deprecated
public class UUIDAdapter
extends XmlAdapter<String, UUID> {
	@Override
	public UUID unmarshal(final String value) {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final UUID value) {
		return null != value ? print(value) : null;
	}
	
	/**
	 * Parses the given UUID.
	 * 
	 * @param representation Representation to parse.
	 * @return The UUID.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @deprecated Use {@link com.trazere.xml.UUIDAdapter#parse(String)}.
	 */
	@Deprecated
	public static UUID parse(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		return UUID.fromString(representation);
	}
	
	/**
	 * Formats the given UUID.
	 * 
	 * @param value UUID to format.
	 * @return The representation.
	 * @deprecated Use {@link com.trazere.xml.UUIDAdapter#format(UUID)}.
	 */
	@Deprecated
	public static String print(final UUID value) {
		assert null != value;
		
		return value.toString();
	}
}
