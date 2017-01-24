package threads;

public class LoopRunnable implements Runnable {

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println(Thread.currentThread().getName() + ": I am a simple runnable.");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted!");
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("Terminated");
	}

}
