package threads.task5;

/**
 * This class counts down from the given number to 1 inclusive.
 * 
 * [1,number]
 */
public class CountDown extends Thread {
	private int number; 
	public CountDown(int number) {
		this.number = number;
	}
	
	@Override
	public void run() {
		for(int i = number; i >= 1; i--) {
			System.out.println(i);
		}
	}

}
