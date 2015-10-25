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

import com.trazere.core.util.Serializer;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link SerializerAdapter} abstract class implements JAXB adpaters based an a serializer.
 * 
 * @param <T> Type of the values.
 * @see Serializer
 * @since 2.0
 */
public class SerializerAdapter<T>
extends XmlAdapter<String, T> {
	/**
	 * Serializer to use.
	 * 
	 * @since 2.0
	 */
	protected final Serializer<T, String> _serializer;
	
	/**
	 * Instantiates a new adapter.
	 * 
	 * @param serializer Serializer to use.
	 * @since 2.0
	 */
	public SerializerAdapter(final Serializer<T, String> serializer) {
		assert null != serializer;
		
		// Initialization.
		_serializer = serializer;
	}
	
	@Override
	public T unmarshal(final String value) {
		return null != value ? _serializer.deserialize(value) : null;
	}
	
	@Override
	public String marshal(final T value) {
		return null != value ? _serializer.serialize(value) : null;
	}
}
