package threads.task5;

/**
 * This class demonstrates the ability to wait for a specific thread to finish
 */
public class ThreadJoinDemo {

	public static void main(String[] args) throws InterruptedException {
		Thread up = new CountUp(100);
		Thread down = new CountDown(100);
		
		up.start();
		up.join();
		// Ensure up is done before starting down
		down.start();
		down.join();
	}
}
