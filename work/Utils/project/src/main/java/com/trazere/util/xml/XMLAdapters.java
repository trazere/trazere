package com.trazere.util.xml;

import com.trazere.util.value.ValueSerializer;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link XMLAdapters} class provides various factories of JAXB type adapters.
 */
public class XMLAdapters {
	/**
	 * Builds an adapter using the given value serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the JAXB representations.
	 * @param serializer The serializer to use.
	 * @return The built adapter.
	 */
	public static <T, R> XmlAdapter<R, T> fromValueSerializer(final ValueSerializer<T, R, ? extends RuntimeException> serializer) {
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
