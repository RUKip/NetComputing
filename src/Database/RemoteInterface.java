package Database;

import java.rmi.*;
import java.util.ArrayList;

import Sockets.Connection;

//This is the stub
public interface RemoteInterface extends Remote {
	public int insert(Connection c) throws RemoteException;
	public ArrayList<Employee> retrieveAll() throws RemoteException;
}
