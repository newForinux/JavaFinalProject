package edu.fin.data;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.compress.archivers.zip.*;

public class ZipOpener {
	
	private String input;
	private ArrayList<String> files = new ArrayList<String>();
	private ArrayList<String> error = new ArrayList<String>();
	private ArrayList<String> excelfile = new ArrayList<String>();
	
	public ZipOpener() {
		
	}
	
	public ZipOpener(String input) {
		this.input = input;
	}

	public void start() throws IOException {
		//first unzip data.zip
		unzip(input);
		
		for (String file : files) {
			System.out.println(file);
			unzipDetail(file);
		}
		
		Reader reader = new Reader();
		reader.run(excelfile);
		
		writeError(error);
		System.out.println("done!");
	}
	
	
	public void unzip(String input) {
		File zip = new File(input);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipArchiveInputStream zipInputStream = null;
		ZipArchiveEntry zipEntry = null;
		File target;
		
		try {
			fileInputStream = new FileInputStream(zip);
			
			zipInputStream = new ZipArchiveInputStream(fileInputStream);
			
			while((zipEntry = zipInputStream.getNextZipEntry()) != null) {
				target = new File(zip, zipEntry.getName());
				
				fileOutputStream = new FileOutputStream(zipEntry.getName());
				files.add(zipEntry.getName());
				System.out.println("loading...");
				int length = 0;
				
				while((length = zipInputStream.read()) != -1) {
					fileOutputStream.write(length);
				}
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zipInputStream.close();
                //fileOutputStream.flush();
                //fileOutputStream.close();
                zipInputStream.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public void unzipDetail (String input) {
		File zip = new File(input);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipArchiveInputStream zipInputStream = null;
		ZipArchiveEntry zipEntry = null;
		File target;
		
		try {
			fileInputStream = new FileInputStream(zip);
			zipInputStream = new ZipArchiveInputStream(fileInputStream, Charset.defaultCharset().name(), false);
			
			while((zipEntry = zipInputStream.getNextZipEntry()) != null) {
				target = new File(zipEntry.getName());
				if (zipEntry.isDirectory())
					target.mkdir();
				
				else
					excelfile.add(zipEntry.getName());
				
				System.out.println(zipEntry.getName());
				
				fileOutputStream = new FileOutputStream(zipEntry.getName());
				System.out.println("detail loading...");
				int length = 0;
				while((length = zipInputStream.read()) != -1) {
					fileOutputStream.write(length);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("---------this is unZipDetail problem-------");
			error.add(input);
			
		} finally {
			try {
				zipInputStream.close();
                //fileOutputStream.flush();
                //fileOutputStream.close();
                zipInputStream.close();
			} catch (IOException e) {
				
			}
		}
	}

	
	public void writeError(ArrayList<String> filename) {
		String target = "error.csv";
		PrintWriter outputStream = null;
		
		while (outputStream == null) {
			try
			{
				outputStream = new PrintWriter(new FileOutputStream(target));
			
			}catch (FileNotFoundException e)
			{
				File mkdir_path = new File(target);
				mkdir_path.getParentFile().mkdirs();
			}
		}
		
		for (String line:filename) {
			outputStream.println(line);
		}
		outputStream.close();
	}

	public ArrayList<String> getFiles() {
		return files;
	}
	
	
}
