import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

class DatabaseImpl extends UnicastRemoteObject implements DatabaseRemote{
	
	ArrayList<ConnectionObject> list;
	
	public DatabaseImpl() throws RemoteException{
		super();
		this.list = new ArrayList<ConnectionObject>();
	} 
	
	@Override
	public ArrayList<ConnectionObject> getConnections() throws RemoteException{
		return list;
	}
	
	@Override
	public void addComputer(String address, int port) throws RemoteException{
		list.add(new ConnectionObject(address, port));
	}
}
