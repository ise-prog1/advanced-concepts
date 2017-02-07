package filestreams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TxtToCsvMain {

	static List<User> readUsers = new ArrayList<>();
	private static User currentUser = null;

	public static void main(String[] args) throws IOException {
		// always use buffered Reader for costly reading operations
		BufferedReader reader = new BufferedReader(new FileReader("./data/input.txt"));
		String line = null;
		int counter = 0;
		
		for (int i = 0; i < 6; i++) {
			reader.readLine(); // skip header
		}
		
		while ((line = reader.readLine()) != null) {
			// start a new user on a  empty line
			if (line.trim().isEmpty()) {
				if (currentUser != null) {
					readUsers.add(currentUser);
				}
				currentUser = new User();
				counter = 0;
				continue;
			}
			updateField(line, counter, currentUser);
			counter++;

		}
		// the last user might not be added yet, add him if that is the case
		if (!readUsers.contains(currentUser)) {
			readUsers.add(currentUser);
		}

		System.out.println(readUsers);
		reader.close(); // dont forget to close your readers/writers/streams!
		
		// PrintWriter offers the println abstraction, very useful!
		PrintWriter writer = new PrintWriter(new FileWriter("./data/output.csv"));
		StringBuilder header = new StringBuilder();
		addToBuilder(header, ",", "Kundennummer", "Nachname", "Vorname", "Strasse", "Postleitzahl", "Ort");
		writer.println(header.toString());
		for (User u : readUsers) {
			writer.println(u.toCsvString(","));
		}
		writer.close();
	}

	/**
	 * Sets the a specific field on a user based on the given number
	 * @param line - the content to be set
	 * @param number - the type of field to be set
	 * @param user - the target for our set operations
	 */
	private static void updateField(String line, int number, User user) {
		switch (number) {
		case 0:
			user.setId(Integer.parseInt(line));
			break;
		case 1:
			user.setLastName(line);
			break;
		case 2:
			user.setName(line);
			break;
		case 3:
			user.setStreet(line);
			break;
		case 4:
			user.setPlz(Integer.parseInt(line));
			break;
		case 5:
			user.setLocation(line);
			break;
		default:
			break;
		}
		
		
	}
	
	/**
	 * Adds an array of objects to a given StringBuilder separated by separator
	 * @param builder - the builder to be added to
	 * @param separator - the separator between each object
	 * @param objects - the array of objects to be added
	 */
	public static void addToBuilder(StringBuilder builder, String separator, Object... objects) {
		boolean first = true;
		for(Object o :objects) {
			if (first) {
				// do not add a separator at the beginning
				first = false;
			} else {
				builder.append(separator);
			}	
			builder.append(o);
		}
	}

	/**
	 * Adds a list of objects to a given StringBuilder separated by separator
	 * @param builder - the builder to be added to
	 * @param separator - the separator between each object
	 * @param objects - the list of objects to be added
	 */
	public static void addToBuilder(StringBuilder builder, String separator, List<? extends Object> objects) {
		addToBuilder(builder, separator, objects.toArray());
	}
}
