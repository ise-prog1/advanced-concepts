package sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the server.
 * It accepts connections and adds them to a thread pool and a collection.
 * The actual communication is done by the Delegate class
 */
public class Server implements Runnable {

	private ServerSocket socket;
	private ExecutorService pool = Executors.newFixedThreadPool(64);
	private Collection<Delegate> connections = new LinkedList<>();
	// volatile forces java to always read this variable from memory.
	public volatile boolean active = true;

	public Server(int port) throws IOException {
		this.socket = new ServerSocket(port);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted() && active) {
			Socket client = null;
			try {
				client = socket.accept();
			} catch (IOException e) {
				Thread.currentThread().interrupt();
			}
			if (client != null) {
				Delegate runnable = new Delegate(client);
				connections.add(runnable);
				pool.submit(runnable);
			}
		}

		System.out.println("Server down!");
	}

	/**
	 * This method gets called when we want to shutdown the server.
	 * It notifies the Delegates by calling their terminate() method,
	 * shuts down the connection pool and closes the socket
	 */
	public void terminate() {
		for (Delegate con : connections) {
			con.terminate();
		}
		pool.shutdown();
		try {
			socket.close();
		} catch (IOException e) {
		}
		active = false;
	}
}

/**
 * A Delegate manages a single connection to a client.
 * Alternative names: Session, ClientConnection
 */
class Delegate implements Runnable {

	private Socket socket;
	public volatile boolean active = true;

	public Delegate(Socket client) {
		this.socket = client;
	}

	@Override
	public void run() {
		// setup wrapper around the connection's streams
		BufferedReader fromClient = null;
		PrintWriter toClient = null;
		try {
			fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
			toClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		} catch (IOException e) {
			return;
		}
		// two possible ways to terminate the loop
		// interrupt (mainly for pool shutdown)
		// and the active flag, for external control
		while (!Thread.currentThread().isInterrupted() && active) {
			String line = null;
			try {
				line = fromClient.readLine();
			} catch (IOException e1) {
				Thread.currentThread().interrupt();
			}
			if (line == null) {
				Thread.currentThread().interrupt();
				continue; // force re-check of loop condition
			}
			
			// send response matching to asked value
			switch (Commands.valueOf(line)) {
			case DATE:
				toClient.println(LocalDate.now());
				toClient.flush();
				break;
				
			case TIME:
				toClient.println(LocalTime.now());
				toClient.flush();
				break;
				
			case DATETIME:
				toClient.println(LocalDateTime.now());
				toClient.flush();
				break;
				
			case TERMINATE:
			default:
				terminate();
				Thread.currentThread().interrupt();
				break;
			}
			
		}

		System.out.println("Delegate Down!");
	}

	/**
	 * This method terminates a connection by closing the socket
	 */
	public void terminate() {
		try {
			socket.close();
		} catch (IOException e) {
		}
		active = false;
	}
}
