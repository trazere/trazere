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

import com.trazere.core.lang.MutableLong;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The {@link TruncatedOutputStream} class implements output streams that truncate the written data after some maximal length.
 */
public class TruncatedOutputStream
extends DecoratorOutputStream {
	private final MutableLong _remaining;
	
	/**
	 * Instantiates a new truncated stream.
	 * 
	 * @param stream Output stream receiving the data.
	 * @param length Maximal length of data to write.
	 */
	public TruncatedOutputStream(final OutputStream stream, final long length) {
		super(stream);
		
		// Checks.
		assert length >= 0;
		
		// Initialization.
		_remaining = new MutableLong(length);
	}
	
	@Override
	public void write(final int b)
	throws IOException {
		if (_remaining.get() > 0) {
			super.write(b);
			_remaining.sub(1);
		}
	}
	
	@Override
	public void write(final byte[] b)
	throws IOException {
		write(b, 0, b.length);
	}
	
	@Override
	public void write(final byte[] b, final int off, final int len)
	throws IOException {
		final int truncatedLen = (int) Math.min(len, _remaining.get());
		super.write(b, off, truncatedLen);
		_remaining.sub(truncatedLen);
	}
}
