package edu.fin.data;

public class IllegalInputException extends Exception {
	
	public IllegalInputException() {
		super ("File is already damaged. Exclude it.");
	}
	
	public IllegalInputException(String msg) {
		super (msg);
	}
}