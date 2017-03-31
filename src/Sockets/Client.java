package Sockets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


//TODO: change names of client server to sender receiver

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class Client implements Runnable {

	//WAIT_TIME in milliseconds after sending request before check if energy usage decreased;
	private static final int WAIT_TIME = 600;
	private static final long RESPONSE_INTERVAL = 5000; //5 sec
	
	private ArrayList<Connection> servers; //TODO: updated by a global database??

	protected Socket socket;
	
	protected int lastPackageId;
	
	protected Message message;
	
	private boolean tooMuchEnergy= false;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Client(String serverAddress, int serverPort) {
		servers = new ArrayList<>();
		servers.add(new Connection(serverAddress, serverPort)); //TODO: for now to test, should be filled with actual mulitple servers
	}
	
	protected boolean connectToServer(Connection connection) { //connects to server and tries turning it on, returns true if succeeded
		ObjectOutputStream out;
		ObjectInputStream is;
		
		try {
			socket = new Socket(connection.getIp(), connection.getPort());
			out = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());

			out.writeObject(message);
			
			System.out.println("I send my request to the server");
			
			//TODO: check if this works on two computers
			Message response;

            try {
            	long startingTime = System.currentTimeMillis();
				while ((response = (Message) is.readObject()) != null) {
				    System.out.println("Server: " + response.getData());
				    if (response.getData().equals(Message.AVAILABLE_MESSAGE)) {
				    	//stop searching.
				    	return true;
				    }else if(response.getData().equals(Message.BUSY_MESSAGE)) {
				    	//try another computer
				    	return false;
				    }
				    if(System.currentTimeMillis() > startingTime + RESPONSE_INTERVAL) return false;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(" oh noes connection to server failed, possibly server is offline or entry is wrong");
		}
		

//		logger.error("something went wrong while connecting to server"); 
		//change to logger thing - probably in catch blocks
		return false;
	}
	
	//TODO: implement (now its randomly simulated too much energy)
	private void checkTooMuchEnergy(){  //Energy usage check, set tooMuchEnergy true if threshold reached
		int randomNr = ThreadLocalRandom.current().nextInt(0, 100+1);
		tooMuchEnergy = (randomNr>90);
		System.out.println("Random Nr: " + randomNr);
	}
	

	@Override
	public void run() {
		while(true){
			checkTooMuchEnergy();
			if(tooMuchEnergy){
				for(Connection server : servers){
					message = new Message(Message.CHECK_TYPE, Message.CHECK_MESSAGE);
					boolean serverStarted = connectToServer(server); //response of connected server, true if server was idle and started 
					if(serverStarted){
						tooMuchEnergy = false;
						break;
					}
				}
			}
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
