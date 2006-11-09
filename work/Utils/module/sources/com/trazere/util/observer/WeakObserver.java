package com.trazere.util.observer;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class WeakObserver
extends WeakReference
implements Observer {
	private static final Log LOG = LogFactory.getLog(WeakObserver.class);
	
	public WeakObserver(final Object object) {
		super(object);
	}
	
	public WeakObserver(final Object object, final ReferenceQueue queue) {
		super(object, queue);
	}
	
	public final void update(final Observable observable, final Object argument) {
		if (null != get()) {
			weakUpdate(observable, argument);
		} else {
			// Remove observer.
			observable.deleteObserver(this);
			
			// Warning.
			LOG.warn("Deleted weakened observer " + this);
		}
	}
	
	public abstract void weakUpdate(final Observable observable, final Object argument);
}
