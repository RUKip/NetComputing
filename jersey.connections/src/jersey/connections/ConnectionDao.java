package jersey.connections;

import java.util.HashMap;
import java.util.Map;



public enum ConnectionDao {
        instance;

        private Map<String, Connection> contentProvider = new HashMap<>();


        private ConnectionDao() {
       	
                Connection c = new Connection("1", "localhost");
                c.setSummary("This is the standard terminal");
                contentProvider.put(c.getId(), c);
                Connection c2 = new Connection("2", "192.168.0.10");
                c2.setSummary("This is a test connection");
                contentProvider.put(c2.getId(), c2);
//                c = new Connection("2", "This terminal is connected");
//                c.setAddress("145.97.175.140");
//                contentProvider.put("2", c);

        }
        public Map<String, Connection> getModel(){
                return contentProvider;
        }


}