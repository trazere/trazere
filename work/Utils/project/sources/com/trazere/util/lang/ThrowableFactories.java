package com.trazere.util.lang;

/**
 * The {@link ThrowableFactories} class provides various throwable factories.
 */
public class ThrowableFactories {
	/** Factory of {@link Exception} instances. */
	public static final ThrowableFactory<Exception> EXCEPTION = new AbstractThrowableFactory<Exception>() {
		public Exception build() {
			return new Exception();
		}
		
		public Exception build(final String message) {
			return new Exception(message);
		}
		
		public Exception build(final Throwable cause) {
			return new Exception(cause);
		}
		
		public Exception build(final String message, final Throwable cause) {
			return new Exception(message, cause);
		}
	};
	
	/** Factory of {@link RuntimeException} instances. */
	public static final ThrowableFactory<RuntimeException> RUNTIME_EXCEPTION = new AbstractThrowableFactory<RuntimeException>() {
		public RuntimeException build() {
			return new RuntimeException();
		}
		
		public RuntimeException build(final String message) {
			return new RuntimeException(message);
		}
		
		public RuntimeException build(final Throwable cause) {
			return new RuntimeException(cause);
		}
		
		public RuntimeException build(final String message, final Throwable cause) {
			return new RuntimeException(message, cause);
		}
	};
	
	private ThrowableFactories() {
		// Prevent instantiation.
	}
}
