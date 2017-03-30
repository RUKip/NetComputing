import java.rmi.Naming;
import java.util.ArrayList;  

public class MyClient{  
	public static void main(String args[]){  
		try{  
			ArrayList<Connection> connections = new ArrayList<Connection>();
			Connection c = new Connection("localhost", 2222);
			connections.add(c);
			Adder stub=(Adder)Naming.lookup("rmi://localhost:5000/wonderland");  
			System.out.println(stub.add(34,4)); 
			System.out.println(stub.getEntries(connections));
		}catch(Exception e){System.out.println("Something went wong");}  
	}  
}  