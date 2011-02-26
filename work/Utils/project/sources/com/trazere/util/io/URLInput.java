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

import com.trazere.util.lang.HashCode;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link FileInput} class implements data inputs based on URLs.
 */
public class URLInput
implements Input {
	/** URL to read. */
	protected final URL _url;
	
	/**
	 * Instanciate a new data input with the given URL.
	 * 
	 * @param url URL to read.
	 */
	public URLInput(final URL url) {
		assert null != url;
		
		// Initialization.
		_url = url;
	}
	
	/**
	 * Get the URL read by the receiver data input.
	 * 
	 * @return The url.
	 */
	public URL getUrl() {
		return _url;
	}
	
	public boolean exists()
	throws IOException {
		return true;
	}
	
	public InputStream open()
	throws IOException {
		return _url.openStream();
	}
	
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
