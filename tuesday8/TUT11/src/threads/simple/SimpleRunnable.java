package threads.simple;

/**
 * This class implements the Runnable interface.
 * It prints its Thread name and a simple message.
 */
public class SimpleRunnable implements Runnable {

	@Override
	public void run() {
		// Accessing the Thread functions is possible with
		// Thread.currentThread()
		System.out.println(Thread.currentThread().getName() + ": I am a simple runnable!");
	}

	
}
