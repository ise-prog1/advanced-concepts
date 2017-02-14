package altklausur.seven;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {

	public static void main(String[] args) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("file.txt"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		List<String> lines = new ArrayList<>();
		while (true) {
			String line =  null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				try {
					reader.close();
				} catch (IOException e1) {}
				throw new RuntimeException(e);
			}
			if (line != null) {
				lines.add(line);
			} else {
				break;
			}
		}
		

		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
