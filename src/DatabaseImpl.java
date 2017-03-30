import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

class DatabaseImpl extends UnicastRemoteObject implements DatabaseRemote{
	
	ArrayList<ConnectionObject> list;
	
	DatabaseImpl() throws RemoteException{
		super();
		this.list = new ArrayList<ConnectionObject>();
		ConnectionObject henkie = new ConnectionObject("127.0.0.1", 42);
		this.list.add(henkie);
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
