package edu.fin;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import edu.fin.data.*;
import edu.fin.data.Reader;


public class Assembler {
	private ArrayList<String> zipfile = null;
	private HashMap<String, ArrayList<Submit>> summary = null;
	private HashMap<String, ArrayList<SubmitTable>> table = null; 
	
	public Assembler (ArrayList<String> zipfile, HashMap<String, ArrayList<Submit>> summary, HashMap<String, ArrayList<SubmitTable>> table) {
		this.zipfile = zipfile;
		this.summary = summary;
		this.table = table;
	}
	
	public void assembleExcel (String resultPath) {
		
		int count = 0;
		int row = 0;
		
		try {
			File file = new File(resultPath);
			FileOutputStream outputFile = new FileOutputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Assembler");
			XSSFRow Row;
			
			
			for (String studentOrder : zipfile) {
				System.out.println ("> " + studentOrder);
				ArrayList<Submit> result_submit = summary.get(studentOrder);
				row += result_submit.size();
				System.out.println (row + " -- " + result_submit.size());
				
				while(count < row) {
					int i = 0;
					Row = sheet.createRow(count);
					Row.createCell(0).setCellValue(result_submit.get(i).getTitle());
					Row.createCell(1).setCellValue(result_submit.get(i).getSummary());
					Row.createCell(2).setCellValue(result_submit.get(i).getCoreWord());
					Row.createCell(3).setCellValue(result_submit.get(i).getDate());
					Row.createCell(4).setCellValue(result_submit.get(i).getRealSource());
					Row.createCell(5).setCellValue(result_submit.get(i).getOriginSource());
					Row.createCell(6).setCellValue(result_submit.get(i).getOwner());
					i++;
					count++;
					System.out.println("making summary...");
				}
				count++;
			}
			
			for (String studentOrder : zipfile) {
				
				ArrayList<SubmitTable> result_submitTable = table.get(studentOrder);
				row += result_submitTable.size();
				
				while (count < row) {
					int i = 0;
					Row = sheet.createRow(count);
					Row.createCell(0).setCellValue(result_submitTable.get(i).getTitle());
					Row.createCell(1).setCellValue(result_submitTable.get(i).getSerial());
					Row.createCell(2).setCellValue(result_submitTable.get(i).getDataType());
					Row.createCell(3).setCellValue(result_submitTable.get(i).getInformations());
					Row.createCell(4).setCellValue(result_submitTable.get(i).getInfoNumber());
					i++;
					count++;
					System.out.println("making table...");
				}
			}
			
			workbook.write(outputFile);
			outputFile.close();
			workbook.close();
			
		} catch (FileNotFoundException e) {
			System.out.println ("File not founded!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println ("Input or Output error!");
			e.printStackTrace();
		}
	}
}