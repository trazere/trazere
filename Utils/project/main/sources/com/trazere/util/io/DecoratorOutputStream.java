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
package com.trazere.util.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * DOCME
 * 
 * @deprecated Use {@link com.trazere.core.io.DecoratorOutputStream}.
 */
@Deprecated
public class DecoratorOutputStream
extends OutputStream {
	protected final OutputStream _stream;
	
	/**
	 * @deprecated Use {@link com.trazere.core.io.DecoratorOutputStream#DecoratorOutputStream(OutputStream)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public DecoratorOutputStream(final OutputStream stream) {
		assert null != stream;
		
		// Initialization.
		_stream = stream;
	}
	
	/**
	 * @deprecated To be removed.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public OutputStream getStream() {
		return _stream;
	}
	
	@Override
	public void write(final int b)
	throws IOException {
		_stream.write(b);
	}
	
	@Override
	public void write(final byte[] b)
	throws IOException {
		_stream.write(b);
	}
	
	@Override
	public void write(final byte[] b, final int off, final int len)
	throws IOException {
		_stream.write(b, off, len);
	}
	
	@Override
	public void flush()
	throws IOException {
		_stream.flush();
	}
	
	@Override
	public void close()
	throws IOException {
		_stream.close();
	}
}
