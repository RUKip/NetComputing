
import java.rmi.*;
import java.rmi.registry.*;

public class Server{
public static void main(String args[])throws Exception{
	DatabaseRemote r = new DatabaseImpl();
	Naming.rebind("rmi://192.168.0.10:8851/wonderland",r);
	}
}