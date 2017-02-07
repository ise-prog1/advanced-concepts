package sockets;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The main class of our application
 */
public class SocketsDemo {

	// settings
	private static final String HOST = "localhost";
	private static final int PORT = 8080;
	
	public static final ExecutorService POOL = Executors.newFixedThreadPool(64 * 2);
	private static Thread server = null;
	
	public static void main(String[] args) throws Exception {
		// start server as thread
		Server serverRunnable = new Server(PORT);
		server = new Thread(serverRunnable);
		server.start();
		
		
		// add clients to pool
		POOL.submit(createClient(Commands.DATETIME));
		POOL.submit(createClient(Commands.DATE));
		POOL.submit(createClient(Commands.TIME));
		
		// show interaction for 5 seconds
		TimeUnit.SECONDS.sleep(5);
		System.out.println("Shutting down main pool..");
		// terminate Server
		serverRunnable.terminate();
		POOL.shutdown(); // shutdown pool
		System.out.println("Main pool shutdown.");
		
	}
	
	/**
	 * Creates a ClientRunnable that executes the given command
	 * @param cmd - the command to be executed by the client
	 * @return a Runnable that represents the client
	 * @throws Exception if a connection could not be established
	 */
	private static Runnable createClient(Commands cmd) throws Exception {
		Client result = new Client(HOST, PORT);
		result.setCommand(cmd);
		result.connect();
		return result;
	}
	
}
