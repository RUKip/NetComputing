package Sockets;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Connection {
        private String id;
        private String summary;
        private String address;

        public Connection(){

        }
        public Connection (String id, String summary){
                this.id = id;
                this.address = summary;
        }
        public String getId() {
                return id;
        }
        public void setId(String id) {
                this.id = id;
        }
        public String getSummary() {
                return summary;
        }
        public void setSummary(String summary) {
                this.summary = summary;
        }
        public String getAddress() {
                return address;
        }
        public void setAddress(String description) {
                this.address = description;
        }


}