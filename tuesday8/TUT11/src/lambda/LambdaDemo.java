package lambda;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * BONUS - Not relevant until a later date.
 * 
 * This class simply demonstrates the usefulness of the lambda expressions
 * by using the stream API of Java 8.
 */
public class LambdaDemo {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add(null);
		
		// also parallelStream available for easy parallelization.
		Double average = list.stream()
		.filter(s -> s != null)
		.map(s -> Integer.parseInt(s))
		.filter(i -> i == 3)
		.map(i -> i * 2)
		.collect(Collectors.averagingInt(i -> i));
		
		System.out.println(average);
		list.forEach((s) -> {
			System.out.println(s);
		});
	}
}
