package com.trazere.util.observer;

import com.trazere.util.function.Predicate1;

/**
 * The {@link Observable} interface defines event sources which can be observed.
 * <p>
 * {@link Observer Observers} can be subscribed to observables in order to listen to the raised events.
 * <p>
 * <b>Important:</b> In order to simplify their life cycle, the subscriptions are automatically canceled when the corresponding {@link ObserverSubscription
 * subscriptions} are garbaged collected. As the observables do not keep strong references to the subscription objects, it is the responsability of the caller
 * to reference them as long as necessary.
 * 
 * @param <T> Type of the event values.
 * @see Observer
 * @see ObserverSubscription
 */
public interface Observable<T> {
	/**
	 * Subcribes the given observer to the receiver observable.
	 * 
	 * @param observer The observer.
	 * @return The corresponding registration.
	 */
	public ObserverSubscription subscribe(final LiveObserver<? super T> observer);
	
	/**
	 * Subcribes the given observer to the receiver observable for all events.
	 * 
	 * @param observer The observer.
	 * @return The corresponding registration.
	 */
	public ObserverSubscription subscribe(final Observer<? super T> observer);
	
	/**
	 * Subcribes the given observer to the receiver observable to listen to a single event.
	 * 
	 * @param observer The observer.
	 * @return The corresponding registration.
	 */
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer);
	
	/**
	 * Subcribes the given observer to the receiver observable to listen to the events while the given condition holds.
	 * 
	 * @param observer The observer.
	 * @param condition The condition.
	 * @return The corresponding registration.
	 */
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition);
}
