package sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue<T> {

	// delegate to a list
	private List<T> queue = new ArrayList<>();

	// constructor here is useless, as we have already initialized the queue
	// it just makes the constructor public
	/**
	 * Constructs a new thread safe queue
	 */
	public MyBlockingQueue() {
	}
	
	/**
	 * Constructs a new blocking queue from an existing thread safe queue 
	 * by copying the backing list
	 * @param safeQueue - the queue to be copied
	 */
	public MyBlockingQueue(MyThreadSafeQueue<T> safeQueue) {
		List<T> copy = new ArrayList<>();
		Collections.copy(copy, safeQueue.getQueue());
		this.queue = copy;
	}
	
	/**
	 * Constructs a new blocking queue from an existing blocking queue 
	 * by copying the backing list
	 * @param blockingQueue - the queue to be copied
	 */
	public MyBlockingQueue(MyBlockingQueue<T> blockingQueue) {
		List<T> copy = new ArrayList<>();
		Collections.copy(copy, blockingQueue.getQueue());
		this.queue = copy;
	}
	


	/**
	 * Blocks the program until the given element can be inserted.
	 * @param element - the element to be inserted
	 */
	public void enqueue(T element) {
		synchronized (queue) {
			while (queue.size() == 10) {
				try {
					wait(); // wait until addition possible
				} catch (Exception e) {
				}
			}
			queue.add(element);

			// somebody might have waited for an addition
			// (was 0 previously)
			if (queue.size() == 1) {
				notifyAll();
			}
		}
	}

	/**
	 * Blocks the program until a useful value can be returned.
	 * @return the first element of the queue
	 */
	public T dequeue() {
		T result = null;
		synchronized (queue) {
			while(queue.size() == 0) {
				try {
					wait(); // wait until removal possible
				} catch (InterruptedException e) {
				}
			}
			result = queue.remove(0);
			// somebody might have waited for a removal 
			// (was 10 previously)
			if (queue.size() == 9) {
				notifyAll();
			}
		}
		return result;
	}
	
	// package private
	List<T> getQueue() {
		return queue;
	}
}
