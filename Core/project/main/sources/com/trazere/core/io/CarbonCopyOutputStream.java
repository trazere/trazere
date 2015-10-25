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
import java.io.OutputStream;

/**
 * The {@link CarbonCopyOutputStream} class implements output streams that writes a copy of the data written to a target output stream to another output stream.
 */
public class CarbonCopyOutputStream
extends DecoratorOutputStream {
	/**
	 * Instantiates a new carbon copy stream.
	 * 
	 * @param target Output stream receiving the data.
	 * @param copy Output stream receiving the copy of the written data.
	 * @since 2.0
	 */
	public CarbonCopyOutputStream(final OutputStream target, final OutputStream copy) {
		super(target);
		
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
	 * Gets the output stream receiving the copy of the data written in this stream.
	 * 
	 * @return The copy output stream.
	 * @since 2.0
	 */
	public OutputStream getCopy() {
		return _copy;
	}
	
	// Output stream.
	
	@Override
	public void write(final int b)
	throws IOException {
		super.write(b);
		_copy.write(b);
	}
	
	@Override
	public void write(final byte[] b)
	throws IOException {
		super.write(b);
		_copy.write(b);
	}
	
	@Override
	public void write(final byte[] b, final int off, final int len)
	throws IOException {
		super.write(b, off, len);
		_copy.write(b, off, len);
	}
	
	@Override
	public void flush()
	throws IOException {
		super.flush();
		_copy.flush();
	}
}
