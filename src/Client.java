import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	public static void main(String args[]){  
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
			System.out.println(stub.getConnections().size()); 
			}catch(Exception e){System.out.println("Something went wong");}  
	}  
}
