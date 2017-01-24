package threads;

public class SimpleRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ": I am a simple runnable!");
	}

	
}
