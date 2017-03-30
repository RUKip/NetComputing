import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;  
public interface Adder extends Remote{  
	

/*This is the interface of the methods*/
	public int add(int x,int y) throws RemoteException;  
	public ArrayList<Connection> getEntries(ArrayList<Connection> array) throws RemoteException;
	
}  