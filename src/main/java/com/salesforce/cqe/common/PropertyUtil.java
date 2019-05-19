/**
 * 
 */
package com.salesforce.cqe.common;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.common.base.Strings;

/**
 * Utility class for reading and caching information stored in properties files.
 */
public class PropertyUtil {
	private static Properties properties;

	/**
	 * Loads the given properties file 
	 * @param fileName path of file; must not be null or empty
	 * @throws IOException if file is either not present or cannot be read from disk
	 */
	public void load(String fileName) throws IOException {
		if (Strings.isNullOrEmpty(fileName)) {
			throw new IllegalArgumentException("The properties file has not been defined!");
		}
		properties = new Properties();
		FileReader fileReader = new FileReader(fileName);
		properties.load(fileReader);
	}

	/**
	 * Gets the value for the given property.
	 * 
	 * @param key name of property
	 * @return value associated with key or null
	 */
	public static String getValue(String key) {
		return getValue(key, null);
	}

	/**
	 * Gets the value for the given property.
	 * 
	 * @param key name of property
	 * @param defaultValue default value to be returned in case property has not been set
	 * @return value associated with key or default value
	 */
	public static String getValue(String key, String defaultValue) {
		if (Strings.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("The property name must not be null or empty!");
		}
		String value = properties.getProperty(key, defaultValue);
		if (value != null) {
			value = value.trim();
		}
		return value;
	}
}
