package threads.interrupt;

/**
 * This class prints a message every half a second until it is interrupted.
 */
public class LoopRunnable implements Runnable {

	@Override
	public void run() {
		// while not interrupted
		while (!Thread.currentThread().isInterrupted()) {
			printAsThread("I am a simple runnable.");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// If a Thread gets interrupted while waiting, the interrupt flag is cleared 
				// and a InterruptedException is thrown.
				printAsThread("Thread interrupted!");
				// Restore the interrupt flag for our loop condition.
				Thread.currentThread().interrupt();
			}
		}
		printAsThread("Terminated.");
	}

	/**
	 * A small helper method that prints the current thread's name with its message.
	 * @param message
	 */
	private static void printAsThread(String message) {
		System.out.println(Thread.currentThread().getName() + ": " + message);
	}
}
