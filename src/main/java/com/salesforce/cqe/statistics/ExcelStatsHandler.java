/**
 * 
 */
package com.salesforce.cqe.statistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Strings;

/**
 * Results handling by reading from Excel (.xlsx) files and writing
 * to a summary Excel file.
 * 
 * @author gneumann
 */
public class ExcelStatsHandler implements IResultsHandler {
	private static final String SHEET_NAME_SUMMARY = "Summary";
	private static final int COLUMN_ID_TEST_CASE = 0;
	private static final int COLUMN_ID_STATE = 1;
	private static final int COLUMN_ID_DURATION = 2;
	private static final String COLUMN_NAME_TEST_CASE = "Test Case";
	private static final String COLUMN_NAME_STATE = "State";
	private static final String COLUMN_NAME_DURATION = "Duration";
	private static final String SUMMARY_FILE_POSTFIX = " summary.xlsx";

	private List<Integer> buildIds = new ArrayList<>();
	private String customerSuite;
	private List<Result> results = new ArrayList<>();
	private String resourceDir;
	private List<String> testCaseNames = new ArrayList<>();

	private XSSFWorkbook wb;
	private XSSFSheet sheet;

	/**
	 * You will need one instance per home directory you want to scan for Excel results files.
	 * @param resourceDir existing directory
	 */
	public ExcelStatsHandler(String resourceDir) {
		File resDir = new File(resourceDir);
		if (resDir.exists() && resDir.isDirectory()) {
			this.resourceDir = resourceDir;
		} else {
			throw new IllegalArgumentException("Directory " + resourceDir + " could not be found.");
		}
	}

	/**
	 * Opens all Excel files with the same prefix given in parameter customerSuite. It will open
	 * them from the home directory defined when constructing this instance.
	 * <p>
	 * After opening the file it will parse the content and create {@link Result} objects for each
	 * test case it finds.
	 * <p>
	 * This means that you should first call this method before engaging in any result processing.
	 *  
	 * @param customerSuite all Excel files for a given customer and test suite have this common
	 * file name prefix, e.g. "HomeDepot-CRM-Source".
	 * @throws Exception if file operation fails or if  
	 */
	@Override
	public void open(String customerSuite) throws Exception {
		if (Strings.isNullOrEmpty(customerSuite)) {
			throw new IllegalArgumentException("Name of customer suite must not be NULL or empty.");
		}
		this.customerSuite = customerSuite;

		String[] excelFilePaths = (new File(resourceDir)).list(
				(File dirToFilter, String filename) -> (filename.startsWith(customerSuite) && !filename.contains(SUMMARY_FILE_POSTFIX)));

		if (excelFilePaths.length == 0) {
			throw new IllegalArgumentException(
					"Could not find any result files for '" + customerSuite + "'in directory '" + resourceDir + "'");
		}

		for (String excelFile : excelFilePaths) {
			openAndRead(customerSuite, resourceDir + File.separator + excelFile);
		}
	}

	/**
	 * Gets the results for a given test case or empty list, if no results could be found.
	 */
	@Override
	public List<Result> getResults(String testName) {
		// validate input parameter is not null or empty
		if (Strings.isNullOrEmpty(testName)) {
			return new ArrayList<>();
		}

		List<Result> filteredResults = results.stream().filter(result -> testName.equals(result.getTestName()))
				.collect(Collectors.toList());
		return filteredResults;
	}

	/**
	 * Gets the results for a given test case and build ID or NULL, if no result could be found.
	 */
	@Override
	public Result getResult(String testName, int buildID) {
		// validate input parameters are not null or empty
		if (Strings.isNullOrEmpty(testName) || buildID <= 0) {
			return null;
		}

		List<Result> filteredResults = getResults(testName).stream()
				.filter(result -> buildID == result.getBuildId())
				.collect(Collectors.toList());
		return (filteredResults.size() == 1) ? filteredResults.get(0) : null;
	}

	/**
	 * Gets all test case names loaded from the Excel result files, regardless of
	 * the build they were acquired for.
	 */
	@Override
	public List<String> getTestCaseNames() {
		List<String> clonedList = new ArrayList<>(testCaseNames.size());
		clonedList.addAll(testCaseNames);
		return clonedList;
	}

	/**
	 * Gets all build IDs loaded from the Excel result files, regardless of the
	 * test case they were acquired for.
	 */
	@Override
	public List<Integer> getBuildIds() {
		Collections.sort(buildIds);
		List<Integer> clonedList = new ArrayList<>(buildIds.size());
		clonedList.addAll(buildIds);
		return clonedList;
	}

