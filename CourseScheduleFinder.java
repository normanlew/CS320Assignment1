import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
//			System.out.print(matcher.group(1) + " ");
//			System.out.println(matcher.group(2));
			String temporary = matcher.group(1);
			temporary = temporary.replace("&amp;", "&");
			String temporary2 = matcher.group(2);
			temporary2 = temporary2.replace("&amp;", "&");

			text = text + temporary + " " + temporary2 + "\n";
		}
		
		System.out.println(text);
		
		String programName;
		String courseID;
		
		System.out.println("\nEnter the program's name: ");
		programName = scanner.nextLine();
		System.out.println("Enter the CourseID: ");
		courseID = scanner.nextLine();
		
		pattern = Pattern.compile("(.*)\\s");
		matcher = pattern.matcher(text);
		System.out.println(matcher.group(0));
//		String courseAbr = matcher.group(0);
//		System.out.println(courseAbr);
		
	}

}
