package threads.simple;

/**
 * This class extends Thread and overrides its run method.
 */
public class SimpleThread extends Thread {

	@Override
	public void run() {
		// Accessing the other Thread functions is possible with this
		// As SimpleThread extends Thread
		System.out.println(this.getName() + ": I am a simple thread.");
	}
}
