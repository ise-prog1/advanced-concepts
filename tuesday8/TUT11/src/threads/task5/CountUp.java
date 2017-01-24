package threads.task5;

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
