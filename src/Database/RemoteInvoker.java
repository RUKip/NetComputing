package Database;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Sockets.Connection;

public class RemoteInvoker extends UnicastRemoteObject implements RemoteInterface{
	RemoteInvoker()throws RemoteException{
		super();
	}
	@Override
	public int insert(Connection c) throws RemoteException {
		Database db = new Database();
		db.add(c); 
		db.close();
		return 0; //everything went fine
	}
	
	public ArrayList<Connection> retrieveAll() throws	RemoteException{
		Database db = new Database();
		return db.getAll();
	}
}