// Norman  Lew
// CS 320
// Winter 2018

// Assignment 1

// This program will look up a class for the user from the Bellevue College Time Schedule.  It asks the
// user for the quarter, year, and subject area.  Then, it asks the user for which class they would like
// more information on.


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseScheduleFinder {

	public static void main(String[] args) throws IOException {
		
		String quarter;
		String year;
		char initial;
		
		Scanner scanner = new Scanner(System.in);
		
		// Prompt user for the classes they wish to look for
		System.out.println("Quarter: ");
		quarter = scanner.nextLine();
		System.out.println("Year: ");
		year = scanner.nextLine();
		System.out.println("Enter the first initial of the program: ");
		initial = scanner.nextLine().charAt(0);
		
		// Lookup the programs that the user would like to view
		String website = "https://www.bellevuecollege.edu/classes" + "/" + quarter + year + "?letter=" + initial;
		URL url = new URL(website);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		String input = "";
		String text = "";
		while((input = in.readLine())!=null) {
			text = text + input + "\n";
		}
		
//		System.out.println(text);
		
		// Print out the possible programs that the user can view
		System.out.println("\nPrograms:");
		
		Pattern pattern = Pattern.compile("<a href=\"/classes/" + quarter + year + "/.*>(.*)</a>\\s(.*)");
		Matcher matcher = pattern.matcher(text);
		text = "";
		while (matcher.find()) {
			String temporary = matcher.group(1);
			temporary = temporary.replace("&amp;", "&");
			String temporary2 = matcher.group(2);
			temporary2 = temporary2.replace("&amp;", "&");

			text = text + temporary + " " + temporary2 + "\n";
		}
		
		System.out.println(text);
		
		String programName;
		String courseID;
		String programNameAbbr = "";
		
		// Prompt the user for the program and course that they would like more information on
		System.out.println("\nEnter the program's name: ");
		programName = scanner.nextLine();
		System.out.println("Enter the CourseID: ");
		courseID = scanner.nextLine();
		
		pattern = Pattern.compile(programName + "\\s\\((\\w*)");
		matcher = pattern.matcher(text);
		
		while(matcher.find()) {
			programNameAbbr = matcher.group(1);
			break;
		}

		
		website = "https://www.bellevuecollege.edu/classes" + "/" + quarter + year + "/" + programNameAbbr;

		
		url = new URL(website);
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		input = "";
		text = "";
		while((input = in.readLine())!=null) {
			text = text + input + "\n";
		}
		
//		System.out.println(text);
				
		// This section uses regex to pull the block of information on the course that we will need to 
		// further parse for the course information
		pattern = Pattern.compile("<span class=\"courseID\">" + courseID + "</span>(.*?)<span class=\"courseID\">", Pattern.DOTALL);
		matcher = pattern.matcher(text);
		String text2 = "";
		while(matcher.find()) {
			text2 = matcher.group(1);
			break;
		}
		if (text2.equals(""))
		{
			pattern = Pattern.compile("<span class=\"courseID\">" + courseID + "(.*)", Pattern.DOTALL);
			matcher = pattern.matcher(text);
			while(matcher.find()) {
				text2= matcher.group(1);
				break;
			}
		}
		
//		System.out.println(text2);
		
		text = "";
		String courseTitle = null;
		
		// Retrieve the course title
		pattern = Pattern.compile("<span class=\"courseTitle\">(.*)</span>");
		matcher = pattern.matcher(text2);
		while(matcher.find()) {
			courseTitle = matcher.group(1);
			break;
		}
		
//		System.out.println(courseTitle);
		
		ArrayList<String> classes = new ArrayList<String>();
		
		// This selects, further, the block that needs to be parsed
		pattern = Pattern.compile("Item number:(.*?)<li class=\"bookstoreinfo\">", Pattern.DOTALL);
		matcher = pattern.matcher(text2);
		while(matcher.find()) {
			classes.add(matcher.group(0));
		}
		
		System.out.println("\n==================================");
		
		for(String s : classes)
		{
			System.out.println("Code: " + courseID);
			
			// Each class taught for a course in a quarter is assigned an Item number.
			// For each Item number, print the Item number, the instructor for the class, and the Days
			// that the classes are held.  
			pattern = Pattern.compile("Item number: </span>(.*)</span>");
			matcher = pattern.matcher(s);
			if (matcher.find()) {
				System.out.println("Item#: " + matcher.group(1));
				System.out.println("Title: " + courseTitle);
			}
			
			pattern = Pattern.compile("<a href=\"https://www.bellevuecollege.edu/directory/?.*>(.*)</a>");
			matcher = pattern.matcher(s);
			if (matcher.find()) {
				System.out.println("Instructor: " + matcher.group(1));
			}
			
			
			pattern = Pattern.compile("<abbr title=\"(.*)\">");
			matcher = pattern.matcher(s);
			while (matcher.find()) {
				System.out.println("Days: " + matcher.group(1));
			}
			
			pattern = Pattern.compile("<span class=\"days online\">(.*)</span>");
			matcher = pattern.matcher(s);
			while (matcher.find()) {
				System.out.println("Days: " + matcher.group(1));
			}
			
			System.out.println("==================================");
		}
		
		
		

//		pattern = Pattern.compile("Item number: </span>(\\w+)</span>");
//		matcher = pattern.matcher(text);
//		ArrayList<String> itemNumbers = new ArrayList<String>();
//		while(matcher.find()) {
//			itemNumbers.add(matcher.group(1));
//		}
//		
//		System.out.println("Item Numbers: ");
//		for (String s: itemNumbers){
//			System.out.println(s);
//		}
//		
//		pattern = Pattern.compile("<a href=\"https://www.bellevuecollege.edu/directory/?SearchString=.*\">(.*//s.*)</a>");
//		matcher = pattern.matcher(text);
//		ArrayList<String> instructors = new ArrayList<String>();
//		while(matcher.find()) {
//			instructors.add(matcher.group(0));
//		}
//		
//		
//		System.out.println("Instructors: ");
//		for (String s: instructors){
//			System.out.println(s);
//		}
		
		
		
	}

}
