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
			Row.createCell(0).setCellValue("1. ã�� �ڷ� ���� �ִ� �׸��̳� ǥ�� �ڷ᳻ ��ġ(�ʹ�ȣ)�� ǥ�� �׸��� �����ϴ� ĸ��(�ּ�)�� �����ϴ�.\r\n" + 
				"2. ǥ�� �׸��� ĸ���� ���� ���, ������ ������ ���� ������ ������ �����ּ���.");
			
			Row = sheet.createRow(count+1);
			Row.createCell(0).setCellValue("����(�ݵ�� ��๮ ��Ŀ� �Է��� ����� ���ƾ� ��.)");
			Row.createCell(1).setCellValue("ǥ/�׸� �Ϸù�ȣ");
			Row.createCell(2).setCellValue("�ڷ�����(ǥ,�׸�,��)");
			Row.createCell(3).setCellValue("�ڷῡ ���� ǥ�� �׸� ����(ĸ��)");
			Row.createCell(4).setCellValue("�ڷᰡ ���� �ʹ�ȣ");
	
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
