package Database;
import java.rmi.*;
import java.util.*;

interface DatabaseRemote extends Remote{
	public List<Connection> getConnections() throws RemoteException;	
}