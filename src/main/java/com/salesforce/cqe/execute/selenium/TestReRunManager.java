/**
 * 
 */
package com.salesforce.cqe.execute.selenium;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * This TestNG listener class allows to enable re-running of failed tests.
 * 
 * To use the re-run mechanism add this class as listener in testng.xml like
 * this:
 * 
 * <pre>
 * {@code <suite name="TestNG Listener Example">
 *     <listeners>
 *             <listener class-name="com.salesforce.cqe.execute.selenium.TestReRunManager"/>
 *     </listeners>
 *     ... }
 * </pre>
 * 
 * @author gneumann
 */
public class TestReRunManager implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass,
			@SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod) {
		// inject reference to TestRetryCounter class in each test's @Test annotation
		annotation.setRetryAnalyzer(TestRetryCounter.class);
	}
}
