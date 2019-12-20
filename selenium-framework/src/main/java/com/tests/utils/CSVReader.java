package com.tests.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class CSVReader {
	
	static final Logger logger = Logger.getLogger(CSVReader.class);
	
	private static Map<String, List<String>> contentCache = new HashMap<>();
		
	private static final String ID_COLUMN = "Key";
	
	private CSVReader(){
		
	}
	
	/**
	 * Read the whole content of given csv file 
	 * @param fileName: String
	 * @return The whole content of given csv file
	 */
	public static List<Map<String, String>> readCSV(String fileName) {
		
		int csvRowCount = 0;
		try {
			// total row count without header
			csvRowCount = readLines(fileName).size() - 1;
		} catch (Exception e) {
			String message = "Exception occured when read csv file: " + fileName;
			logger.error(message, e);
			
		}
		
		return readCSV(fileName, 1, csvRowCount);
	}

	/**
	 * Read lines from relative path
	 * 
	 * @param relativePath file name
	 * @return List of lines
	 */
	private static List<String> readLines(String relativePath) {
		if (contentCache.containsKey(relativePath)) {
			return contentCache.get(relativePath);
		}
		
		BufferedReader reader = null;
		String line = null;
		List<String> lines = new ArrayList<>();
		
		if ("".equals(relativePath)) {
			String message = "relativePath can't be empty.";
			logger.info(message);
		}
		
		try {
			reader = new BufferedReader(new InputStreamReader(CSVReader.class.getClassLoader().getResourceAsStream(relativePath)));
			
			while ((line =  reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (Exception e) {
			logger.error("IOException occured when get absolute path of " + relativePath, e);
		} 
		contentCache.put(relativePath, lines);
		return lines;
	}
	
	/**
	 * Read the line content from startRow to endRow
	 * 
	 * @param fileName: String
	 * @param startRow: int
	 * @param endRow: int
	 * @return The line content from startRow to endRow
	 */
	public static List<Map<String, String>> readCSV(String fileName, int startRow, int endRow)
	{
		List<Map<String, String>> content = new ArrayList<>();
		
		if(startRow < 0 || endRow < 0 || (startRow > endRow)) {
			String message = "Parameter error, startRow is: " + startRow + ", endRow is:" + endRow;
			logger.error(message);
		}
		
		for(int i = startRow; i <= endRow; i++) {
			content.add(readCSV(fileName, i));
		}
		
		return content;
	}
		
	/**
	 * Read the single line map of given csv file by given row number
	 * 
	 * @param fileName: String
	 * @param row row number of csv file, start from 1
	 * @return Map which its keyset contains headers and valueset contains given row's values.
	 */
	public static Map<String, String> readCSV(String fileName, int row)
	{
		Map<String, String> lineMap = null;
		String line = null;
		
		if(row < 0) {
			String message = "Row can not be negative, row: " + row;
			logger.error(message);
		}
		
		List<String> lines = readLines(fileName);
		
		String[] header = lines.get(0).split(",", -1);
		
		if(row >= lines.size()) {
			String message = "Only " + lines.size() + " in csv file: " + fileName + ", but finding row: " + row;
			logger.error(message);
		}
		line = lines.get(row);
		if(line == null) {
			String message = "Row: " + row + " does not exist in csv file: " + fileName;
			logger.error(message);
		}
		
		String[] columns = line.split(",", -1);
		if (header.length != columns.length) {
			String message = "CSV file: " + fileName + " is not valid, header column equals "
					+ header.length + ", row column equals " + columns.length + " on row: " + row;
			logger.error(message);
		}
		
		lineMap = new LinkedHashMap<>();
		for(int i = 0; i < header.length; i++) {
			String value = replaceTwoVerticalLineToComma(columns[i]);	
				lineMap.put(header[i], value);
		}
		
		return lineMap;
	}

	/**
	 * Read single line map of csv file by given id value.
	 * 
	 * @param fileName csv file name.
	 * @param idValue value to be find in "Key" column.
	 * @return Map<String, String> of the row content, <b>null</b> if not found.
	 */
	public static Map<String, String> readCSV(String fileName, String idValue)
	{ 
		String errMsg = "";

		if (fileName == null || idValue == null) {
			errMsg = "Parameter error, fileName is: " + fileName + ", idValue: " + idValue;
			logger.info(errMsg);
		}
		
		List<Map<String, String>> rows = readCSV(fileName);
		if (rows.isEmpty()) {
			errMsg = "CSV file's content is empty, fileName is: " + fileName;
			logger.info(errMsg);
		}
		
		Map<String, String> rowFound = null;
		
		for(Map<String, String> row : rows) {
			if(idValue.equals(row.get(ID_COLUMN))) {
				rowFound = row;
				break;
			}
		}
		
		if (rowFound == null) {
			errMsg = idValue + " not found as a Key in " + "CSV file: " + fileName;
			logger.info(errMsg);
		}
		return rowFound;
	}
	/**
	 * Read the CSV file for all the rows
	 * @param fileName: String
	 * @return List<Map<String,Object>> CSV data for all the rows
	 */
	public static List<Map<String, Object>> readResourceCSV(String fileName)
	{
		List<Map<String, String>> temp = readCSV(fileName);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		temp.forEach(k -> result.add(convertMap(k)));
		return result;
	}


	/**
	 * Converts a map of String,String type to map of String, Object type
	 * @param map: Map of String,String
	 * @return Map of String,Object
	 */
	private static Map<String, Object> convertMap(Map<String, String> map)
	{
		Map<String, Object> result = new HashMap<String,Object>();
		map.keySet().stream().forEach(k -> result.put(k, map.get(k)));
		return result;
	}
	
	
	/**
	 * Replace two vertical line with comma.
	 * @param value String
	 * @return String 
	 */
	private static String replaceTwoVerticalLineToComma(String value) {
		return value.replaceAll("\\|\\|", ",");
	}
	
	
}
