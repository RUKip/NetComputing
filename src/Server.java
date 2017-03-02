
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


//TODO: implement good correct logging and error handling
public class Server implements Runnable {
	
	private ServerSocket serverSocket;
	private int packageId;
	private int clientId = 1;
	
	
	protected Connection clientConnection;
	private Message message;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	private Boolean tooMuchEnergy= false;
	
	
	public Server() {
		try {
				serverSocket = new ServerSocket(8850); //TODO: why 8850???
		} catch (IOException e) {
//			logger.error("Unable to intialize socket");
			e.printStackTrace();
		}
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
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
