package edu.fin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.fin.data.IllegalInputException;
import edu.fin.data.SubmitTable;


public class AssemblerTable implements Runnable{
	
	private ArrayList<String> zipfile = null;
	private HashMap<String, ArrayList<SubmitTable>> table = null; 
	private String resultPath = null;
	
	public AssemblerTable (ArrayList<String> zipfile, HashMap<String, ArrayList<SubmitTable>> table, String resultPath) {
		this.zipfile = zipfile;
		this.table = table;
		this.resultPath = resultPath;
	}
	
	public void run() {
		
		int count = 0;
		int row = 0;
		int i;
		
		try {
			File file = new File(resultPath);
			FileOutputStream outputFile = new FileOutputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Result");
			XSSFRow Row;
			
			
			//initial message & title
			Row = sheet.createRow(count);
			Row.createCell(0).setCellValue("1. 찾은 자료 내에 있는 그림이나 표의 자료내 위치(쪽번호)와 표와 그림을 설명하는 캡션(주석)을 적습니다.\r\n" + 
				"2. 표와 그림의 캡션이 없는 경우, 본문의 내용을 보고 간단히 설명을 적어주세요.");
			
			Row = sheet.createRow(count+1);
			Row.createCell(0).setCellValue("제목(반드시 요약문 양식에 입력한 제목과 같아야 함.)");
			Row.createCell(1).setCellValue("표/그림 일련번호");
			Row.createCell(2).setCellValue("자료유형(표,그림,…)");
			Row.createCell(3).setCellValue("자료에 나온 표나 그림 설명(캡션)");
			Row.createCell(4).setCellValue("자료가 나온 쪽번호");
	
			count += 2;
			row += 2;
	
			for (String studentOrder : zipfile) {
				ArrayList<SubmitTable> result_submitTable = table.get(studentOrder);
				row += result_submitTable.size();
		
				i = 0;
				
				Row = sheet.createRow(count);
				
				Row.createCell(0).setCellValue(studentOrder);

				count++;
				row++;
				
				while (count < row) {
			
					Row = sheet.createRow(count);
					Row.createCell(0).setCellValue(result_submitTable.get(i).getTitle());
					Row.createCell(1).setCellValue(result_submitTable.get(i).getSerial());
					Row.createCell(2).setCellValue(result_submitTable.get(i).getDataType());
					Row.createCell(3).setCellValue(result_submitTable.get(i).getInformations());
					Row.createCell(4).setCellValue(result_submitTable.get(i).getInfoNumber());
					i++;
					count++;
				}
			}
			
			workbook.write(outputFile);
			outputFile.close();
			workbook.close();
		
		} catch(FileNotFoundException e) {
			new IllegalInputException("File not founded.");
		} catch (IOException e) {
			new IllegalInputException("Input or Output Error.");
		}
	}
}
