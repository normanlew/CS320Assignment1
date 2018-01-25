// Norman  Lew
// CS 320
// Winter 2018

// Assignment 1

//  This program reads the A.Java source file and extracts all the variables defined in the class.
//  It outputs the findings to the display console.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableParser {

	public static void main(String[] args) throws IOException {
		// Open the A.java file.  Read all of its contents and store it into the text string variable
		String fileName = "A.java";
		FileReader  fileReader = new FileReader(fileName);
		
		BufferedReader in = new BufferedReader(fileReader);
		
		String input = "";
		String text = "";
		while((input = in.readLine())!=null) {
			text = text + input + "\n";
		}
		
		
		Pattern pattern = Pattern.compile("(\\w+)\\s(\\w+);");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			System.out.println("Type:  " + matcher.group(1));
			System.out.println("Variable name:  " + matcher.group(2));
			System.out.println("Value:  NULL");
			System.out.println("^^^^^^^^^^^^^^^^^^^");		
		}	
		
		
	
		pattern = Pattern.compile("(\\w+)\\s(\\w+)\\s=\\s(.*);");
		matcher = pattern.matcher(text);
		
		text = "";
		while (matcher.find()) {
			System.out.println("Type:  " + matcher.group(1));
			System.out.println("Variable name:  " + matcher.group(2));
			System.out.println("Value:  " + matcher.group(3));
			System.out.println("^^^^^^^^^^^^^^^^^^^");		
		}		
				
	}

}
