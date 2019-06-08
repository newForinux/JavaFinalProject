package edu.fin;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import edu.fin.data.*;
import edu.fin.data.Reader;


public class Assembler implements Runnable {
	private ArrayList<String> zipfile = null;
	private HashMap<String, ArrayList<Submit>> summary = null;
	private String resultPath = null;
	private ArrayList<String> fileStudent = null;
	
	public Assembler (ArrayList<String> file, ArrayList<String> zipfile, HashMap<String, ArrayList<Submit>> summary, String resultPath) {
		this.zipfile = zipfile;
		this.summary = summary;
		this.resultPath = resultPath;
		this.fileStudent = file;
	}
	
	public void run () {
		
		int count = 1;
		int row = 1;
		int i, index = 0;
		
		try {
			File file = new File(resultPath);
			FileOutputStream outputFile = new FileOutputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Result");
			XSSFRow Row;
			
			//initial title
			Row = sheet.createRow(0);
			Row.createCell(0).setCellValue("����");
			Row.createCell(1).setCellValue("��๮ (300�� ����)");
			Row.createCell(2).setCellValue("�ٽɾ�\r\n" + "(keyword,��ǥ�� ����)");
			Row.createCell(3).setCellValue("��ȸ��¥");
			Row.createCell(4).setCellValue("�����ڷ���ȸ\r\n" + "��ó (���ڷḵũ)");
			Row.createCell(5).setCellValue("����ó (����� ��)");
			Row.createCell(6).setCellValue("������\r\n" + "(Copyright ����ó)");
			
			for (String studentOrder : zipfile) {
				ArrayList<Submit> result_submit = summary.get(studentOrder);
				row += result_submit.size();
				
				i = 0;
				
				Row = sheet.createRow(count);
				
				int substr = fileStudent.get(index).indexOf(".");
				String stuId = fileStudent.get(index).substring(0, substr);
				Row.createCell(0).setCellValue(stuId);
				
				index++;
				count++;
				row++;
				
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
			}
			
			workbook.write(outputFile);
			outputFile.close();
			workbook.close();
			
		} catch (FileNotFoundException e) {
			new IllegalInputException("File not founded.");
		} catch (IOException e) {
			new IllegalInputException("Input or Output Error.");
		}
	}
}