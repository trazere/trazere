/*
 *  Copyright 2006-2008 Julien Dufour
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


/**
 * The <code>ReportUtils</code> class provides various helpers regarding the manipulation of report entries.
 */
public class ReportUtils {
	/**
	 * Render a human readable string representation of the given report entry.
	 * 
	 * @param entry Entry to render.
	 * @return The representation.
	 */
	public static String render(final ReportEntry<?, ?> entry) {
		assert null != entry;
		
		// Render.
		return render(entry.getCategory(), entry.getCode(), entry.getMessage());
	}
	
	/**
	 * Render a human readable string representation of a report entry with the given code and message.
	 * 
	 * @param <Category> Type of the categories.
	 * @param <Code> Type of the codes.
	 * @param category Category of the report entry. May be <code>null</code>.
	 * @param code Code of the report entry. May be <code>null</code>.
	 * @param message Message of the report entry. May be <code>null</code>.
	 * @return The representation.
	 */
	public static <Category, Code extends Enum<?>> String render(final Category category, final Code code, final String message) {
		final StringBuilder builder = new StringBuilder();
		if (null != category) {
			builder.append(category).append(" : ");
		}
		if (null != message) {
			builder.append(message);
			
			if (null != code) {
				builder.append(" (").append(code).append(")");
			}
		} else if (null != code) {
			builder.append("Code ").append(code);
		} else {
			builder.append("n/a");
		}
		return builder.toString();
	}
	
	/**
	 * Build a new report entry by chaining the given report entries.
	 * 
	 * @param <Category> Type of the categories.
	 * @param <Code> Type of the codes.
	 * @param entry The leading report entry.
	 * @param causeEntry The cause report entry.
	 * @return The chained report entry.
	 */
	public static <Category, Code extends Enum<?>> ReportEntry<Category, Code> chain(final ReportEntry<Category, Code> entry, final ReportEntry<?, ?> causeEntry) {
		assert null != entry;
		
		// Chain.
		return chain(entry.getCategory(), entry.getCode(), entry.getMessage(), causeEntry);
	}
	
	/**
	 * Build a new report entry by chaining the report entry for the given entry parameters and the given cause report entry.
	 * 
	 * @param <Category> Type of the categories.
	 * @param <Code> Type of the codes.
	 * @param code Code of the report entry. May be <code>null</code>.
	 * @param category Category of the report entry. May be <code>null</code>.
	 * @param message Message of the report entry. May be <code>null</code>.
	 * @param causeEntry The cause report entry.
	 * @return The chained report entry.
	 */
	public static <Category, Code extends Enum<?>> ReportEntry<Category, Code> chain(final Category category, final Code code, final String message, final ReportEntry<?, ?> causeEntry) {
		assert null != causeEntry;
		
		// Build the new entry..
		final StringBuilder builder = new StringBuilder();
		if (null != message) {
			builder.append(message).append(", caused by ").append(causeEntry);
		} else {
			builder.append("Caused by ").append(causeEntry);
		}
		
		return new SimpleReportEntry<Category, Code>(category, code, builder.toString());
	}
	
	private ReportUtils() {
		// Prevent instantiation.
	}
}
