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
			Row.createCell(0).setCellValue("1. ã�� �ڷ� ���� �ִ� �׸��̳� ǥ�� �ڷ᳻ ��ġ(�ʹ�ȣ)�� ǥ�� �׸��� �����ϴ� ĸ��(�ּ�)�� �����ϴ�.\r\n" + 
					"2. ǥ�� �׸��� ĸ���� ���� ���, ������ ������ ���� ������ ������ �����ּ���.");
			
			Row = sheet.createRow(count+1);
			Row.createCell(0).setCellValue("����(�ݵ�� ��๮ ��Ŀ� �Է��� ����� ���ƾ� ��.)");
			Row.createCell(0).setCellValue("ǥ/�׸� �Ϸù�ȣ");
			Row.createCell(0).setCellValue("�ڷ�����(ǥ,�׸�,��)");
			Row.createCell(0).setCellValue("�ڷῡ ���� ǥ�� �׸� ����(ĸ��)");
			Row.createCell(0).setCellValue("�ڷᰡ ���� �ʹ�ȣ");
			
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