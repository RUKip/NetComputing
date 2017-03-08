package Sockets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


//TODO: change names of client server to sender receiver

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class Client implements Runnable {

	//WAIT_TIME in milliseconds after sending request before check if energy usage decreased;
	private static final int WAIT_TIME = 600;
	
	private ArrayList<Connection> servers; //TODO: updated by a global database?? Maybe use REST/SOAP for this??

	protected Socket socket;
	
	protected int lastPackageId;
	
	protected Message message;
	
	private boolean tooMuchEnergy= false;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Client(String serverAddress, int serverPort) {
		servers = new ArrayList<>();
		servers.add(new Connection(serverAddress, serverPort)); //TODO: for now to test
	}
	
	protected boolean connectToServer(Connection connection) { //connects to server and tries turning it on, returns true if succeeded
		ObjectOutputStream out;
		
		try {
			socket = new Socket(connection.getIp(), connection.getPort());
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
			
			//TODO: read response here
			
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

//		logger.error("something went wrong while connecting to server"); 
		//change to logger thing - probably in catch blocks
		return false;
	}
	
	//TODO: implement
	private void checkTooMuchEnergy(){  //Energy usage check, set tooMuchEnergy true if threshold reached
		tooMuchEnergy = true;
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
