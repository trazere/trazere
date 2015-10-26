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
package com.trazere.util.io;

import com.trazere.util.function.Function1;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * The {@link IOUtils} class provides various utilities regarding I/O.
 */
public class IOUtils {
	/**
	 * Builds a function which builds files.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link IOFunctions#file()}. (since 1.0)
	 */
	@Deprecated
	public static <X extends Exception> Function1<String, File, X> buildFileFunction() {
		return IOFunctions.file();
	}
	
	/**
	 * Reads all data from the given input stream and write it to the given output stream.
	 * 
	 * @param input Input stream to read.
	 * @param output Output stream to write into.
	 * @throws IOException When the input cannot be read.
	 * @throws IOException When the output cannot be written.
	 */
	public static void copyStream(final InputStream input, final OutputStream output)
	throws IOException {
		assert null != input;
		assert null != output;
		
		// Copy.
		final byte[] buffer = new byte[512];
		while (true) {
			final int n = input.read(buffer);
			if (n > 0) {
				output.write(buffer, 0, n);
			} else {
				break;
			}
		}
	}
	
	/**
	 * Reads all text from the given reader and write it to the given writer.
	 * 
	 * @param reader Reader to read from.
	 * @param writer Writer to write into.
	 * @throws IOException When the reader cannot be read.
	 * @throws IOException When the writer cannot be written.
	 */
	public static void copyText(final Reader reader, final Writer writer)
	throws IOException {
		assert null != reader;
		assert null != writer;
		
		// Copy.
		final char[] buffer = new char[512];
		while (true) {
			final int n = reader.read(buffer);
			if (n > 0) {
				writer.write(buffer, 0, n);
			} else {
				break;
			}
		}
	}
	
	/**
	 * Reads all text from the given reader.
	 * 
	 * @param reader Reader to read from.
	 * @return The read text.
	 * @throws IOException The
	 */
	public static String readText(final Reader reader)
	throws IOException {
		final StringWriter result = new StringWriter();
		copyText(reader, result);
		return result.toString();
	}
	
	/**
	 * Maps the contents of the given XML input with an XSLT transformation.
	 * 
	 * @param input The input providing the content to map.
	 * @param transformation The input providing the contents of the XSLT.
	 * @return The built input.
	 */
	public static Input transform(final Input input, final Input transformation) {
		assert null != input;
		assert null != transformation;
		
		return new Input() {
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
	
	private static final TransformerFactory _transformerFactory = TransformerFactory.newInstance();
	
	private IOUtils() {
		// Prevents instantiation.
	}
}
