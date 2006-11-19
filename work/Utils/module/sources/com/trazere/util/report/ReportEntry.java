package com.trazere.util.report;

/**
 * The <code>ReportEntry</code> interface defines report entries.
 * <p>
 * Report entries are characterized by a category, a code, a message.
 * 
 * @param <Category> Type of the categories.
 * @param <Code> Type of the codes.
 */
public interface ReportEntry<Category, Code extends Enum<?>> {
	/**
	 * Get the category of the receiver report entry.
	 * 
	 * @return The category. May be <code>null</code>.
	 */
	public Category getCategory();

	/**
	 * Get the code of the receiver report entry.
	 * 
	 * @return The code. May be <code>null</code>.
	 */
	public Code getCode();

	/**
	 * Get the message of the receiver report entry.
	 * 
	 * @return The message. May by <code>null</code>.
	 */
	public String getMessage();
}
