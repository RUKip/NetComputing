
import java.rmi.*;
import java.rmi.registry.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class Server{

	public Server() throws RemoteException{
		
		DatabaseImpl databaseImplementation = new DatabaseImpl();
		
		int port = 8851; //HARDCODED RMI PORT

	    try { 
	        LocateRegistry.createRegistry(port);
	        System.out.println("java RMI registry created.");
	    } catch (RemoteException e) {
	        System.out.println("java RMI registry already exists.");
	    }	

	    String hostname = "145.97.175.140"; //HARDCODED TO RMI SERVER

	    String bindLocation = "//" + hostname + ":" + port + "/wonderland";

		// Naming.rebind("rmi://192.168.0.10:8851/wonderland", databaseImplementation);

		try {
	            Naming.bind(bindLocation, databaseImplementation);
	            System.out.println("Server is ready at:" + bindLocation + "...");
	        } catch (RemoteException e) {  
	        	System.out.println("Something went wrong with the remote connection");
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
	           System.out.println("The address given is not valid");
	           e.printStackTrace();
	        } catch (Exception e) {
	            System.out.println(" Server failed: \n" + e);
	            e.printStackTrace();
	        }	
	}
	
	public static void main(String [] args) throws RemoteException{
		new Server();
	}
			
}