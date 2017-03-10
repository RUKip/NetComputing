package Sockets;
import Interface.Menu;

public class Main {

	public static void main(String[] args){
		new Menu();
		new Server().run();
	}	
}
