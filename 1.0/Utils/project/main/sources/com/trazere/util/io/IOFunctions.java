package com.trazere.util.io;

import com.trazere.util.function.Function1;
import java.io.File;

/**
 * The {@link IOFunctions} class provides factories of functions regarding I/O.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class IOFunctions {
	/**
	 * Builds a function that builds files.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.io.IOFunctions#file()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, File, X> file() {
		return (Function1<String, File, X>) _FILE;
	}
	
	private static Function1<String, File, ?> _FILE = new Function1<String, File, RuntimeException>() {
		@Override
		public File evaluate(final String path) {
			return new File(path);
		}
	};
	
	private IOFunctions() {
		// Prevents instantiation.
	}
}
