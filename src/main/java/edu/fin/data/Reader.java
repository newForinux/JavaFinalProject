package edu.fin.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Reader {
	private Workbook workbook;
	private Sheet sheet;
	private ArrayList<Submit> submitList;
	private String filename;
	
	
	public void run(String filename, InputStream stream) {
		try {
			if (filename.endsWith("xlsx"))
				workbook = new XSSFWorkbook(stream);
				
			else if (filename.endsWith("xls"))
				workbook = new HSSFWorkbook(stream);
			
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
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }

                }
                System.out.println();

            }
        } catch (Exception e) {
            System.out.println("Error : " + filename);
        }
    }


}
