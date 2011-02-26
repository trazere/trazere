/*
 *  Copyright 2006-2011 Julien Dufour
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
import java.io.InputStream;

/**
 * DOCME
 */
public class DecoratorInputStream
extends InputStream {
	private final InputStream _stream;
	
	public DecoratorInputStream(final InputStream stream) {
		assert null != stream;
		
		// Initialization.
		_stream = stream;
	}
	
	public InputStream getStream() {
		return _stream;
	}
	
	@Override
	public int available()
	throws IOException {
		return _stream.available();
	}
	
	@Override
	public int read()
	throws IOException {
		return _stream.read();
	}
	
	@Override
    public int read(final byte[] b)
    throws IOException {
    	return _stream.read(b);
    }

	@Override
	public int read(final byte[] b, final int off, final int len)
	throws IOException {
		return _stream.read(b, off, len);
	}
	
	@Override
	public long skip(final long n)
	throws IOException {
		return _stream.skip(n);
	}
	
	@Override
	public boolean markSupported() {
		return _stream.markSupported();
	}
	
	@Override
	public synchronized void mark(final int readlimit) {
		_stream.mark(readlimit);
	}
	
	@Override
	public synchronized void reset()
	throws IOException {
		_stream.reset();
	}
	
	@Override
	public void close()
	throws IOException {
		_stream.close();
	}
}
