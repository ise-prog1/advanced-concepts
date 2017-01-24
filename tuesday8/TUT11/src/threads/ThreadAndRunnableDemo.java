package threads;

public class ThreadAndRunnableDemo {

	public static void main(String[] args) {
		SimpleThread t = new SimpleThread();
		Thread runnableThread = new Thread(new SimpleRunnable());
		t.start();
		runnableThread.start();
		Thread lambda = new Thread(() -> System.out.println("Hello World"));
		lambda.start();
		Thread anonymClasses = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Hallo aus der Anonyme Klasse");
			}
		});
		anonymClasses.start();

	}

}
