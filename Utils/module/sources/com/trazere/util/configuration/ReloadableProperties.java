package com.trazere.util.configuration;

import java.util.Properties;

import com.trazere.util.Assert;

/**
 * The <code>ReloadableProperties</code> class represent reloadble persistent sets of properties stored in files.
 * <p>
 * TODO: local feature
 */
public class ReloadableProperties {
	/** Path of the file containing the properties. */
	protected String _path = null;

	/** Flag indicating wether the properties may be overridden in a <em>local</em> file. */
	protected boolean _local = false;

	/** Properties. May be <code>null</code> when no properties has ever been loaded. */
	protected final Properties _properties;

	/**
	 * Instanciate new reloadable properties with no path and no default properties.
	 */
	public ReloadableProperties() {
		this(null, false, null);
	}

	/**
	 * Instanciate new reloadable properties with the given path and no default properties.
	 * <p>
	 * The properties are no loaded by the constructor.
	 * 
	 * @param path Path of the file containing the properties.
	 * @param local Flag indicating wether the properties may be overridden in a <em>local</em> file.
	 */
	public ReloadableProperties(final String path, boolean local) {
		this(path, local, null);
	}

	/**
	 * Instanciate new reloadable properties with no path and the given default properties.
	 * 
	 * @param defaults Default properties.
	 */
	public ReloadableProperties(final Properties defaults) {
		this(null, false, defaults);
	}

	/**
	 * Instanciate new reloadable properties with the given path and default properties.
	 * <p>
	 * The properties are no loaded by the constructor.
	 * 
	 * @param path Path of the file containing the properties.
	 * @param local Flag indicating wether the properties may be overridden in a <em>local</em> file.
	 * @param defaults Default properties.
	 */
	public ReloadableProperties(final String path, boolean local, final Properties defaults) {
		// Initialization.
		_path = path;
		_local = local;
		_properties = null == defaults ? new Properties() : new Properties(defaults);
	}

	/**
	 * Get the path of the receiver reloadable properties.
	 * 
	 * @return The path. May be <code>null</code>.
	 */
	public String getPath() {
		return _path;
	}

	/**
	 * Get the flag indicating wether the properties may be overridden in a <em>local</em> file.
	 * 
	 * @return The flag.
	 */
	public boolean getLocal() {
		return _local;
	}

	/**
	 * Load the properties at the given path.
	 * 
	 * @param path Path of the properties.
	 * @param local Flag indicating wether the properties may be overridden in a <em>local</em> file.
	 * @throws ConfigurationException
	 */
	public synchronized void load(final String path, final boolean local)
	throws ConfigurationException {
		Assert.notNull(path);

		// Set the path.
		_path = path;
		_local = local;

		// Load the properties.
		_properties.clear();
		ConfigurationUtils.loadConfiguration(_properties, _path, _local);
	}

	/**
	 * Reload the properties using the current path.
	 * <p>
	 * This method should not be called when no path has been set.
	 * 
	 * @throws ConfigurationException
	 * @throws IllegalStateException When no path has been set.
	 */
	public synchronized void reload()
	throws ConfigurationException {
		if (null != _path) {
			// Reload the properties.
			_properties.clear();
			ConfigurationUtils.loadConfiguration(_properties, _path, _local);
		} else {
			throw new IllegalStateException("No path");
		}
	}

	/**
	 * Get the loaded or default properties.
	 * 
	 * @return The properties. May be <code>null</code> when no default properties have been provided and when no properties have ever been loaded.
	 */
	public Properties getProperties() {
		return _properties;
	}

	public String toString() {
		return String.valueOf(_properties);
	}
}
