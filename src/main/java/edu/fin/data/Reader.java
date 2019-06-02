package edu.fin.data;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Reader {
	private Workbook workbook;
	private Sheet sheet;
	private ArrayList<Submit> summaryList;
	private ArrayList<Submit> summaryTable;
	private ArrayList<String> zipName;
	private HashMap<String, ArrayList<Submit>> studentAssignment = new HashMap<String, ArrayList<Submit>>();
	
	
	public void run(ArrayList<String> files) {
		
		
		for(String filename:files) {
			File file = new File(filename);
			
			if (filename.indexOf("¿ä¾à") > -1 || filename.indexOf("Summary") > -1)
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
				System.out.println ("no excel file");
				System.exit(0);
			}
		
			sheet = workbook.getSheetAt(0);
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			
			for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Submit submit = new Submit();
				
				if (row != null) {
					
					submit.setTitle(row.getCell(0).getStringCellValue());
					submit.setSummary(row.getCell(1).getStringCellValue());
					submit.setCoreWord(row.getCell(2).getStringCellValue());
					submit.setDate(row.getCell(3).getStringCellValue());
					submit.setRealSource(row.getCell(4).getStringCellValue());
					submit.setOriginSource(row.getCell(5).getStringCellValue());
					submit.setOwner(row.getCell(6).getStringCellValue());
					summaryList.add(submit);
				}
			}
			
			
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
				System.out.println ("nono");
				System.exit(0);
			}
		
			sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					if (currentCell.getCellType() == CellType.STRING)
						System.out.print(currentCell.getStringCellValue() + "--");
					
					else if (currentCell.getCellType() == CellType.NUMERIC)
						System.out.print(currentCell.getNumericCellValue() + "--");
					

				}
				
				System.out.println();

			}
			
		} catch (IOException e) {
			System.out.println("Error2 : " + filename);
		}
	}
}
