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

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * The {@link ReaderUtils} class provides various utilities regarding {@link Reader readers}.
 * 
 * @see Reader
 * @since 1.0
 */
public class ReaderUtils {
	/**
	 * Copies all available text from the given reader into the given writer.
	 * <p>
	 * This methods returns when the end-of-file of the input is reached.
	 * 
	 * @param input Reader from which the text should be read.
	 * @param output Writer into which the text should be writen.
	 * @throws IOException When some text cannot be read.
	 * @throws IOException When some text cannot be written.
	 * @since 1.0
	 */
	public static void copy(final Reader input, final Writer output)
	throws IOException {
		final char[] buffer = new char[512];
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
	 * Reads all available text from the given reader.
	 * 
	 * @param reader Reader from which the text should be read.
	 * @return The read text.
	 * @throws IOException When some text cannot be read.
	 * @since 1.0
	 */
	public static String read(final Reader reader)
	throws IOException {
		final StringWriter result = new StringWriter();
		copy(reader, result);
		return result.toString();
	}
	
	private ReaderUtils() {
		// Prevent instantiation.
	}
}
