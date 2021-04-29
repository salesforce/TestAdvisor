/**
 * 
 */
package com.salesforce.cqe.common;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.testng.Assert;

import com.google.common.base.Strings;

/**
 * Utility class for reading and caching information stored in properties files.
 */
public class PropertyUtil {
	private static HashMap<String, PropertyUtil> repository = new HashMap<>();

	private Properties properties;
	
	/**
	 * Loads the given properties file.
	 * <p>
	 * Fails the calling test if properties file could not be loaded.
	 * @param fileName path of file; must not be null or empty
	 * @return existing instance, if file has been opened before, or a fresh instance 
	 */
	public static PropertyUtil load(String fileName) {
		if (Strings.isNullOrEmpty(fileName)) {
			throw new IllegalArgumentException("The properties file has not been defined!");
		}
		PropertyUtil instance = repository.get(fileName);
		if (instance == null) {
			instance = new PropertyUtil(fileName);
			repository.put(fileName, instance);
		}
		return instance;
	}

	private PropertyUtil(String fileName) {
		properties = new Properties();
		FileReader fileReader;
		try {
			fileReader = new FileReader(fileName);
			properties.load(fileReader);
		} catch (IOException e) {
			Assert.fail("Unable to open properties file " + fileName, e.getCause());
		}
	}

	/**
	 * Gets the value for the given property.
	 * 
	 * @param key name of property
	 * @return value associated with key or null
	 */
	public String getValue(String key) {
		return getValue(key, null);
	}

	/**
	 * Gets the value for the given property.
	 * 
	 * @param key name of property
	 * @param defaultValue default value to be returned in case property has not been set
	 * @return value associated with key or default value
	 */
	public String getValue(String key, String defaultValue) {
		if (Strings.isNullOrEmpty(key)) {
			Assert.fail("The property name must not be null or empty!");
		}
		String value = properties.getProperty(key, defaultValue);
		if (value != null) {
			value = value.trim();
		}
		return value;
	}
}
