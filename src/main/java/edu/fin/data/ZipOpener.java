package edu.fin.data;
import java.io.*;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.compress.archivers.zip.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


public class ZipOpener {
	
	private String input;
	private String output;
	private boolean help;
	private ArrayList<String> files = new ArrayList<String>();
	private ArrayList<String> error = new ArrayList<String>();
	private ArrayList<String> excelfile = new ArrayList<String>();
	
	public void start(String[] args) throws IOException {
		
		//implement commons CLI Option
		Options options = createOptions();
		
		if (parseOptions(options, args)) {
			if (help) {
				printHelp(options);
				return;
			}
		
			System.out.println("Extracting zip file...");
			unzip(input);
		
			for (String file : files) {
				unzipDetail(file);
				System.out.println(file + " is successfully extracted.");
			}
		
			writeError(error);
		}
	}
	
	
	
	public void unzip(String input) {
		File zip = new File(input);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipArchiveInputStream zipInputStream = null;
		ZipArchiveEntry zipEntry = null;
		
		try {
			fileInputStream = new FileInputStream(zip);
			
			zipInputStream = new ZipArchiveInputStream(fileInputStream);
			
			while((zipEntry = zipInputStream.getNextZipEntry()) != null) {

				
				fileOutputStream = new FileOutputStream(zipEntry.getName());
				files.add(zipEntry.getName());
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
                fileOutputStream.flush();
                fileOutputStream.close();
                zipInputStream.close();
			} catch (IOException e) {
				new IllegalInputException("detected critical damage in zip file.");
				e.printStackTrace();
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
		
		int index = input.indexOf(".");
		String inputName = input.substring(0, index);
		
		try {
			fileInputStream = new FileInputStream(zip);
			zipInputStream = new ZipArchiveInputStream(fileInputStream, Charset.defaultCharset().name(), false);
			
			while((zipEntry = zipInputStream.getNextZipEntry()) != null) {
				target = new File(zipEntry.getName());
				if (zipEntry.isDirectory()) {
					target.mkdir();
					continue;
				}
				
				else
					excelfile.add(inputName + zipEntry.getName());
				
				fileOutputStream = new FileOutputStream(inputName + zipEntry.getName());

				int length = 0;
				while((length = zipInputStream.read()) != -1) {
					fileOutputStream.write(length);
				}
			}
			
		} catch (IOException e) {
			new IllegalInputException();
			error.add(input);
			
		} finally {
			try {
				zipInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                zipInputStream.close();
			} catch (IOException e) {
				new IllegalInputException("detected critical damage in excel file.");
				e.printStackTrace();
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
	
	private boolean parseOptions (Options options, String args[]) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
			
		}catch (Exception e){
			printHelp(options);
			return false;
		}
		
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		//add option to receive input file path
		options.addOption(Option.builder("i").longOpt("input")
							.desc("Set a data file path")
							.hasArg()
							.argName("Input path")
							.required()
							.build());
		
		//add option to receive output file path
		options.addOption(Option.builder("o").longOpt("output")
							.desc("Set a result file path")
							.hasArg()
							.argName("Output path")
							.required()
							.build());
		
		options.addOption(Option.builder("h").longOpt("help")
							.desc("Show a Help page")
							.build());
		
		return options;
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Data Assembler";
		String footer = "";
		formatter.printHelp("JavaFinalProject", header, options, footer, true);
	}

	

	public String getOutput() {
		return output;
	}

	public ArrayList<String> getExcelfile() {
		return excelfile;
	}
	
}