/**
 * 
 */
package com.salesforce.cqe.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.common.base.Strings;


/**
 * Utility class for reading and caching information stored in Excel files.
 * @author gneumann
 */
public class ExcelUtil {
	private static HashMap<Index, String> readValues = new HashMap<>();

	private static String fileName;
	private static String sheetName;
	
	/**
	 * Loads the spreadsheet file and presets the spreadsheet name to work with to
	 * "Sheet1" until you explicitly switch to another spreadsheet using {@link #setSheetName(String)}.
	 * 
	 * This method is especially useful when working with a file which has multiple
	 * spreadsheets or has just one named "Sheet1".
	 * 
	 * @param fileName name of the spreadsheet file
	 */
	public static void open(String fileName) {
		open(fileName, "Sheet1");
	}

	/**
	 * Loads the spreadsheet file and presets the spreadsheet name to work with until you
	 * explicitly switch to another spreadsheet using {@link #setSheetName(String)}.
	 * 
	 * This method is especially useful when working with a file which has just one
	 * spreadsheet or you plan to work with just one of the available spreadsheets.
	 * 
	 * @param fileName name of the spreadsheet file
	 * @param sheetName the name of the spreadsheet to use in calls {@link #getValue(int, int)}
	 * and {@link #setCellData(int, int, String)}
	 */
	public static void open(String fileName, String sheetName) {
		if (Strings.isNullOrEmpty(fileName)) {
			throw new IllegalArgumentException("The Excel file has not been defined!");
		}
		if (Strings.isNullOrEmpty(sheetName)) {
			throw new IllegalArgumentException("The Excel sheet name has not been defined!");
		}
		try {
			FileInputStream fis = new FileInputStream(fileName);
			fis.close();
			ExcelUtil.fileName = fileName;
			ExcelUtil.sheetName = sheetName;
		} catch (IOException ioe) {
			throw new IllegalArgumentException("The Excel file cannot be found: " + fileName);
		}
	}

	/**
	 * Sets the name of the spreadsheet to access in calls {@link #getValue(int, int)}
	 * and {@link #setCellData(int, int, String)}.
	 * 
	 * @param sheetName name of spreadsheet to work with; must not be null or empty
	 */
	public static void setSheetName(String sheetName) {
		if (Strings.isNullOrEmpty(sheetName)) {
			throw new IllegalArgumentException("Spreadsheet name must not be null or empty!");
		}
		ExcelUtil.sheetName = sheetName;
	}

	/**
	 * Gets string from cell.
	 * <p>
	 * This method assumes that you have loaded the spreadsheet using {@link ExcelUtil#open(String, String)} or
	 * {@link #setSheetName(String)} where you define the spreadsheet name.
	 *  
	 * @param rowNum row number, 0-based
	 * @param colNum column number, 0-based
	 * @return string, empty string, or null in case of an exception during reading
	 */
	public static String getValue(int rowNum, int colNum) {
		return getValue(ExcelUtil.sheetName, rowNum, colNum);
	}

	/**
	 * Gets string from cell.
	 *
	 * @param sheetName name of the spreadsheet to access
	 * @param rowNum row number, 0-based
	 * @param colNum column number, 0-based
	 * @return string, empty string, or null in case of an exception during reading
	 */
	public static String getValue(String sheetName, int rowNum, int colNum) {
		Index key = new Index(sheetName, rowNum, colNum);
		if (readValues.containsKey(key)) {
			return readValues.get(key);
		}

		if (Strings.isNullOrEmpty(fileName)) {
			throw new IllegalArgumentException("The Excel file has not been defined!");
		}

		String value = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell cell = r.getCell(colNum);
			if (cell == null) {
				return null;
			}
			cell.setCellType(CellType.STRING);
			value = cell.getStringCellValue().trim();
			readValues.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Sets cell to given string value and saves the file to disk.
	 * <p>
	 * This method assumes that you have loaded the spreadsheet using {@link ExcelUtil#open(String, String)} or
	 * {@link #setSheetName(String)} where you define the spreadsheet name.
	 *  
	 * @param rowNum row number, 0-based
	 * @param colNum column number, 0-based
	 * @param value
	 */
	public static boolean setCellData(int rowNum, int colNum, String value) {
		return setCellData(ExcelUtil.sheetName, rowNum, colNum, value);
	}

	/**
	 * Sets cell to given string value and saves the file to disk.
	 * 
	 * @param sheetName name of the spreadsheet to access
	 * @param rowNum row number, 0-based
	 * @param colNum column number, 0-based
	 * @param value
	 */
	public static boolean setCellData(String sheetName, int rowNum, int colNum, String value) {
		if (Strings.isNullOrEmpty(fileName)) {
			throw new IllegalArgumentException("The Excel file has not been defined!");
		}

		try {
			FileInputStream fis = new FileInputStream(fileName);
			org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);

			Row row = s.getRow(rowNum);
			if (row == null)
				row = s.createRow(rowNum);

			Cell cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(value);

			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();

			// now that saving the new value has succeeded, 
			// don't forget to update the readValues cache!
			Index key = new Index(sheetName, rowNum, colNum);
			readValues.put(key, value);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Object class to cache cell values for in-memory access. 
	 */
	private static class Index {
		// lazily initialized, cached hashCode
		private volatile int hashCode;
		private String sheet;
		private int row;
		private int col;

		public Index(String sheet, int row, int col) {
			this.sheet = sheet;
			this.row = row;
			this.col = col;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Index))
				return false;
			Index i = (Index) o;
			boolean isMatchingSheet = (sheet == null) ? i.sheet == null : sheet.equals(i.sheet);
			return isMatchingSheet && (row == i.row) && (col == i.col);
		}

		@Override
		public int hashCode() {
			int result = hashCode;
			if (result == 0) {
				result = 17;
				result = 31 * result + sheet.hashCode();
				result = 31 * result + row;
				result = 31 * result + col;
				hashCode = result;
			}
			return result;
		}
	}
}
