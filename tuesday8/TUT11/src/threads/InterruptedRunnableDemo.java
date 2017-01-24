package threads;

public class InterruptedRunnableDemo {
	
	public static void main(String[] args) throws InterruptedException {
		Thread r = new Thread(new LoopRunnable());
		r.start();
		Thread.sleep(5000);
		System.out.println(Thread.currentThread().getName() + ": Trying interrupt.");
		r.interrupt();
	}
}
