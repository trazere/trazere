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

import com.trazere.core.functional.Thunk;
import com.trazere.core.functional.Thunks;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ThrowableUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * The {@link URLInput} class implements inputs for the content accessible at some URL.
 * 
 * @since 2.0
 */
public class URLInput
implements Input {
	/**
	 * Instanciates a new input.
	 * 
	 * @param url URL of the data.
	 * @since 2.0
	 */
	public URLInput(final URL url) {
		assert null != url;
		
		// Initialization.
		_url = url;
		_connection = () -> {
			try {
				return url.openConnection();
			} catch (final IOException exception) {
				throw new RuntimeException(exception);
			}
		};
	}
	
	/**
	 * Instanciates a new input.
	 * 
	 * @param connection URL connection providing the data.
	 * @since 2.0
	 */
	public URLInput(final URLConnection connection) {
		assert null != connection;
		
		// Initialization.
		_url = connection.getURL();
		_connection = Thunks.constant(connection);
	}
	
	// Url.
	
	/**
	 * URL of the data.
	 * 
	 * @since 2.0
	 */
	protected final URL _url;
	
	/**
	 * Gets the URL of data provided by this input.
	 * 
	 * @return The url.
	 * @since 2.0
	 */
	public URL getUrl() {
		return _url;
	}
	
	// Connection.
	
	/**
	 * URL connection providing the data.
	 * 
	 * @since 2.0
	 */
	protected final Thunk<URLConnection> _connection;
	
	/**
	 * Gets the URL connection providing the data.
	 * 
	 * @return The URL connection.
	 * @throws IOException When the connection cannot be opened.
	 * @since 2.0
	 */
	protected URLConnection getConnection()
	throws IOException {
		try {
			return _connection.evaluate();
		} catch (final RuntimeException exception) {
			ThrowableUtils.throwCause(exception, IOException.class);
			throw exception;
		}
	}
	
	// Input.
	
	@Override
	public boolean exists()
	throws IOException {
		final URLConnection connection = getConnection();
		if (connection instanceof HttpURLConnection) {
			// Http.
			final HttpURLConnection httpConnection = (HttpURLConnection) connection;
			final int code = httpConnection.getResponseCode();
			if (code >= 200 && code < 300) {
				return true;
			} else if (code >= 400 && code < 500) {
				return false;
			} else {
				throw new IOException("Failed testing existance of input (result = " + code + ")");
			}
		} else {
			// General case.
			try {
				open().close();
				return true;
			} catch (final IOException exception) {
				return false;
			}
		}
	}
	
	@Override
	public InputStream open()
	throws IOException {
		return getConnection().getInputStream();
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_url);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final URLInput input = (URLInput) object;
			return _url.equals(input._url);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _url.toString();
	}
}
