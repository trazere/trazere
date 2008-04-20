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
package com.trazere.util.report;

import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.ObjectUtils;

/**
 * The <code>SimpleReportEntry</code> represents simple report entries.
 * 
 * @param <Category> Type of the categories.
 * @param <Code> Type of the codes.
 */
public class SimpleReportEntry<Category, Code extends Enum<?>>
implements ReportEntry<Category, Code> {
	/** Category of the report entry. May be <code>null</code>. */
	protected final Category _category;
	
	/** Code of the report entry. May be <code>null</code>. */
	protected final Code _code;
	
	/** Message of the report entry. May be <code>null</code>. */
	protected final String _message;
	
	/**
	 * Instantiate a new report entry with the given parameters.
	 * 
	 * @param category Category of the report entry. May be <code>null</code>.
	 * @param code Code of the report entry. May be <code>null</code>.
	 * @param message Message of the report entry. May be <code>null</code>.
	 */
	public SimpleReportEntry(final Category category, final Code code, final String message) {
		// Initialization.
		_category = category;
		_code = code;
		_message = message;
	}
	
	public Category getCategory() {
		return _category;
	}
	
	public Code getCode() {
		return _code;
	}
	
	public String getMessage() {
		return _message;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_category);
		hashCode.append(_code);
		hashCode.append(_message);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SimpleReportEntry<?, ?> entry = (SimpleReportEntry<?, ?>) object;
			return ObjectUtils.equals(_category, entry._category) && ObjectUtils.equals(_code, entry._code) && ObjectUtils.equals(_message, entry._message);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return ReportUtils.render(_category, _code, _message);
	}
}
