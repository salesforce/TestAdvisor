package com.salesforce.cqe.common;

import com.google.gson.stream.MalformedJsonException;
import com.salesforce.cqe.execute.selenium.WebDriverFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.ITestResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import static com.salesforce.cqe.execute.selenium.WebDriverFactory.getSeleniumTestContext;
import static com.salesforce.selenium.support.event.WebDriverEventListener.TESTDROPIN_LOGFILES_DIR;

/**
 * class has all helper methods
 * @author shankysharma
 */
public class HelperUtil {

    public static ThreadLocal<String> setTestStartTimeinUtc = new ThreadLocal<>();
    public static ThreadLocal<String> setTestEndTimeinUtc = new ThreadLocal<>();
    public static ThreadLocal<String> setTestFailureDetails = new ThreadLocal<>();
    public static TestContext.Env env = getSeleniumTestContext();
    public static final String SPLUNK_TIME_FORMAT = "MM/dd/YYYY:HH:mm:ss";

    public static void main(String[] args) throws ParseException {
        System.out.println(WebDriverFactory.getSystemDateByTimezone("UTC","MM/dd/YYYY:HH:mm:ss"));
    }

    public static String splunkQuery(ITestResult testResult){
        String orgId = null;
        String sandboxPod = null;
        String testName= null;

        try {
            TestContext testContext = TestContext.getContext();
            orgId = testContext.getOrgs().getClone().getSandboxOrgId();
            sandboxPod = testContext.getOrgs().getClone().getSandboxPod();
            testName = testResult.getTestClass().getName() + "." + testResult.getName();
            String testStartTime = setTestStartTimeinUtc.get() != null ? setTestStartTimeinUtc.get() : HelperUtil.setTimeInSplunkFormat(testResult.getStartMillis());
            String testEndTime = setTestEndTimeinUtc.get() != null ? setTestEndTimeinUtc.get() : HelperUtil.setTimeInSplunkFormat(testResult.getEndMillis());
            String testStatus = testResult.getStatus() == 1 ? "PASS" : testResult.getStatus()==3 ? "SKIPPED":"FAIL" ;
            String failureDetails = setTestFailureDetails.get()  !=null ? setTestFailureDetails.get() : "";
            System.out.println("setTestStartTimeinUtc value: " + testStartTime);
            System.out.println("setTestEndTimeinUtc value: " + testEndTime);
            System.out.println("timezone value: " + DateTimeZone.getDefault().toString());
            String splunkQuery = WebDriverFactory.getSystemDateByTimezone(DateTimeZone.getDefault().toString(),"") + " : " +
                    testName + ": " + testStatus  + " " + failureDetails +
                    " : `from_index_sandbox(" + sandboxPod  +")` organizationId=" + orgId + " `logRecordType(G,gglog,gslog,selog)`" +
                    " earliest=" + testStartTime + " latest=" + testEndTime ;
            System.out.println("Test case name: " + testName + ": " + splunkQuery);
            return splunkQuery;
        } catch (MalformedJsonException e) {
            e.printStackTrace();
            return testName + " : exception occured while reading JSON" ;
        }
    }

    public static String setCurrentTimeInSplunkFormat(){
       return WebDriverFactory.getSystemDateByTimezone("UTC",SPLUNK_TIME_FORMAT);
    }

    public static String setTimeInSplunkFormat(Long timeInMilliseconds){
        return HelperUtil.getDateByTimezone("UTC",SPLUNK_TIME_FORMAT,timeInMilliseconds);
    }

    public static void writingToFile(String text){

       String fileName = "splunkQueries.txt";

        Path summaryPath = FileSystems.getDefault().getPath(TESTDROPIN_LOGFILES_DIR, fileName);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(summaryPath.toFile().getAbsolutePath(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            printWriter.println();
            printWriter.println(text);
        } catch (IOException e) {
            System.err.println("Error while writing to file " + summaryPath.toFile().getAbsolutePath());
            e.printStackTrace();
        }
        finally {
            if(printWriter != null)
                printWriter.close();

            try {
                if(bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                System.err.println("Error while writing to file " + summaryPath.toFile().getAbsolutePath());
                e.printStackTrace();
            }

            try {
                if(fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                System.err.println("Error while writing to file " + summaryPath.toFile().getAbsolutePath());
                e.printStackTrace();
            }
        }

        }

    public static String getDateByTimezone(String timezone, String format, Long timeInMilliseconds) {
        if( format == null || format.equals("") || format.isEmpty())
            format = "yyyy-MM-dd'T'HH:mm:ss";
        DateTime dt = new DateTime(timeInMilliseconds);
        DateTime timezoneDT = dt.withZone(DateTimeZone.forID(timezone));
        return timezoneDT.toString(format);
        }
    }


