package edu.fin.data;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.fin.Assembler;


public class Reader {
	private String resultPath;
	private Workbook workbook;
	private Sheet sheet;
	private ArrayList<Submit> summaryList = new ArrayList<Submit>();
	private ArrayList<SubmitTable> summaryTable = new ArrayList<SubmitTable>();
	private ArrayList<String> zipFileName = new ArrayList<String>();
	private HashMap<String, ArrayList<Submit>> studentAssignment = new HashMap<String, ArrayList<Submit>>();
	private HashMap<String, ArrayList<SubmitTable>> studentAssignmentTable = new HashMap<String, ArrayList<SubmitTable>>();
	
	public void run(ArrayList<String> files, String resultPath) {
		
		this.resultPath = resultPath;
		
		for(String filename:files) {
			File file = new File(filename);
			
			if (filename.indexOf("¿ä¾à") > -1 || filename.indexOf("Summary") > -1)
				runSummary(filename, file);
			
			else
				runTable(filename, file);
		}
		
		Assembler assemble = new Assembler (zipFileName, studentAssignment, studentAssignmentTable);
		assemble.assembleExcel(resultPath);
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
			
			for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Submit submit = new Submit();
				
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

			System.out.println("Error : " + filename);
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
			
			for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				SubmitTable submitTable = new SubmitTable();
				
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
			System.out.println("Error : " + filename);
		}
	}


	public ArrayList<String> getZipFileName() {
		return zipFileName;
	}


	public HashMap<String, ArrayList<Submit>> getStudentAssignment() {
		return studentAssignment;
	}


	public HashMap<String, ArrayList<SubmitTable>> getStudentAssignmentTable() {
		return studentAssignmentTable;
	}
	
}
