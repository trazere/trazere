package com.trazere.util.type;

import static com.trazere.util.type.Maybe.Tag.NONE;
import static com.trazere.util.type.Maybe.Tag.SOME;

import com.trazere.util.Assert;
import com.trazere.util.InternalException;

/**
 * The <code>Maybe</code> class represents the <em>Maybe</em> algebraic data type which wraps an optional value.
 * <p>
 * This class aims to improve the reliability of the code by avoiding uses of <code>null</code> values.
 * 
 * @param <T> Type of the value.
 */
public abstract class Maybe<T> {
	/**
	 * The <code>Tag</code> enumeration represents the various constructors of the algebraic data type.
	 */
	public static enum Tag {
		NONE,
		SOME,
	}

	/**
	 * Constant for the <code>NONE</code> instances.
	 */
	protected static final Maybe<?> _NONE = new Maybe<Object>() {
		@Override
		public Tag getTag() {
			return Tag.NONE;
		}

		@Override
		public Object getValue() {
			throw new UnsupportedOperationException();
		}
	};

	/**
	 * Build a new instance using the <code>NONE</code> constuctor.
	 * 
	 * @param <T> Type of the value.
	 * @return The instance.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Maybe<T> none() {
		return (Maybe<T>) _NONE;
	}

	/**
	 * Build a new instance using the <code>SOME</code> constuctor.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap.
	 * @return The instance.
	 */
	public static <T> Maybe<T> some(final T value) {
		Assert.notNull(value);

		// Build.
		return new Maybe<T>() {
			@Override
			public Tag getTag() {
				return Tag.SOME;
			}

			@Override
			public T getValue() {
				return value;
			}

			@Override
			public int hashCode() {
				int result = getClass().hashCode();
				result = result * 31 + value.hashCode();
				return result;
			}

			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final Maybe<?> entry = (Maybe<?>) object;
					return value.equals(entry.getValue());
				} else {
					return false;
				}
			}
		};
	}

	/**
	 * Wrap the given value into a <code>Maybe</code> instance.
	 * <p>
	 * This factory uses the <code>NONE</code> constructor when the value is <code>null</code>, or wrap it using the <code>SOME</code> constructor
	 * otherwise.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap. May be <code>null</code>.
	 * @return The instance.
	 */
	public static <T> Maybe<T> fromValue(final T value) {
		if (null != value) {
			return some(value);
		} else {
			return none();
		}
	}

	/**
	 * Unwrap the value from the given <code>Maybe</code> instance.
	 * <p>
	 * This method returns <code>null</code> for the instances built using the <code>NONE</code> constructors, or the wrapped value for the instances built
	 * using the <code>SOME</code> constuctors.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param instance The instance containing the value to unwrap.
	 * @return The wrapped value or <code>null</code>.
	 */
	public static <T> T toValue(final Maybe<T> instance) {
		switch (instance.getTag()) {
			case NONE: {
				return null;
			}
			case SOME: {
				return instance.getValue();
			}
			default: {
				throw new InternalException("Internal error");
			}
		}
	}

	/**
	 * Get the algebraic type of the receiver instance.
	 * 
	 * @return The tag.
	 */
	public abstract Tag getTag();

	/**
	 * Test wether the receiver instance has been built using the <code>NONE</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>NONE</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isNone() {
		return getTag() == Tag.NONE;
	}

	/**
	 * Test wether the receiver instance has been built using the <code>SOME</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>SOME</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isSome() {
		return getTag() == Tag.SOME;
	}

	/**
	 * Get the value wrapped in the receiver instance.
	 * <p>
	 * This method should only be called with instances built with the <code>SOME</code> constructor.
	 * 
	 * @return The wrapped value.
	 * @throws UnsupportedOperationException when the receiver instance has not been built using the <code>SOME</code> constuctor.
	 */
	public abstract T getValue();
}
