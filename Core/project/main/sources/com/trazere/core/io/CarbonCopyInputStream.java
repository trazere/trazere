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
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The {@link CarbonCopyInputStream} class implements input streams that writes a copy of the data read from a source input stream to an output stream.
 * 
 * @since 2.0
 */
public class CarbonCopyInputStream
extends DecoratorInputStream {
	/**
	 * Instantiates a new carbon copy stream.
	 * 
	 * @param source Input stream providing the data.
	 * @param copy Output stream receiving the copy of the read data.
	 * @since 2.0
	 */
	public CarbonCopyInputStream(final InputStream source, final OutputStream copy) {
		super(source);
		
		// Checks.
		assert null != copy;
		
		// Initialization.
		_copy = copy;
	}
	
	// Copy.
	
	/**
	 * Output stream receiving the copy of the data.
	 * 
	 * @since 2.0
	 */
	protected final OutputStream _copy;
	
	/**
	 * Gets the output stream receiving the copy of the data read from this stream.
	 * 
	 * @return The copy output stream.
	 * @since 2.0
	 */
	public OutputStream getCopy() {
		return _copy;
	}
	
	// Input stream.
	
	@Override
	public int read()
	throws IOException {
		final int b = super.read();
		if (-1 != b) {
			_copy.write(b);
		}
		return b;
	}
	
	@Override
	public int read(final byte[] b)
	throws IOException {
		final int n = super.read(b);
		if (n > 0) {
			_copy.write(b, 0, n);
		}
		return n;
	}
	
	@Override
	public int read(final byte[] b, final int off, final int len)
	throws IOException {
		final int n = super.read(b, off, len);
		if (n > 0) {
			_copy.write(b, off, n);
		}
		return n;
	}
}
