import java.rmi.*;  
import java.rmi.registry.*;  

public class MyServer{  

	public static void main(String args[]){  

		try{  
			Adder stub=new AdderRemote();  
			Naming.rebind("rmi://0.0.0.0:5000/wonderland",stub);  
		}catch(Exception e){System.out.println(e);}  
	}  
}  