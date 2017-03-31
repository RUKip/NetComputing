package Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


//TODO: implement good correct logging and error handling
public class Server implements Runnable {
	
	private ServerSocket serverSocket;

	private volatile boolean serverBusy = false;
	
	protected Connection clientConnection;
	private Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Server() {
		try {
				System.out.println("made server object");
				serverSocket = new ServerSocket(8850); //TODO: why 8850???
		} catch (IOException e) {
//			logger.error("Unable to intialize socket");
			e.printStackTrace();
		}
	}

	public boolean isBusy(){
		return serverBusy;
	}
	
	public void setBusy(boolean value){
		serverBusy = value;
	}
	
	@Override
	public void run() {
		long executionTime, sleepTime;
		while (true) {
			executionTime = System.currentTimeMillis();
				
			Socket socket;
			try {
				System.out.println("Server waitng for connection");
				socket = serverSocket.accept();
				System.out.println("server accepted a client");
				new InputHandler(this, socket).run();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			executionTime -= System.currentTimeMillis();
			sleepTime = 40 + executionTime;
			

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
