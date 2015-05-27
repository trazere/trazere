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
package com.trazere.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * The {@link InputUtils} class provides various utilities regarding {@link Input inputs}.
 * 
 * @see Input
 */
public class InputUtils {
	/**
	 * Builds an input that provides some XML content transformed using some XSLT transformation.
	 * 
	 * @param input Input providing the XML content to transform.
	 * @param transformation Input providing the XSLT transformation to apply.
	 * @return The built input.
	 */
	public static Input transform(final Input input, final Input transformation) {
		assert null != input;
		assert null != transformation;
		
		return new Input() {
			private final TransformerFactory _transformerFactory = TransformerFactory.newInstance();
			
			@Override
			public boolean exists()
			throws IOException {
				return input.exists();
			}
			
			@Override
			public InputStream open()
			throws IOException {
				try {
					if (transformation.exists()) {
						// Transform the input.
						final ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
						final InputStream inputStream = input.open();
						try {
							final InputStream transformationStream = transformation.open();
							try {
								final Transformer transformer = _transformerFactory.newTransformer(new StreamSource(transformationStream));
								transformer.transform(new StreamSource(inputStream), new StreamResult(resultStream));
							} finally {
								transformationStream.close();
							}
						} finally {
							inputStream.close();
						}
						
						// Build the result.
						return new ByteArrayInputStream(resultStream.toByteArray());
					} else {
						// Open the input.
						return input.open();
					}
				} catch (final TransformerException exception) {
					throw new IOException("Failed loading configuration " + input, exception);
				}
			}
		};
	}
	
	private InputUtils() {
		// Prevent instantiation.
	}
}
