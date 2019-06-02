package edu.fin;
import java.io.IOException;

import edu.fin.data.ZipOpener;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZipOpener opener = new ZipOpener(args[0]);
		try {
			opener.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}