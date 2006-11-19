package com.trazere.util.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trazere.util.Assert;

/**
 * The <code>LogReportListener</code> class represents report listeners which forward the entries to a logger.
 * 
 * @param <Entry> Type of the entries.
 */
public class LogReportListener<Entry extends ReportEntry<?, ?>>
implements ReportListener<Entry> {
	private static final Log LOG = LogFactory.getLog(LogReportListener.class);

	/** Logger to use. */
	protected final Log _log;

	/**
	 * Instanciate a new listener with the given logger.
	 * 
	 * @param log Logger to use.
	 */
	public LogReportListener(final Log log) {
		Assert.notNull(log);

		// Initialization.
		_log = log;
	}

	/**
	 * Get the logger used by the receiver listener.
	 * 
	 * @return The logger.
	 */
	public Log getLog() {
		return _log;
	}

	public void report(final ReportLevel level, final Entry entry) {
		Assert.notNull(entry);

		// Render the message.
		final String message_ = ReportUtils.render(entry);

		// Log.
		switch (level) {
			case NOTICE: {
				_log.info(message_);
				break;
			}
			case WARNING: {
				_log.warn(message_);
				break;
			}
			case ERROR: {
				_log.error(message_);
				break;
			}
			default: {
				LOG.warn("Invalid report level " + level + " for entry " + message_);
				_log.error(message_);
				break;
			}
		}
	}
}
