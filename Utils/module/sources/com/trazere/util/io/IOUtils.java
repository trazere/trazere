/*
 *  Copyright 2008 Julien Dufour
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

import com.trazere.util.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * DOCME
 */
public class IOUtils {
	/**
	 * Read the data from the given input stream and write it to the given output stream.
	 * 
	 * @param input Input stream to read.
	 * @param output Output stream to write into.
	 * @throws IOException
	 */
	public static void copyStream(final InputStream input, final OutputStream output)
	throws IOException {
		Assert.notNull(input);
		Assert.notNull(output);
		
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
	 * Read the text from the given reader and write it to the given writer.
	 * 
	 * @param reader Reader to read from.
	 * @param writer Writer to write into.
	 * @throws IOException
	 */
	public static void copyText(final Reader reader, final Writer writer)
	throws IOException {
		Assert.notNull(reader);
		Assert.notNull(writer);
		
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
	
	private IOUtils() {
		// Prevent instantiation.
	}
}