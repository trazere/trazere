package com.trazere.util.observer;

/**
 * The {@link Observatory} interface defines relays for routing events from publishers to observers which do not know about each other.
 * <p>
 * Publishers and observers are associated using a common subjets.
 * 
 * @param <S> Type of the subjects.
 * @param <E> Type of the events.
 */
public interface Observatory<S, E> {
	/**
	 * Gets an observable which allows to subscribe to events routed by the reveiver observatory for the given subject.
	 * 
	 * @param subject The subject.
	 * @return The observable.
	 */
	public Observable<E> observe(final S subject);
	
	/**
	 * Notifies the observers of the events routed by the receiver observatory for the given subject with the given event.
	 * 
	 * @param subject The subject.
	 * @param event The event.
	 */
	public void notify(final S subject, final E event);
}
