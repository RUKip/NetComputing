import java.rmi.Naming;
import java.util.ArrayList;

public class Client {
	public static void main(String args[]){  
		try{  
			DatabaseRemote stub=(DatabaseRemote)Naming.lookup("rmi://localhost:6666/wonderland");  
			stub.addComputer("192.0.0.1", 10);
			System.out.println(stub.getConnections().size()); 
			}catch(Exception e){System.out.println("Something went wong");}  
	}  
}
