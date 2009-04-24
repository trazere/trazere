package com.trazere.util.type;

/**
 * The {@link TypeUtils} class provides various helpers regarding the types.
 */
public class TypeUtils {
	/**
	 * Get the value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param <T> Type of the value.
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static <T> T get(final Maybe<? extends T> value, final T defaultValue) {
		assert null != value;
		
		// Get.
		return value.isSome() ? value.asSome().getValue() : defaultValue;
	}
	
	private TypeUtils() {
		// Prevent instantiation.
	}
}
