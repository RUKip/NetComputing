
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

//import org.slf4j.Logger;

//TODO: change names of client server to sender receiver

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import Sockets.Message;
import Sockets.SocketServer;
import Workload.Processor;

public class Client implements Runnable {

	//WAIT_TIME in milliseconds after sending request before check if energy usage decreased;
	private static final int WAIT_TIME = 3000;
	private static final long RESPONSE_INTERVAL = 5000; //5 sec
	
	private List<ConnectionObject> servers;
	
	protected Socket socket;
	
	private String socketServeraddress;
	
	private SocketServer s;
	
	private Processor p;
	
	protected int lastPackageId;
	
	private boolean initialStartup = true;
	
	protected Message message;
	
	private boolean tooMuchEnergy= false, tooLowEnergy = false;
	
//	Logger logger = LoggerFactory.getLogger(Server.class);
	
	public Client(SocketServer s) {
		servers = new ArrayList<>();
		this.s = s;
		new Thread(s).start();
		p = new Processor(2, 25); //can be scaled up or down for own testing purposes.
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
			System.out.println(" oh noes connection to server failed, "
					+ "possibly server is offline or entry is wrong");
		}
		

//		logger.error("something went wrong while connecting to server"); 
		//change to logger thing - probably in catch blocks
		return false;
	}
	
	private void checkEnergyLvl(){  //Energy usage check, set tooMuchEnergy true if threshold reached
		int workload = p.getWorkload();
		System.out.println(workload);
		tooMuchEnergy = (workload >60);
		tooLowEnergy = (workload <15);
	}
	
	
	private List<ConnectionObject> updateList(){
		List<ConnectionObject> list = new ArrayList<ConnectionObject>();		
		try{  
			
			String databaseAddress = "rmi://145.90.133.95:8802/wonderland"; //HARDCODED RMI SERVER

			int port = 8802; //PORT RMI SERVER
			

	        
			DatabaseRemote stub=(DatabaseRemote)
					Naming.lookup(databaseAddress);
			
			if(initialStartup){
				//BELOW IS FOR SOCKET CONNECTION				
				try { 
		            LocateRegistry.createRegistry(port);
		            System.out.println("java RMI registry created at port: ." + port);
		        } catch (RemoteException e) {
		            System.out.println("RMI registry already exists");
		        }
				
				System.out.println("pls give your ip address:");
				Scanner scanner = new Scanner(System.in);
				socketServeraddress = scanner.nextLine();
				
				scanner.close();
				s.sendAddress(socketServeraddress);
				System.out.println("Read: " + socketServeraddress);
				
				int portAddedComputer = 8801;
				
				initialStartup = false;
				System.out.println("added this computer to the RMI server");
				stub.addComputer(socketServeraddress, portAddedComputer);
			}
				list = stub.getConnections();
				System.out.println("Nr of servers connected: " + stub.getConnections().size()); 
			}catch(Exception e){		        	
				System.out.println(e.getMessage());
				System.out.println("Something went wong");
			}  
		
		return list;
	}

	@Override
	public void run() {
		 while (!Thread.currentThread().isInterrupted()) {
			servers = updateList();
			checkEnergyLvl();
			if(tooMuchEnergy){
				for(ConnectionObject server : servers){
					if(!server.getIp().equals("/"+socketServeraddress)){
						System.out.println("IP: " + server.getIp());
						System.out.println("Connected to: " +"/" + socketServeraddress);
						message = new Message(Message.CHECK_TYPE, Message.CHECK_MESSAGE);
						boolean serverStarted = connectToServer(server); //response of connected server, true if server was idle and started 
						System.out.println("Client connected to server: " + server.getIp());
						if(serverStarted){
							tooMuchEnergy = false;
							break;
						}
					}
				}
			}else if(tooLowEnergy){
				s.setBusy(false);
			}
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		 p.stop();
		 System.out.println("Peace out");
	}
	
	public static void main(String[] argv){
		SocketServer s = new SocketServer();
		new Client(s).run();
	}
}
