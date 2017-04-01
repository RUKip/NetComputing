

public class MenuModel {
	private String address;
	private int port;
	
	private static String nickname = "dummy";
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public static void setNickname(String nickname) {
		MenuModel.nickname = nickname;
	}
	
	public static String getNickname() {
		return nickname;
	}
	

}
