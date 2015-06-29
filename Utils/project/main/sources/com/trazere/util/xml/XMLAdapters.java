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

import com.trazere.util.value.ValueSerializer;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link XMLAdapters} class provides various factories of JAXB type adapters.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class XMLAdapters {
	/**
	 * Builds an adapter using the given value serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the JAXB representations.
	 * @param serializer The serializer to use.
	 * @return The built adapter.
	 * @deprecated Use {@link com.trazere.xml.SerializerAdapter}.
	 */
	@Deprecated
	public static <T, R> XmlAdapter<R, T> fromValueSerializer(final ValueSerializer<T, R, ?> serializer) {
		assert null != serializer;
		
		return new XmlAdapter<R, T>() {
			@Override
			public T unmarshal(final R representation)
			throws Exception {
				return serializer.deserialize(representation);
			}
			
			@Override
			public R marshal(final T value)
			throws Exception {
				return serializer.serialize(value);
			}
		};
	}
	
	private XMLAdapters() {
		// Prevents instantiation.
	}
}