	/**
	 * Writes a summary Excel file to the same directory where the result files were
	 * loaded from.
	 * <p>
	 * For each test case the summary will contain the duration and state per build ID
	 * plus an average duration. 
	 */
	@Override
	public void close() throws Exception {
		XSSFWorkbook summaryWb = new XSSFWorkbook();
		XSSFSheet summarySheet = summaryWb.createSheet(SHEET_NAME_SUMMARY);

		// create first row with column names
		XSSFRow titleRow = summarySheet.createRow(0);
		titleRow.createCell(COLUMN_ID_TEST_CASE).setCellValue(COLUMN_NAME_TEST_CASE);
		int columnNum = 1;
		
		List<Integer> ids = getBuildIds();
		for (Integer buildId : ids) {
			titleRow.createCell(columnNum).setCellValue("Build #" + buildId + " - " + COLUMN_NAME_DURATION);
			titleRow.createCell(columnNum + 1).setCellValue("Build #" + buildId + " - " + COLUMN_NAME_STATE);
			columnNum = columnNum + 2;
		}
		titleRow.createCell(columnNum).setCellValue(COLUMN_NAME_DURATION + " Average");

		int rowNum = 1;
		for (int i = 0; i < testCaseNames.size(); i++) {
			XSSFRow row = summarySheet.createRow(rowNum);
			String testCaseName = testCaseNames.get(i);
			row.createCell(COLUMN_ID_TEST_CASE).setCellValue(testCaseName);
			rowNum = rowNum + 1;

			columnNum = 1;
			long timeSum = 0L;
			int timeSummands = 0;
			for (Integer buildId : ids) {
				Result res = getResult(testCaseName, buildId);
				if (res.getState() == Result.State.PASS) {
					timeSum = timeSum + res.getTimeElapsedInMillisecs();
					timeSummands = timeSummands + 1;
				}
				row.createCell(columnNum).setCellValue(res.getTimeElapsedInMillisecs()/1000L + " secs");
				row.createCell(columnNum + 1).setCellValue(res.getState().toString().toLowerCase());
				columnNum = columnNum + 2;
			}
			if (timeSummands > 0) {
				row.createCell(columnNum).setCellValue(timeSum/timeSummands/1000L + " secs");
			}
		}

		FileOutputStream fos = new FileOutputStream(
				resourceDir + File.separator + customerSuite + SUMMARY_FILE_POSTFIX);
		summaryWb.write(fos);
		fos.close();
	}

	private void openAndRead(String customerSuite, String file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		// create a workbook
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheetAt(0);

		final short maxColumn = sheet.getRow(0).getLastCellNum(); // 1-based
		final int maxRow = sheet.getLastRowNum(); // 0-based

		if (maxColumn < 3) {
			throw new IllegalArgumentException("Results file " + file + " has too few columns!");
		}
		if (maxRow < 1) {
			throw new IllegalArgumentException("Results file " + file + " has no results!");
		}

		// get build id from sheet name
		String sheetName = wb.getSheetName(wb.getActiveSheetIndex());
		int buildId = -1;
		Pattern p = Pattern.compile("[b|B]uild\\s*#(\\d+)");
		Matcher m = p.matcher(sheetName);
		if (m.matches()) {
			buildId = Integer.parseInt(m.group(1));
			if (!buildIds.contains(buildId)) {
				buildIds.add(buildId);
			}
		} else {
			throw new IllegalArgumentException("Name of first sheet in result file '" + file
					+ "' has to be of format 'Build #X' e.g. 'Build #12'");
		}
		

		// get first row and check columns to match "Test Case", "State", "Duration"
		String columnName1 = getCellValue(0, COLUMN_ID_TEST_CASE);
		String columnName2 = getCellValue(0, COLUMN_ID_STATE);
		String columnName3 = getCellValue(0, COLUMN_ID_DURATION);

		if (!COLUMN_NAME_TEST_CASE.equalsIgnoreCase(columnName1)) {
			throw new IllegalArgumentException(
					"First column in results file '" + file + "' has to be named '" + COLUMN_NAME_TEST_CASE + "'");
		}
		if (!COLUMN_NAME_STATE.equalsIgnoreCase(columnName2)) {
			throw new IllegalArgumentException(
					"Second column in results file '" + file + "' has to be named '" + COLUMN_NAME_STATE + "'");
		}
		if (!COLUMN_NAME_DURATION.equalsIgnoreCase(columnName3)) {
			throw new IllegalArgumentException(
					"Third column in results file '" + file + "' has to be named '" + COLUMN_NAME_DURATION + "'");
		}
		// Given file seems to be OK; let's load the results
		// iterate over all rows till the end and create result for each row
		for (int i = 1; i <= maxRow; i++) {
			String testCaseName = getCellValue(i, COLUMN_ID_TEST_CASE).trim();
			String stateString = getCellValue(i, COLUMN_ID_STATE).trim().toUpperCase();
			String durationString = getCellValue(i, COLUMN_ID_DURATION).trim();

			if (!testCaseNames.contains(testCaseName)) {
				testCaseNames.add(testCaseName);
			}
			Result res = new Result(customerSuite, testCaseName, buildId, Result.State.valueOf(stateString),
					convertDurationStringToMillisecs(durationString));
			results.add(res);
		}

		fis.close();
	}

	/**
	 * Converts duration string to milliseconds. Supported formats:
	 * <ul>
	 * <li>2m 39s</li>
	 * <li>11M 9S</li>
	 * <li>24s</li>
	 * <li>9S</li>
	 * </ul>
	 * 
	 * @param durationString
	 * @return
	 */
	private long convertDurationStringToMillisecs(String durationString) {
		long duration = 0L;
		boolean hasMinutes = durationString.contains("m") || durationString.contains("M");
		Pattern p = Pattern.compile("((\\d+)[m|M]\\s*)*((\\d+)[s|S])");
		Matcher m = p.matcher(durationString);
		if (m.matches()) {
			if (hasMinutes) {
				duration = Long.parseLong(m.group(2)) * 60;
			}
			duration = (duration + Long.parseLong(m.group(4))) * 1000L;
		} else {
			throw new IllegalArgumentException("Duration string '" + durationString
					+ "' has to be of format 'Xm Ys' or 'Ys' e.g. '2m 39s' or '24s'");
		}
		return duration;
	}

	private String getCellValue(int row, int col) {
		XSSFCell cell = sheet.getRow(row).getCell(col);
		String celltext = null;
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			celltext = cell.getStringCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			celltext = String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			celltext = "";
		}
		return celltext;
	}
}
