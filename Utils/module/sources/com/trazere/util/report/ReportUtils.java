package com.trazere.util.report;

import com.trazere.util.Assert;

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
		Assert.notNull(entry);

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
	public static <Category, Code extends Enum> String render(final Category category, final Code code, final String message) {
		final StringBuilder builder = new StringBuilder();
		if (null != category) {
			builder.append(category);
			builder.append(" : ");
		}
		if (null != message) {
			builder.append(message);

			if (null != code) {
				builder.append(" (");
				builder.append(code);
				builder.append(")");
			}
		} else if (null != code) {
			builder.append("Code ");
			builder.append(code);
		} else {
			builder.append("n/a");
		}
		return builder.toString();
	}

	/**
	 * Build a new report entry by chaining the given report entries.
	 * 
	 * @param entry The leading report entry.
	 * @param causeEntry The cause report entry.
	 * @return The chained report entry.
	 */
	public static ReportEntry chain(final ReportEntry<?, ?> entry, final ReportEntry causeEntry) {
		Assert.notNull(entry);

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
	public static <Category, Code extends Enum> ReportEntry<Category, Code> chain(final Category category, final Code code, final String message, final ReportEntry causeEntry) {
		Assert.notNull(causeEntry);

		// Build the new entry..
		final StringBuilder builder = new StringBuilder();
		if (null != message) {
			builder.append(message);
			builder.append(", caused by ");
			builder.append(causeEntry);
		} else {
			builder.append("Caused by ");
			builder.append(causeEntry);
		}

		return new SimpleReportEntry<Category, Code>(category, code, builder.toString());
	}

	private ReportUtils() {
		// Prevent instanciation.
	}
}
