package com.trazere.util.accumulator;

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;

/**
 * The {@link AccumulatorUtils} class provides various helpers regarding accumulators.
 */
public class AccumulatorUtils {
	// TODO: move into Accumulator1
	/**
	 * Accumulates the given value into the given accumulator.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param accumulator The accumulator.
	 * @param value The value.
	 * @throws X When the accumulation fails.
	 */
	public static <T, X extends Exception> void add(final Accumulator1<? super T, ?, ? extends X> accumulator, final Maybe<? extends T> value)
	throws X {
		assert null != accumulator;
		
		if (value.isSome()) {
			accumulator.add(value.asSome().getValue());
		}
	}
	
	// TODO: move into Accumulator2
	/**
	 * Accumulates the given pair of values into the given accumulator.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <X> Type of the exceptions.
	 * @param accumulator The accumulator.
	 * @param value The value.
	 * @throws X When the accumulation fails.
	 */
	public static <T1, T2, X extends Exception> void add(final Accumulator2<? super T1, ? super T2, ?, ? extends X> accumulator, final Maybe<? extends Tuple2<? extends T1, ? extends T2>> value)
	throws X {
		assert null != accumulator;
		
		if (value.isSome()) {
			final Tuple2<? extends T1, ? extends T2> value_ = value.asSome().getValue();
			accumulator.add(value_.getFirst(), value_.getSecond());
		}
	}
	
	private AccumulatorUtils() {
		// Prevents instantiation.
	}
}
