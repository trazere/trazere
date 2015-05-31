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
package com.trazere.core.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The {@link InputStreamUtils} class provides various utilities regarding {@link InputStream input streams}.
 * 
 * @see InputStream
 * @since 1.0
 */
public class InputStreamUtils {
	/**
	 * Copies all available data from the given input stream into the given output stream.
	 * <p>
	 * This methods returns when the end-of-file of the input is reached.
	 * 
	 * @param input Input stream from which the data should be read.
	 * @param output Output stream into which the data should be writen.
	 * @throws IOException When some data cannot be read.
	 * @throws IOException When some data cannot be written.
	 * @since 1.0
	 */
	public static void copy(final InputStream input, final OutputStream output)
	throws IOException {
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
	 * Reads all available data from the given reader.
	 * 
	 * @param input Input stream from which the data should be read.
	 * @return The read text.
	 * @throws IOException When some text cannot be read.
	 * @since 1.0
	 */
	public static byte[] read(final InputStream input)
	throws IOException {
		final ByteArrayOutputStream result = new ByteArrayOutputStream();
		copy(input, result);
		return result.toByteArray();
	}
	
	private InputStreamUtils() {
		// Prevent instantiation.
	}
}
