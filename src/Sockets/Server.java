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

	private boolean serverBusy = false; //TODO: Will be accessed by multiple thread so is volatile
	
	protected Connection clientConnection;
	private Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Server() {
		try {
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
	
	public void run() {
		long executionTime, sleepTime;
		while (true) {
			executionTime = System.currentTimeMillis();
				
			Socket socket;
			try {
				socket = serverSocket.accept();
				new InputHandler(this, socket).run();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			executionTime -= System.currentTimeMillis();
			sleepTime = 40 + executionTime;
			
			if(serverBusy){
				//TODO: if server not already busy, active the rest of the server(stop idling) 
				System.out.println("I am Busy so don't accept requests");
			}else{
				//TODO: if server not already not busy, stop the rest of the server (start idling)
				System.out.println("I am not busy, so give me your requests");
			}
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
