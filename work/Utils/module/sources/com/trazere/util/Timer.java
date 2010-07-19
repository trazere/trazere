package com.trazere.util;

/**
 * The {@link Timer} class allows to measure time.
 */
public class Timer {
	/**
	 * Builds a new timer.
	 * 
	 * @return The timer.
	 */
	public static Timer build() {
		return new Timer();
	}
	
	/** The time the timer was started. */
	private final long _start;
	
	/**
	 * Instantiate a new timer.
	 */
	public Timer() {
		_start = System.currentTimeMillis();
	}
	
	/**
	 * Get the elapsed time since the creation of the receiver timer.
	 * 
	 * @return The elapsed number of milliseconds.
	 * @see System#currentTimeMillis()
	 */
	public long read() {
		return System.currentTimeMillis() - _start;
	}
}
