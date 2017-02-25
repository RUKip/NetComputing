
public class Location {
	private String address;
	private int port;
	
	public Location(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
