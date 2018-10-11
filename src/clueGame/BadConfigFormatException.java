package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 
 * @author Adam Kinard
 * @author Jay Harrison
 * 
 *
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
	}


	public BadConfigFormatException(String input) {
		super(input + " is not a valid card type.");
		//Used when room config file has an invalid input
		PrintWriter out;
		try {
			out = new PrintWriter("logfile.txt");
			out.println(input + " is not a valid card type.");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
	
}
