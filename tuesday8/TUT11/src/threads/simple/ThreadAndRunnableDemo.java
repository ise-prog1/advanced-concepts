package threads.simple;

/**
 * This class demonstrates the basic way of creating and starting a thread.
 */
public class ThreadAndRunnableDemo {

	public static void main(String[] args) {
		SimpleThread t = new SimpleThread();
		// Thread(Runnable) constructor is called here:
		Thread runnableThread = new Thread(new SimpleRunnable());
		// starting the threads is done by calling start() on the thread objects
		t.start();
		runnableThread.start();
		
		/************
		 * 
		 * Bonus stuff
		 * 
		 ************/
		
		// Lambda Expression work with all functional interfaces.
		// Functional interfaces are those who only have 1 abstract method
		// Runnable method signature: void ()
		// System.out.println is void
		// () <- 0 parameters
		Thread lambda = new Thread(() -> System.out.println("Hello World Lambda"));
		lambda.start();
		
		// Anonymous classes work with more than one abstract method
		// but it can become quickly overwhelming 
		Thread anonymClasses = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Hallo aus der Anonyme Klasse");
			}
		});
		anonymClasses.start();

	}

}
