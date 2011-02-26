/*
 *  Copyright 2006-2011 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.util;

// TODO: move in another package

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link FlagSet} class represents masks which can filter sets of flags.
 * <p>
 * Each mask is composed of three groups of flags:
 * <ul>
 * <li>the required flags,
 * <li>the enabled flags,
 * <li>the forbidden flags.
 * </ul>
 * A set of flags is accepted by a given mask when all following conditions are fulfiled:
 * <ul>
 * <li>the set must contain every required flag,
 * <li>the set must contain at least one enabled flag, or no flags are enabled but some are required,
 * <li>the set must contain no forbidden flags.
 * </ul>
 */
public class FlagSet
implements Describable {
	/** Required flags. */
	protected final Set<String> _required;
	
	/** Enabled flags. */
	protected final Set<String> _enabled;
	
	/** Forbidden flags. */
	protected final Set<String> _forbidden;
	
	/**
	 * Instantiate a new flag set with the given mask representation.
	 * <p>
	 * The representation is a list of comma (<tt>,</tt>) delimited flags. Required flags must be prefixed with a plus (<tt>+</tt>). Forbidden flags must be
	 * prefixed with a minus (<tt>-</tt>). Active flags are prefixed with neither.
	 * 
	 * @param representation Representation of the mask.
	 */
	public FlagSet(final String representation) {
		this(representation, ",");
	}
	
	/**
	 * Instiantiate a new flag set with the given mask mask representation and delimiter.
	 * <p>
	 * The representation is a list flags delimited by the given delimiter. Required flags must be prefixed with a plus (<tt>+</tt>). Forbidden flags must be
	 * prefixed with a minus (<tt>-</tt>). Active flags are prefixed with neither.
	 * 
	 * @param representation Representation of the mask.
	 * @param delimeter Delimiter of the flags.
	 */
	public FlagSet(final String representation, final String delimeter) {
		this(TextUtils.split(representation, delimeter, false, true, new ArrayList<String>()));
	}
	
	/**
	 * Instantiate a new flag set with the given flags.
	 * <p>
	 * Required flags must be prefixed with a plus (<tt>+</tt>). Forbidden flags must be prefixed with a minus (<tt>-</tt>). Active flags are prefixed with
	 * neither.
	 * 
	 * @param flags Flags composing the mask.
	 */
	public FlagSet(final List<String> flags) {
		assert null != flags;
		
		// Compute the flags.
		final Set<String> required = new HashSet<String>();
		final Set<String> enabled = new HashSet<String>();
		final Set<String> excluded = new HashSet<String>();
		for (final String flag : flags) {
			if (flag.startsWith("-")) {
				final String flagName = flag.substring(1);
				if (flagName.length() > 0) {
					required.remove(flagName);
					enabled.remove(flagName);
					excluded.add(flagName);
				}
			} else if (flag.startsWith("+")) {
				final String flagName = flag.substring(1);
				if (flagName.length() > 0) {
					if (!excluded.contains(flagName)) {
						enabled.remove(flagName);
						required.add(flagName);
					}
				}
			} else if (!excluded.contains(flag) && !required.contains(flag)) {
				enabled.add(flag);
			}
		}
		
		// Initialization.
		_required = Collections.unmodifiableSet(required);
		_enabled = Collections.unmodifiableSet(enabled);
		_forbidden = Collections.unmodifiableSet(excluded);
	}
	
	/**
	 * Get the required flags of the receiver set.
	 * 
	 * @return The required flags.
	 */
	public Set<String> getRequired() {
		return _required;
	}
	
	/**
	 * Get the enabled flags of the receiver set.
	 * 
	 * @return The enabled flags.
	 */
	public Set<String> getEnabled() {
		return _enabled;
	}
	
	/**
	 * Get the forbidden flags of the receiver set.
	 * 
	 * @return The forbidden flags.
	 */
	public Set<String> getForbidden() {
		return _forbidden;
	}
	
	/**
	 * Test whether the given flags is accepted by the mask of the receiver set.
	 * 
	 * @param flags Flags to test.
	 * @return <code>true</code> when the flags are accepted, <code>false</code> otherwise.
	 */
	public boolean test(final Collection<String> flags) {
		return flags.containsAll(_required) && (CollectionUtils.intersects(flags, _enabled) || (_enabled.isEmpty() && !_required.isEmpty())) && !CollectionUtils.intersects(flags, _forbidden);
	}
	
	/**
	 * Test whether the given flags is accepted by the mask of the receiver set.
	 * 
	 * @param flags Flags to test.
	 * @return <code>true</code> when the flags are accepted, <code>false</code> otherwise.
	 */
	public boolean test(final String... flags) {
		assert null != flags;
		
		// Test.
		return test(Arrays.asList(flags));
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		if (!_required.isEmpty()) {
			description.append("Required", TextUtils.join(_required, ","));
		}
		if (!_enabled.isEmpty()) {
			description.append("Enabled", TextUtils.join(_enabled, ","));
		}
		if (!_forbidden.isEmpty()) {
			description.append("Forbidden", TextUtils.join(_forbidden, ","));
		}
	}
}
