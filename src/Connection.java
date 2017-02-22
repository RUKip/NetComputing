

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Connection implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private InetAddress ip;
	private int port;
	
	public Connection(String address, int port) {
		try {
			this.ip = InetAddress.getByName(address);
			this.port = port;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public InetAddress getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	
	public boolean equals(Object o){
		Connection toCompare = (Connection) o;
		return (ip.equals(toCompare.ip) && this.port == toCompare.port);
	}
}
