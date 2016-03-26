package com.trazere.core.reactive;

import com.trazere.core.util.Tuple2;
import org.slf4j.Logger;

/**
 * The {@link BaseObservableValue} class provides a skeleton implementation of observable values.
 * 
 * @param <T> Type of the values.
 * @since 2.0
 */
public abstract class BaseObservableValue<T>
extends BaseObservable<T>
implements ObservableValue<T> {
	/**
	 * Instantiates a new observable.
	 * 
	 * @since 2.0
	 */
	public BaseObservableValue() {
		super();
	}
	
	/**
	 * Instantiates a new observable.
	 * 
	 * @param logger Logger to use.
	 * @since 2.0
	 */
	public BaseObservableValue(final Logger logger) {
		super(logger);
	}
	
	@Override
	public ObserverSubscription subscribeAndNotify(final Observer<? super T> observer) {
		// Subscribe.
		final Tuple2<T, ObserverSubscription> subscription = subscribeToValue(observer);
		
		// Notify.
		ObservableUtils.notify(observer, subscription.get1(), _logger);
		
		return subscription.get2();
	}
}
