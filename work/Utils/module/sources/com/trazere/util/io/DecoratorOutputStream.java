package com.trazere.util.io;

import java.io.IOException;
import java.io.OutputStream;

public class DecoratorOutputStream
extends OutputStream {
	protected final OutputStream _stream;
	
	public DecoratorOutputStream(final OutputStream stream) {
		assert null != stream;
		
		// Initialization.
		_stream = stream;
	}
	
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
