package sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * This class represent a simple client 
 * that sends the set command every second
 * and prints the response to the console
 */
public class Client implements Runnable {

	private Socket socket;
	private String host;
	private int port;
	private Commands command;

	public Client(String host, int port) {
		this.socket = new Socket();
		this.host = host;
		this.port = port;
		this.command = Commands.DATETIME;
	}

	
	/**
	 * Tries to connect to the set address and port.
	 * @throws Exception if the connection could not be established.
	 */
	public void connect() throws Exception {
		socket.connect(new InetSocketAddress(this.host, this.port));
		if (!this.socket.isConnected()) {
			throw new Exception("Connection failed.");
		}
	}

	@Override
	public void run() {
		//wrap connection to and from the server in Writer and Reader
		BufferedReader fromServer = null;
		PrintWriter toServer = null;
		try {
			fromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
			toServer = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8"))));
		} catch (IOException e) {
		}
		
		// do a maximum of 10 times
		int counter = 0;
		while (!Thread.currentThread().isInterrupted()) {
			// send a command to the server
			toServer.println(command.toString());
			toServer.flush();
			// read response
			String line = null;
			try {
				line = fromServer.readLine();
			} catch (IOException e) {
				//hard error; something is wrong with the server; abort
				Thread.currentThread().interrupt();
			}
			if (line != null) {
				System.out.println("Got line: " + line);
			} else {
				//hard error; something is wrong with the server; abort
				Thread.currentThread().interrupt();
			}
			if (counter == 10) {
				//notify the server, that we are done
				toServer.println(Commands.TERMINATE.toString());
				toServer.flush();
				Thread.currentThread().interrupt();
			}
			counter++;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//notify the server, that we are done
				toServer.println(Commands.TERMINATE.toString());
				toServer.flush();
				Thread.currentThread().interrupt();
			}
		}
		// Shutdown process
		try {
			socket.close(); // also closes the underlying streams
		} catch (IOException e) {
			System.out.println("Client: IOException");
		} finally {
			System.out.println("Client down!");
		}
	}
	
	public void setCommand(Commands cmd) {
		this.command = cmd;
	}

}
