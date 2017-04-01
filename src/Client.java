
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import Sockets.Connection;
import Sockets.Message;


//TODO: change names of client server to sender receiver

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class Client implements Runnable {

	//WAIT_TIME in milliseconds after sending request before check if energy usage decreased;
	private static final int WAIT_TIME = 3000;
	private static final long RESPONSE_INTERVAL = 5000; //5 sec
	
	private List<ConnectionObject> servers; //TODO: updated by a global database??

	private ConnectionObject guiConnection;
	
	protected Socket socket;
	
	protected int lastPackageId;
	
	protected Message message;
	
	private boolean tooMuchEnergy= false;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Client(String serverAddress, int serverPort) {
		servers = new ArrayList<>();
		guiConnection = new ConnectionObject(serverAddress, serverPort); //TODO: for now to test, should be filled with actual mulitple servers
	}
	
	protected boolean connectToServer(ConnectionObject connection) { //connects to server and tries turning it on, returns true if succeeded
		ObjectOutputStream out;
		ObjectInputStream is;
		
		try {
			socket = new Socket(connection.getIp(), connection.getPort());
//			System.out.println("I made new socket" );

			out = new ObjectOutputStream(socket.getOutputStream());
//			System.out.println("I made objectoutputstream");
		
			
			out.writeObject(message);
//			System.out.println("I send my request to the server");
			
			out.flush();
			
			is = new ObjectInputStream(socket.getInputStream());
//			System.out.println("I made ojbect input stream");

			
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
	
	
	private List<ConnectionObject> updateList(){
		List<ConnectionObject> list = new ArrayList<ConnectionObject>();
		
		try{  
			String databaseAddress = "rmi://192.168.0.10:8851/wonderland";
			String addressAddedComputer;
			int portAddedComputer;
			
	        int port = 8851;

	        try { // special exception handler for registry creation
	            LocateRegistry.createRegistry(port);
	            System.out.println("java RMI registry created.");
	        } catch (RemoteException e) {
	            // do nothing, error means registry already exists
	            System.out.println("java RMI registry already exists.");
	        }
			
			Scanner s = new Scanner(System.in);
			System.out.println("Type Address(Ip):");
			addressAddedComputer = s.nextLine();
			System.out.println("Type port:");
			portAddedComputer = s.nextInt();
			
			DatabaseRemote stub=(DatabaseRemote)
					Naming.lookup(databaseAddress);  
			stub.addComputer(addressAddedComputer, portAddedComputer);
			list = stub.getConnections();
			System.out.println(stub.getConnections().size()); 
			}catch(Exception e){System.out.println("Something went wong");}  
		
		return list;
	}

	@Override
	public void run() {
		while(true){
			servers = updateList();
			servers.add(guiConnection);
			checkTooMuchEnergy();
//			System.out.println(" checked for too much energy" );
			if(tooMuchEnergy){
//				System.out.println(" too much energy" );
				for(ConnectionObject server : servers){
					message = new Message(Message.CHECK_TYPE, Message.CHECK_MESSAGE);
					boolean serverStarted = connectToServer(server); //response of connected server, true if server was idle and started 
					System.out.println("client connected to server");
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
