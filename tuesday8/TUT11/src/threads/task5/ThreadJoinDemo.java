package threads.task5;

public class ThreadJoinDemo {

	public static void main(String[] args) throws InterruptedException {
		Thread up = new CountUp(100);
		Thread down = new CountDown(100);
		up.start();
		up.join();
		down.start();
		down.join();
	}
}
