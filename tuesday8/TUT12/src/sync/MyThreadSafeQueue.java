package sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a simple thread-safe queue.
 * @param <T> - The type of the elements to be stored
 */
public class MyThreadSafeQueue<T> {

	// delegate to a list
	private List<T> queue = new ArrayList<>();
	// constructor here is useless, as we have already initialized the queue
	// it just makes the constructor public
	/**
	 * Constructs a new thread safe queue
	 */
	public MyThreadSafeQueue() {
	}
	
	/**
	 * Constructs a new thread safe queue from an existing thread safe queue 
	 * by copying the backing list
	 * @param safeQueue - the queue to be copied
	 */
	public MyThreadSafeQueue(MyThreadSafeQueue<T> safeQueue) {
		List<T> copy = new ArrayList<>();
		Collections.copy(copy, safeQueue.queue);
		this.queue = copy;
	}
	
	/**
	 * Constructs a new thread safe queue from an existing blocking queue 
	 * by copying the backing list
	 * @param safeQueue - the queue to be copied
	 */
	public MyThreadSafeQueue(MyBlockingQueue<T> blockingQueue) {
		List<T> copy = new ArrayList<>();
		Collections.copy(copy, blockingQueue.getQueue());
		this.queue = copy;
	}
	
	/**
	 * Enqueues a given element if the queue has less than 10 elements
	 * @param element - the element to be added
	 * @throws QueueException - if the queue is full
	 */
	public void enqueue(T element) throws QueueException {
		// synchronize it to ensure atomicity for comparison and addition
		// atomicity meaning viewing it as a single operation
		synchronized (queue) {
			if (queue.size() == 10) {
				throw new QueueException();
			}
			queue.add(element);
		}
	}
	
	/**
	 * Returns the first element of the queue or null if the list is empty
	 * @return the element
	 */
	public T dequeue() {
		T result = null;
		synchronized (queue) {
			if (queue.size() > 0) {
				result = queue.remove(0);
			}
		}
		return result;
	}

	// package private
	List<T> getQueue() {
		return queue;
	}
}


