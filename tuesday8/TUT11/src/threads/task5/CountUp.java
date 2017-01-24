package threads.task5;

/**
 * This class counts up from 1 to number inclusive.
 * 
 * [1,number]
 */
public class CountUp extends Thread {
	private int number;
	
	public CountUp(int number) {
		this.number = number;
	}
	
	@Override
	public void run() {
		for(int i = 1; i <= number; i++) {
			System.out.println(i);
		}
	}

}
