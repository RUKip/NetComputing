import java.io.Serializable;

public class Message implements Serializable{
	
	 	public static final int CHECK_TYPE = 1, RESPONSE_TYPE = 2; 
		public static final String CHECK_MESSAGE = "Are you busy?", BUSY_MESSAGE = "Sorry im busy, find someone else", AVAILABLE_MESSAGE = "Yes im available, you can stop searching";		
		
		private int messageType;
		private String dataRequest; //on/off
		
		public Message(int messageType, String data) {
			this.messageType = messageType;
			this.dataRequest = data;
		}

		public int getMessageType() {
			return messageType;
		}

		public void setMessageType(int messageType) {
			this.messageType = messageType;
		}

		public String getData() {
			return dataRequest;
		}

		public void setData(String data) {
			this.dataRequest = data;
		}
		
}
