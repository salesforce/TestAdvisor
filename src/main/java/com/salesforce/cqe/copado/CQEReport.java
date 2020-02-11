/**
 * 
 */
package com.salesforce.cqe.copado;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Report provider for supporting CQE BST program.
 * 
 * @author gneumann
 */
public class CQEReport {
	private static final String FILENAME = "target/test-results.xml";
	private static final String TEST_SUITE_NAME = "Copado Testing";
	private static final String TEST_CLASS_NAME = "copado";

	public void saveResult(String testReportName, String testStatus, String failureDescription) {
		TestCase testCase = new TestCase("Copado", "failed".equalsIgnoreCase(testStatus), failureDescription);

		File outputFile = new File(FILENAME);
		System.out.println("Processing output file " + outputFile.getAbsolutePath());

		int numOfFailedTests = 0;
		if (testCase.isFailure())
			numOfFailedTests = numOfFailedTests + 1;

		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element testsuites = createTestsuitesElement(document, numOfFailedTests);
			document.appendChild(testsuites);
			Element testsuite = createTestsuiteElement(document, numOfFailedTests);
			testsuites.appendChild(testsuite);
			testsuite.appendChild(createTestcaseElement(document, testCase));
			// create the xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(FILENAME));
			transformer.transform(domSource, streamResult);
			System.out.println("Completed writing output file " + outputFile.getAbsolutePath());
		} catch (ParserConfigurationException | TransformerException e) {
			System.err.println("Error while writing output file: " + e.getMessage());
		}
	}

	private static Element createTestsuitesElement(Document document, int numOfFailedTests) {
		Element testsuites = document.createElement("testsuites");

		Attr attrDisabled = document.createAttribute("disabled");
		attrDisabled.setValue("0");
		testsuites.setAttributeNode(attrDisabled);

		Attr attrErrors = document.createAttribute("errors");
		attrErrors.setValue("0");
		testsuites.setAttributeNode(attrErrors);

		Attr attrFailures = document.createAttribute("failures");
		attrFailures.setValue("" + numOfFailedTests);
		testsuites.setAttributeNode(attrFailures);

		Attr attrTests = document.createAttribute("tests");
		attrTests.setValue("1");
		testsuites.setAttributeNode(attrTests);

		Attr attrTime = document.createAttribute("time");
		attrErrors.setValue("0");
		testsuites.setAttributeNode(attrTime);

		return testsuites;
	}

	private static Element createTestsuiteElement(Document document, int numOfFailedTests) {
		Element testsuite = document.createElement("testsuite");

		Attr attrDisabled = document.createAttribute("disabled");
		attrDisabled.setValue("0");
		testsuite.setAttributeNode(attrDisabled);

		Attr attrErrors = document.createAttribute("errors");
		attrErrors.setValue("0");
		testsuite.setAttributeNode(attrErrors);

		Attr attrFailures = document.createAttribute("failures");
		attrFailures.setValue("" + numOfFailedTests);
		testsuite.setAttributeNode(attrFailures);

		Attr attrName = document.createAttribute("name");
		attrName.setValue(TEST_SUITE_NAME);
		testsuite.setAttributeNode(attrName);

		Attr attrSkipped = document.createAttribute("skipped");
		attrSkipped.setValue("0");
		testsuite.setAttributeNode(attrSkipped);

		Attr attrTests = document.createAttribute("tests");
		attrTests.setValue("1");
		testsuite.setAttributeNode(attrTests);

		Attr attrTime = document.createAttribute("time");
		attrSkipped.setValue("0");
		testsuite.setAttributeNode(attrTime);

		return testsuite;
	}

	private static Element createTestcaseElement(Document document, TestCase tc) {
		Element testcase = document.createElement("testcase");

		Attr attrClassname = document.createAttribute("classname");
		attrClassname.setValue(TEST_CLASS_NAME);
		testcase.setAttributeNode(attrClassname);

		Attr attrName = document.createAttribute("name");
		attrName.setValue(tc.getCustomerName());
		testcase.setAttributeNode(attrName);

		Attr attrTime = document.createAttribute("time");
		attrTime.setValue("0");
		testcase.setAttributeNode(attrTime);

		if (tc.isFailure()) {
			testcase.appendChild(createFailureElement(document, tc));
		}

		return testcase;
	}

	private static Element createFailureElement(Document document, TestCase tc) {
		Element failure = document.createElement("failure");

		Attr attrMessage = document.createAttribute("message");
		attrMessage.setValue(tc.getMessage());
		failure.setAttributeNode(attrMessage);

		Attr attrType = document.createAttribute("type");
		attrType.setValue("failure");
		failure.setAttributeNode(attrType);

		StringBuilder sb = new StringBuilder();
		sb.append(TEST_CLASS_NAME);
		failure.setTextContent(sb.toString());
		return failure;
	}

	private static class TestCase {
		private final String customerName;
		private final boolean isFailure;
		private final String message;

		TestCase(String customerName, boolean isFailure, String message) {
			this.customerName = customerName;
			this.isFailure = isFailure;
			if (message != null)
				message = message.replace("\"", "'");
			this.message = message;
		}

		String getCustomerName() {
			return this.customerName;
		}

		String getMessage() {
			return this.message;
		}

		boolean isFailure() {
			return this.isFailure;
		}
	}
}
