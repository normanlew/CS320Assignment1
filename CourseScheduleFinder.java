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
		
		System.out.println("Quarter: ");
		quarter = scanner.nextLine();
		System.out.println("Year: ");
		year = scanner.nextLine();
		System.out.println("Enter the first initial of the program: ");
		initial = scanner.nextLine().charAt(0);
		
		String website = "https://www.bellevuecollege.edu/classes" + "/" + quarter + year + "?letter=" + initial;
		URL url = new URL(website);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		String input = "";
		String text = "";
		while((input = in.readLine())!=null) {
			text = text + input + "\n";
		}
		
//		System.out.println(text);
		
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
				
		pattern = Pattern.compile("<span class=\"courseID\">" + courseID + "</span> <span class=\"courseTitle\">(.*)</span>");
		matcher = pattern.matcher(text);
		
		String courseTitle = "";
		
		while(matcher.find()) {
			courseTitle = matcher.group(1);
			break;
		}
		

		pattern = Pattern.compile("Item number: </span>(\\w+)</span>");
		matcher = pattern.matcher(text);
		ArrayList<String> itemNumbers = new ArrayList<String>();
		while(matcher.find()) {
			itemNumbers.add(matcher.group(1));
		}
		
		System.out.println("Item Numbers: ");
		for (String s: itemNumbers){
			System.out.println(s);
		}
		
		pattern = Pattern.compile("<a href=\"https://www.bellevuecollege.edu/directory/?SearchString=.*\">(.*//s.*)</a>");
		matcher = pattern.matcher(text);
		ArrayList<String> instructors = new ArrayList<String>();
		while(matcher.find()) {
			instructors.add(matcher.group(0));
		}
		
		
		System.out.println("Instructors: ");
		for (String s: instructors){
			System.out.println(s);
		}
		
		
		
	}

}
