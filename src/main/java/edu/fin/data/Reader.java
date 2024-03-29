package edu.fin.data;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.fin.Assembler;


public class Reader {
	private Workbook workbook;
	private Sheet sheet;
	private ArrayList<String> zipFileName = new ArrayList<String>();
	private HashMap<String, ArrayList<Submit<String>>> studentAssignment = new HashMap<String, ArrayList<Submit<String>>>();
	private HashMap<String, ArrayList<SubmitTable<String>>> studentAssignmentTable = new HashMap<String, ArrayList<SubmitTable<String>>>();
	
	public void run(ArrayList<String> files) {
				
		for(String filename:files) {
			File file = new File(filename);
			
			if (filename.indexOf("���") > -1 || filename.indexOf("Summary") > -1)
				runSummary(filename, file);
			
			else
				runTable(filename, file);
		}
	}
	
	
	public void runSummary(String filename, File file) {
		try {
			if (filename.endsWith("xlsx"))
				workbook = new XSSFWorkbook(new FileInputStream(file));
			
			else if (filename.endsWith("xls"))
				workbook = new HSSFWorkbook(new FileInputStream(file));
		
			else {
				System.out.println ("no excel summary file");
				System.exit(0);
			}
		
			sheet = workbook.getSheetAt(0);
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			ArrayList<Submit<String>> summaryList = new ArrayList<Submit<String>>();
			
			for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Submit<String> submit = new Submit<String>();
				
				if (row != null) {
					
					submit.setTitle(String.valueOf(row.getCell(0)));
					submit.setSummary(String.valueOf(row.getCell(1)));
					submit.setCoreWord(String.valueOf(row.getCell(2)));
					submit.setDate(String.valueOf(row.getCell(3)));
					submit.setRealSource(String.valueOf(row.getCell(4)));
					submit.setOriginSource(String.valueOf(row.getCell(5)));
					submit.setOwner(String.valueOf(row.getCell(6)));
					

					summaryList.add(submit);
				}
			}
			
			studentAssignment.put(filename.substring(0, 4), summaryList);
			
		} catch (IOException e) {

			new IllegalInputException ("Error : " + filename);
		}
	}
	
	public void runTable(String filename, File file) {
		try {
			if (filename.endsWith("xlsx"))
				workbook = new XSSFWorkbook(new FileInputStream(file));
			
			else if (filename.endsWith("xls"))
				workbook = new HSSFWorkbook(new FileInputStream(file));
		
			else {
				System.out.println ("no excel table file");
				System.exit(0);
			}
		
			sheet = workbook.getSheetAt(0);
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			ArrayList<SubmitTable<String>> summaryTable = new ArrayList<SubmitTable<String>>();
			
			for (int rowIndex = 2; rowIndex < numberOfRows; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				SubmitTable<String> submitTable = new SubmitTable<String>();
				
				if (row != null) {
					
					submitTable.setTitle(String.valueOf(row.getCell(0)));
					submitTable.setSerial(String.valueOf(row.getCell(1)));
					submitTable.setDataType(String.valueOf(row.getCell(2)));
					submitTable.setInformations(String.valueOf(row.getCell(3)));
					submitTable.setInfoNumber(String.valueOf(row.getCell(4)));
					
					summaryTable.add(submitTable);
				}
			}
			
			studentAssignmentTable.put(filename.substring(0, 4), summaryTable);
			zipFileName.add(filename.substring(0, 4));
			
		} catch (IOException e) {
			new IllegalInputException("Error : " + filename);
		}
	}
	
	public ArrayList<String> getZipFileName() {
		return zipFileName;
	}


	public HashMap<String, ArrayList<Submit<String>>> getStudentAssignment() {
		return studentAssignment;
	}


	public HashMap<String, ArrayList<SubmitTable<String>>> getStudentAssignmentTable() {
		return studentAssignmentTable;
	}
	
}
