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
	 * @param fileName JSON file to de-serialize.
	 * @param clazz de-serialize JSON file as an object of this class
	 * @return de-serialized object or null in case of any exception
	 */
	public static <T> T toObject(String fileName, Class<T> clazz) {
		T retrievedObject = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			// Convert JSON string from file to Object
			retrievedObject = mapper.readValue(new File(fileName), clazz);
		} catch (JsonGenerationException e) {
			System.err.println("Error while de-serializing object from JSON");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.err.println("Error while de-serializing object from JSON");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error while de-serializing object from JSON");
			e.printStackTrace();
		}
		return retrievedObject;
	}

	/**
	 * Saves given object to JSON file.
	 *  
	 * @param fileName JSON file with serialized object information
	 * @param object to be serialized
	 */
	public static void toFile(String fileName, Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
    	// Set pretty printing of json
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    	// convert object to JSON
    	String logEntriesToJson = null;
		try {
			logEntriesToJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e1) {
			System.err.println("Error while serializing object to JSON");
			e1.printStackTrace();
			return;
		}

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(logEntriesToJson);
			System.out.println("Done writing JSON file to " + fileName);
		} catch (IOException e) {
			System.err.println("Error while writing JSON file to " + fileName);
			e.printStackTrace();
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
