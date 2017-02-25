import java.io.Serializable;

public class Message implements Serializable{
	
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
