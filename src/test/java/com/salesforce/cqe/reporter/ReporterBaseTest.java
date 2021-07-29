package com.salesforce.cqe.reporter;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * ReporterBaseTest will test all of the methods in the ReporterBase class
 * to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class ReporterBaseTest {

	private Path outputFile;
	private ReporterBase reporterBase;
	
	/**
	 * Sets up a temporary output folder for the test class
	 * @throws IOException if an I/O error occurs or the temporary-file directory does not exist
	 */
	@Before
	public void setUp() throws IOException {
		outputFile = Files.createTempDirectory("");
		outputFile.toFile().mkdirs();
		reporterBase = new ReporterBase(outputFile.toAbsolutePath().toString());
	}

    /**
     * Tests to make sure that the create() method works as expected
     */
	@Test
	public void testCreate() {
		//not implemented yet
	}

    /**
     * Tests to make sure that the log() method works as expected
     */
	@Test
	public void testLog() {
		//not implemented yet
	}

}
