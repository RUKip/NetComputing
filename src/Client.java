import java.rmi.Naming;
import java.util.ArrayList;

public class Client {
	public static void main(String args[]){  
		try{  
			DatabaseRemote stub=(DatabaseRemote)
					Naming.lookup("rmi://192.168.0.10:8851/wonderland");  
			stub.addComputer("AliceInWonderland", 10);
			System.out.println(stub.getConnections().size()); 
			}catch(Exception e){System.out.println("Something went wong");}  
	}  
}
