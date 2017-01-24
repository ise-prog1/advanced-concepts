package threads;

public class SimpleThread extends Thread {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ": I am a simple thread.");
	}
}
