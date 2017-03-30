import java.rmi.*;
import java.util.*;

interface DatabaseRemote extends Remote{
	public List<ConnectionObject> getConnections() throws RemoteException;	
	public void addComputer(String a, int port) throws RemoteException;
}