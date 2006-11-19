package com.trazere.util.observer;

import java.util.Observable;

/**
 * The <code>SimpleObservable</code> class represents observable which provide public control of their <em>changed</em> status.
 */
public class SimpleObservable
extends Observable {
	@Override
	public void clearChanged() {
		super.clearChanged();
	}

	@Override
	public void setChanged() {
		super.setChanged();
	}

	/**
	 * Mark the receiver observable as changed and immediately notify its observers.
	 */
	public void setChangedAndNotifyObservers() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Mark the receiver observable as changed and immediately notify its observers with the given argument.
	 * 
	 * @param argument Argument to pass to the observers.
	 */
	public void setChangedAndNotifyObservers(final Object argument) {
		setChanged();
		notifyObservers(argument);
	}
}
