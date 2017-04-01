
import java.rmi.*;
import java.rmi.registry.*;

public class Server{
public static void main(String args[])throws Exception{
	DatabaseImpl r = new DatabaseImpl();
	
	int port = 8851;

    try { // special exception handler for registry creation
        LocateRegistry.createRegistry(port);
        System.out.println("java RMI registry created.");
    } catch (RemoteException e) {
        // do nothing, error means registry already exists
        System.out.println("java RMI registry already exists.");
    }	

    String hostname = "192.168.0.10";

    String bindLocation = "//" + hostname + ":" + port + "/Hello";

	Naming.rebind("rmi://192.168.0.10:8851/wonderland",r);
	
	// try {
 //            Naming.bind(bindLocation, Hello);
 //            System.out.println("Server is ready at:" + bindLocation);
 //        } catch (RemoteException e) {
 //            // TODO Auto-generated catch block
 //            e.printStackTrace();
 //        } catch (MalformedURLException e) {
 //            // TODO Auto-generated catch block
 //            e.printStackTrace();
 //        } catch (Exception e) {
 //            System.out.println("Addition Serverfailed: " + e);
 //        }
	// }
}}