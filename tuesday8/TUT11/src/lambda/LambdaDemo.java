package lambda;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class LambdaDemo {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add(null);
		
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
