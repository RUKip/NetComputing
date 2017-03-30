package Database;

import java.rmi.Naming;
import java.rmi.Remote;
public class Server{
public static void main(String args[])throws Exception{
	Remote r = new DatabaseImpl();
	Naming.rebind("rmi://localhost:6666/javatpoint",r);
}
}