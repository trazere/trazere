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

import java.io.IOException;
import java.io.OutputStream;

/**
 * The {@link CarbonCopyOutputStream} class carbon copies the data written to a destination output stream to another output stream.
 */
public class CarbonCopyOutputStream
extends OutputStream {
	/** Destination output stream receiving the data. */
	protected final OutputStream _output;
	
	/** Copy output stream receiving the copy of the data. */
	protected final OutputStream _copy;
	
	/**
	 * Instantiate a new stream with the given destination and copy output streams.
	 * 
	 * @param output Destination output stream receiving the data.
	 * @param copy Copy output stream receiving the copy of the data.
	 */
	public CarbonCopyOutputStream(final OutputStream output, final OutputStream copy) {
		assert null != output;
		assert null != copy;
		
		// Initialization.
		_output = output;
		_copy = copy;
	}
	
	/**
	 * Get the destination output stream receiving the data.
	 * 
	 * @return The output stream.
	 */
	public OutputStream getOutput() {
		return _output;
	}
	
	/**
	 * Get the copy output stream receiving the copy of the data.
	 * 
	 * @return The output stream.
	 */
	public OutputStream getCopy() {
		return _copy;
	}
	
	@Override
	public void write(final int b)
	throws IOException {
		_output.write(b);
		_copy.write(b);
	}
	
	@Override
	public void write(final byte[] b)
	throws IOException {
		_output.write(b);
		_copy.write(b);
	}
	
	@Override
	public void write(final byte[] b, final int off, final int len)
	throws IOException {
		_output.write(b, off, len);
		_copy.write(b, off, len);
	}
	
	@Override
	public void flush()
	throws IOException {
		_output.flush();
		_copy.flush();
	}
	
	@Override
	public void close()
	throws IOException {
		_output.close();
	}
}
