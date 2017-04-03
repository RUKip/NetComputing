package Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


//TODO: implement good correct logging and error handling
public class SocketServer implements Runnable {
	
	private ServerSocket serverSocket;

	private volatile boolean serverBusy = false;
	
	protected Connection clientConnection;
	private Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public SocketServer() {
		try {
				System.out.println("made server object");
				serverSocket = new ServerSocket(8850); //TODO: hardcoded 8850, so always connect to 8850 in the socketaddress
		} catch (IOException e) {
//			logger.error("Unable to intialize socket");
			e.printStackTrace();
		}
	}

	public boolean isBusy(){
		return serverBusy;
	}
	String address = "";
	public void sendAddress(String address){
		this.address = address;
	}
	UUID id;
	//TODO: this is a simulation, in the actual implementation for this program you should idle/unidle the server he
	public synchronized void setBusy(boolean value){
		serverBusy = value;
		System.out.println("Server set to busy: " + value);
		RestClient t = new RestClient();
		id = UUID.randomUUID();
		if(value){
			if(!this.address.equals("")) {
				t.update(address,id);
			}
			System.out.println("Stuff is uploaded... ");
		}else{
			if(!this.address.equals("")) {
				t.delete(id.toString());
			}
		}
	}
	
	@Override
	public void run() {
			System.out.println("Running");
		long sleepTime;
		while (true) {
				
			Socket socket;
			try {
				System.out.println("Server waitng for connection");
				socket = serverSocket.accept();
				System.out.println("server accepted a client");
				InputHandler handler = new InputHandler(this,socket);
				new Thread(handler).start();
//				System.out.println("Goes wrong?");

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			sleepTime = 100;
			

			try {

				Thread.sleep(sleepTime);
//				System.out.println("Sleeping goes wrong?");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
