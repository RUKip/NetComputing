package MQ;

import javax.jms.*;  
import javax.naming.InitialContext;  
  
public class MyReceiver {  
    public static void main(String[] args) {  
        try{  
            //1) Create and start connection  
            InitialContext ctx=new InitialContext();  
            QueueConnectionFactory f=(QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");  
            QueueConnection con=f.createQueueConnection();  
            con.start();  
            //2) create Queue session  
            QueueSession ses=con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
            //3) get the Queue object  
            Queue t=(Queue)ctx.lookup("myQueue");  
            //4)create QueueReceiver  
            QueueReceiver receiver=ses.createReceiver(t);  
              
            //5) create listener object  
            MyListener listener=new MyListener();  
              
            //6) register the listener object with receiver  
            receiver.setMessageListener(listener);  
              
            System.out.println("Receiver1 is ready, waiting for messages...");  
            System.out.println("press Ctrl+c to shutdown...");  
            while(true){                  
                Thread.sleep(1000);  
            }  
        }catch(Exception e){System.out.println(e);}  
    }  
  
}

//package MQ;
//
//import javax.annotation.Resource;
//import javax.ejb.*;
//import javax.jms.*;
//import java.util.Enumeration;
// 
////@Stateless
//public class MyReceiver {
// 
//    @Resource(mappedName = "jms/JMSConnectionFactory")
//    private ConnectionFactory connectionFactory;
// 
//    @Resource(mappedName = "jms/myQueue")
//    Queue myQueue;
// 
//    private String message;
// 
//    public String receiveMessage() {
//        try {
//            Connection connection = connectionFactory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            QueueBrowser queueBrowser = session.createBrowser(myQueue);
//            Enumeration enumeration = queueBrowser.getEnumeration();
//            while (enumeration.hasMoreElements()) {
//                TextMessage o = (TextMessage) enumeration.nextElement();
//               return "Receive " + o.getText();
// 
//            }
//            session.close();
//            connection.close();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//}
