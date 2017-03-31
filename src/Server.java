
import java.rmi.*;
import java.rmi.registry.*;

public class Server{
public static void main(String args[])throws Exception{
	DatabaseRemote r = new DatabaseImpl();
	Naming.rebind("rmi://localhost:6666/wonderland",r);
}
}