/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

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
		super(input + " is not a valid input.");
		//Used when room config file has an invalid input
		PrintWriter out;
		try {
			out = new PrintWriter("logfile.txt");
			out.println(input + " is not a valid input.");
			out.close();
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		
	}

	public String getMessage() {
		String message = "There is an error with one of your Config File";
		return message;
	}
	
}
