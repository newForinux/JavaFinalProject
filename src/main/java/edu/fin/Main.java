package edu.fin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.fin.data.IllegalInputException;
import edu.fin.data.Reader;
import edu.fin.data.Submit;
import edu.fin.data.SubmitTable;
import edu.fin.data.ZipOpener;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZipOpener opener = new ZipOpener();
		try {
			opener.start(args);
			
			String Output = opener.getOutput();
			ArrayList<String> Excelfile = opener.getExcelfile();
			ArrayList<String> file = opener.getFiles();
			
			Reader reader = new Reader();
			reader.run(Excelfile);
			
			ArrayList<String> zipFileName = reader.getZipFileName();
			HashMap<String, ArrayList<Submit<String>>> studentAssignment = reader.getStudentAssignment();
			HashMap<String, ArrayList<SubmitTable<String>>> studentAssignmentTable = reader.getStudentAssignmentTable();
			
			int index = Output.indexOf(".");
			String output1 = Output.substring(0, index) + "1" + Output.substring(index, Output.length());
			String output2 = Output.substring(0, index) + "2" + Output.substring(index, Output.length());
			
			System.out.println (output1 + " and " + output2 + " is created.");
			
			Runnable assemble = new Assembler(zipFileName, studentAssignment, output1);
			Thread thread_summary = new Thread(assemble);
			
			Runnable assembleTable = new AssemblerTable(zipFileName, studentAssignmentTable, output2);
			Thread thread_table = new Thread(assembleTable);
			
			
			thread_summary.start();
			thread_table.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new IllegalInputException("Please input Command Line.");
		}
	}

}