import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;  


/*This is the implementation of the methods*/
public class AdderRemote extends UnicastRemoteObject implements Adder{  
	AdderRemote()throws RemoteException{  
	super();  
	}  
	public int add(int x,int y){
		return x+y;
	}  
	public ArrayList<Connection> getEntries(ArrayList<Connection> array){
		for(Connection c: array){
			System.out.println(c.getPort());
			System.out.println(c.getIp());
		}
		return array;	
	}
}