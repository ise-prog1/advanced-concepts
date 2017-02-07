package filestreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This class demonstrates the java.nio Api
 */
public class ReadWithNio {

	public static void main(String[] args) throws IOException {
		Path input = Paths.get(".", "data", "input.txt");
		if (Files.notExists(input)) {
			System.out.println("Path " + input + "does not exist!");
			return;
		}
		
		// better use Files#newBufferedReader
		// input is small enough to get away with readAllLines
		List<String> lines = Files.readAllLines(input);
		
		/*
		 * Solution for splitting a list into sub sets is based on this link:
		 * http://www.baeldung.com/java-list-split
		 * (See 5.3)
		 */
		int[] indexes = 
			      Stream.of(IntStream.of(-1), IntStream.range(0, lines.size()) 
			    		  
			      .filter(i -> lines.get(i).isEmpty()), IntStream.of(lines.size()/*range is exclusive*/))
			      // generates [-1],[0,1,2,..,lines.size() -1],[lines.size()]
			      // middle part contains only empty line indexes
			      .flatMapToInt(s -> s) // flatten the IntStreams to a single IntStream
			      // [-1, 0, ..., lines.size()]
			      .toArray(); // convert to array
		
		List<List<String>> subSets =
				IntStream.range(0, indexes.length -1)
				//magic:
				// first run: -1 gets turned into 0 and if there are zero blank lines, indexes would still contain at least two elements
				// sublist is [a,b) (lastIndex not added)
				.mapToObj(i -> lines.subList(indexes[i] + 1, indexes[i +1]))
				.collect(Collectors.toList());
		
		
		StringBuilder header = new StringBuilder();
		List<User> users = createUsersFromSubSets(subSets, 
				(list) -> TxtToCsvMain.addToBuilder(header, ",", list));
		
		
		System.out.println(users);
		
		List<String> linesToWrite = new ArrayList<>();
		linesToWrite.add(header.toString());
		linesToWrite.addAll(users.stream().map(u -> u.toCsvString(",")).collect(Collectors.toList()));
		// Write all the lines to disk, overwriting previous files
		Files.write(Paths.get(".", "data", "output-nio.csv"), linesToWrite);
	}
	
	/**
	 * Creates a list of Users from a list of subsets.
	 * Each subSet represents either the header or a user
	 * @param subSets - a list of subsets
	 * @param action - action to be executed with the header set
	 * @return a list of users
	 */
	private static List<User> createUsersFromSubSets(List<List<String>> subSets, java.util.function.Consumer<List<String>> action) {
		List<User> users = new ArrayList<>();
		for (List<String> list : subSets) {
			if(list.get(0).startsWith("Kundennummer")) {
				//add headers to Builder
				action.accept(list);
				continue;
			}
			if (list.size() != 6) {
				//ignore false formatted data
				continue;
			}
			User user = new User();
			user.setId(Integer.parseInt(list.get(0)));
			user.setLastName(list.get(1));
			user.setName(list.get(2));
			user.setStreet(list.get(3));
			user.setPlz(Integer.parseInt(list.get(4)));
			user.setLocation(list.get(5));
			users.add(user);
		}
		return users;
	}

}
