/**
 * 
 */
package com.salesforce.cqe.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author gneumann
 *
 */
public class JsonHelper {
	/**
	 * Constructs instance of give type from JSON file.
	 * 
	 * @param <T> class to be de-serialized
	 * @param fileName JSON file to de-serialize.
	 * @param clazz de-serialize JSON file as an object of this class
	 * @return de-serialized object or null in case of any exception
	 * @throws Exception if file got corrupted or does not fit the class to de-serialize
	 */
	public static <T> T toObject(String fileName, Class<T> clazz) throws Exception {
		T retrievedObject = null;
		try {
			// Convert JSON string from file to Object
			retrievedObject = new ObjectMapper().readValue(new File(fileName), clazz);
			System.out.println("Done reading object from JSON file " + fileName);
		} catch (JsonGenerationException e) {
			throw new Exception("Error while de-serializing object from JSON file " + fileName, e);
		} catch (JsonMappingException e) {
			throw new Exception("Error while de-serializing object from JSON file " + fileName, e);
		} catch (IOException e) {
			throw new Exception("Error while de-serializing object from JSON file " + fileName, e);
		}
		return retrievedObject;
	}

	/**
	 * Saves given object to JSON file.
	 *  
	 * @param fileName JSON file with serialized object information
	 * @param object to be serialized
	 * @throws Exception if object could not be serialized to file
	 */
	public static void toFile(String fileName, Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
    	// Set pretty printing of json
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    	// convert object to JSON
    	String logEntriesToJson = null;
		try {
			logEntriesToJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e1) {
			throw new Exception("Error while serializing object to JSON file " + fileName, e1);
		}

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(logEntriesToJson);
			System.out.println("Done writing JSON file to " + fileName);
		} catch (IOException e) {
			throw new Exception("Error while serializing object to JSON file " + fileName, e);
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException ex) {
				System.err.println("Error while trying to close file writer to " + fileName);
				ex.printStackTrace();
			}
		}
	}
}
