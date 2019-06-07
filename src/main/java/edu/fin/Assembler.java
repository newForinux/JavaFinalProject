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
		
		int count = 1;
		int row = 1;
		int i;
		
		try {
			File file = new File(resultPath);
			FileOutputStream outputFile = new FileOutputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Result");
			XSSFRow Row;
			
			//initial title
			Row = sheet.createRow(0);
			Row.createCell(0).setCellValue("제목");
			Row.createCell(1).setCellValue("요약문 (300자 내외)");
			Row.createCell(2).setCellValue("핵심어\r\n" + "(keyword,쉽표로 구분)");
			Row.createCell(3).setCellValue("조회날짜");
			Row.createCell(4).setCellValue("실제자료조회\r\n" + "출처 (웹자료링크)");
			Row.createCell(5).setCellValue("원출처 (기관명 등)");
			Row.createCell(6).setCellValue("제작자\r\n" + "(Copyright 소유처)");
			
			for (String studentOrder : zipfile) {
				ArrayList<Submit> result_submit = summary.get(studentOrder);
				row += result_submit.size();
				
				i = 0;
				
				while(count < row) {
					
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
					
				}
				count++;
				row++;
			}
			
			Row = sheet.createRow(count);
			Row.createCell(0).setCellValue("1. 찾은 자료 내에 있는 그림이나 표의 자료내 위치(쪽번호)와 표와 그림을 설명하는 캡션(주석)을 적습니다.\r\n" + 
					"2. 표와 그림의 캡션이 없는 경우, 본문의 내용을 보고 간단히 설명을 적오주세요.");
			
			Row = sheet.createRow(count+1);
			Row.createCell(0).setCellValue("제목(반드시 요약문 양식에 입력한 제목과 같아야 함.)");
			Row.createCell(0).setCellValue("표/그림 일련번호");
			Row.createCell(0).setCellValue("자료유형(표,그림,…)");
			Row.createCell(0).setCellValue("자료에 나온 표나 그림 설명(캡션)");
			Row.createCell(0).setCellValue("자료가 나온 쪽번호");
			
			count += 2;
			row += 2;
			
			for (String studentOrder : zipfile) {
				ArrayList<SubmitTable> result_submitTable = table.get(studentOrder);
				row += result_submitTable.size();
				
				i = 0;
				
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
				
				count++;
				row++;
			}
			
			workbook.write(outputFile);
			outputFile.close();
			workbook.close();
			
		} catch (FileNotFoundException e) {
			new IllegalInputException("File not founded.");
		} catch (IOException e) {
			new IllegalInputException("Input or Output error.");
		}
	}
}