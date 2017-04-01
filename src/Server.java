
import java.rmi.*;
import java.rmi.registry.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class Server{
	public static void main(String args[])throws Exception{
		DatabaseImpl databaseImplementation = new DatabaseImpl();
		
		int port = 8851; //HARDCODED RMI PORT

	    try { // special exception handler for registry creation
	        LocateRegistry.createRegistry(port);
	        System.out.println("java RMI registry created.");
	    } catch (RemoteException e) {
	        // do nothing, error means registry already exists
	        System.out.println("java RMI registry already exists.");
	    }	

	    String hostname = "192.168.0.9"; //HARDCODED TO RMI SERVER

	    String bindLocation = "//" + hostname + ":" + port + "/wonderland";

		// Naming.rebind("rmi://192.168.0.10:8851/wonderland", databaseImplementation);

		try {
	            Naming.bind(bindLocation, databaseImplementation);
	            System.out.println("Server is ready at:" + bindLocation);
	        } catch (RemoteException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (Exception e) {
	            System.out.println(" Server failed: \n" + e);
	        }
	}
}