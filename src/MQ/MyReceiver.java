package MQ;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MyReceiver {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}


//import javax.jms.*;  
//import javax.naming.InitialContext;  
//  
//public class MyReceiver {  
//    public static void main(String[] args) {  
//        try{  
//            //1) Create and start connection  
//            InitialContext ctx=new InitialContext();  
//            QueueConnectionFactory f=(QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");  
//            QueueConnection con=f.createQueueConnection();  
//            con.start();  
//            //2) create Queue session  
//            QueueSession ses=con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
//            //3) get the Queue object  
//            Queue t=(Queue)ctx.lookup("myQueue");  
//            //4)create QueueReceiver  
//            QueueReceiver receiver=ses.createReceiver(t);  
//              
//            //5) create listener object  
//            MyListener listener=new MyListener();  
//              
//            //6) register the listener object with receiver  
//            receiver.setMessageListener(listener);  
//              
//            System.out.println("Receiver1 is ready, waiting for messages...");  
//            System.out.println("press Ctrl+c to shutdown...");  
//            while(true){                  
//                Thread.sleep(1000);  
//            }  
//        }catch(Exception e){System.out.println(e);}  
//    }  
//  
//}

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
