package Database;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

class DatabaseImpl extends UnicastRemoteObject implements DatabaseRemote{
	
	ArrayList<Connection> list;
	
	DatabaseImpl() throws RemoteException{
		super();
		list = new ArrayList<Connection>();
		Connection henkie = new Connection("gangpad 2", 42);
		list.add(henkie);
	} 
	
	@Override
	public ArrayList<Connection> getConnections() throws RemoteException{
		return list;
	}
}