/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gpahuja
 *
 */
public class JsonHelperTest {

	/**
	 * 
	 * This is a private class for testing purposes only.
	 * 
	 * @author gpahuja
	 *
	 */
	private static class Student {

		@JsonProperty
		private String firstName;
		@JsonProperty
		private String lastName;
		@JsonProperty
		private String studentID;
		
		/**
		 * A default constructor for the Student class
		 */
		private Student() {
			firstName = "First";
			lastName = "Last";
			studentID = "12345";
		}
		
		/**
		 * A constructor for the Student class that takes in three arguments
		 * 
		 * @param firstName represents the student's first name
		 * @param lastName represents the student's last name
		 * @param studentID represents the student's ID number
		 */
		private Student(String firstName, String lastName, String studentID) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.studentID = studentID;
		}
		
		/**
		 * Returns the student's first name
		 * 
		 * @return firstName represents the student's first name
		 */
		private String getFirstName() {
			return firstName;
		}
		
		/**
		 * Returns the student's last name
		 * 
		 * @return lastName represents the student's last name
		 */
		private String getLastName() {
			return lastName;
		}
		
		/**
		 * Returns the student's ID number
		 * 
		 * @return studentID represents the student's last name
		 */
		private String getStudentID() {
			return studentID;
		}
		
	}
	
	/**
	 * 
	 * This is a private class for testing purposes only.
	 * 
	 * @author gpahuja
	 *
	 */
	private static class Book {

		private String title;
		private String author;
		
		/**
		 * A default constructor for the Student class
		 */
		private Book() {
			title = "Title";
			author = "Author";
		}

	}
	
	private Student student;
	private Book book;
	private Path root;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	/**
	 * Creates a temporary test run directory before the test starts along with an
	 * instance of the Student class to save into a JSON file
	 *
	 * @throws IOException if an I/O error occurs or the temporary-file directory does not exist
	 */
	@Before
	public void setUp() throws IOException {
		book = new Book();
		student = new Student("Bill", "Gates", "12345");
		root = Files.createTempDirectory("");
		
		root.toFile().mkdirs();
	}
	
	/**
	 * Deletes the previously-created temporary test run directory after all of the tests have finished running
	 */
	@After
	public void tearDown() {
		root.toFile().deleteOnExit();
	}
	
    /**
     * Tests to make sure that the toObject() method works as expected
     * @throws Exception if file got corrupted or does not fit the class to de-serialize
     */
	@Test
	public void testToObject() throws Exception {
		Path outputFileLocation = Paths.get(root.toString(), "student.json");
		JsonHelper.toFile(outputFileLocation.toAbsolutePath().toString(), student);
		Student jsonStudent = JsonHelper.toObject(outputFileLocation.toString(), Student.class);
		assertEquals("Bill", jsonStudent.getFirstName());
		assertEquals("Gates", jsonStudent.getLastName());
		assertEquals("12345", jsonStudent.getStudentID());
	}
	
    /**
     * Tests to make sure that the toObject() method works as expected
     * and that it throws an exception when the JSON file being passed in is non-existent
     * @throws Exception if the JSON file being passed in is non-existent
     */
	@Test
	public void testToObjectNonExistentFile() throws Exception {
		exceptionRule.expect(Exception.class);
		exceptionRule.expectMessage("Cannot de-serialize object from non-existing JSON file " + Paths.get(root.toString(),
				"non_existent.json").toAbsolutePath().toString());
		JsonHelper.toObject(Paths.get(root.toString(), "non_existent.json").toAbsolutePath().toString(), Student.class);
	}
	
    /**
     * Tests to make sure that the toObject() method works as expected
     * and that it throws an exception when the object being de-serialized
     * from the JSON file isn't serialized
     * @throws Exception if the object being de-serialized from the JSON file isn't serialized
     */
	@Test
	public void testToObjectDeserializingError() throws Exception {
		exceptionRule.expect(Exception.class);
		exceptionRule.expectMessage("Error while de-serializing object from JSON file");
		Path outputFileLocation = Paths.get(root.toString(), "student.json");
		JsonHelper.toFile(outputFileLocation.toAbsolutePath().toString(), student);
		Book jsonBook = JsonHelper.toObject(outputFileLocation.toString(), Book.class);
	}
	
    /**
     * Tests to make sure that the toFile() method works as expected
     * @throws Exception if the object can't be serialized to a JSON file
     */
	@Test
	public void testToFile() throws Exception {
		Path outputFileLocation = Paths.get(root.toString(), "student.json");
		JsonHelper.toFile(outputFileLocation.toAbsolutePath().toString(), student);
		assertTrue(root.toFile().exists());
		assertTrue(root.toFile().isDirectory());
		assertTrue(outputFileLocation.toFile().isFile());
		assertEquals("student.json", outputFileLocation.toFile().getName());
	}
	
    /**
     * Tests to make sure that the toFile() method works as expected
     * and that it throws an exception when the object isn't serialized to a JSON file
     * @throws Exception if the object can't be serialized to a JSON file
     */
	@Test
	public void testToFileSerializingError() throws Exception {
		exceptionRule.expect(Exception.class);
		exceptionRule.expectMessage("Error while serializing object to JSON file");
		Path outputFileLocation = Paths.get(root.toString(), "book.json");
		JsonHelper.toFile(outputFileLocation.toAbsolutePath().toString(), book);
	}
	
    /**
     * Tests to make sure that the toFile() method works as expected
     * and that it throws an exception when the location the file is being saved to is a directory
     * @throws Exception if the the location the file is being saved to is a directory
     */
	@Test
	public void testToFileSerializingErrorTwo() throws Exception {
		exceptionRule.expect(Exception.class);
		exceptionRule.expectMessage("Error while serializing object to JSON file");
		Path outputFileLocation = Paths.get(root.toString());
		JsonHelper.toFile(outputFileLocation.toAbsolutePath().toString(), student);
	}

}
