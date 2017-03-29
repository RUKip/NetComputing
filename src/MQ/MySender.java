package MQ;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import javax.naming.*;  
import javax.jms.*;
  
public class MySender {  
    public static void main(String[] args) {  
        try  
        {   //Create and start connection  
            InitialContext ctx=new InitialContext();  
            QueueConnectionFactory f=(QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");  
            QueueConnection con=f.createQueueConnection();  
            con.start();  
            //2) create queue session  
            QueueSession ses=con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
            //3) get the Queue object  
            Queue t=(Queue)ctx.lookup("myQueue");  
            //4)create QueueSender object         
            QueueSender sender=ses.createSender(t);  
            //5) create TextMessage object  
            TextMessage msg=ses.createTextMessage();  
              
            //6) write message  
            BufferedReader b=new BufferedReader(new InputStreamReader(System.in));  
            while(true)  
            {  
                System.out.println("Enter Msg, end to terminate:");  
                String s=b.readLine();  
                if (s.equals("end"))  
                    break;  
                msg.setText(s);  
                //7) send message  
                sender.send(msg);  
                System.out.println("Message successfully sent.");  
            }  
            //8) connection close  
            con.close();  
        }catch(Exception e){System.out.println(e);}  
    }  
}  

//package MQ;
//
//import javax.annotation.Resource;
//import javax.jms.*;
// 
////@Stateless
//public class MySender {
// 
//    @Resource(mappedName = "jms/JMSConnectionFactory")
//    private ConnectionFactory connectionFactory;
// 
//    @Resource(mappedName = "jms/myQueue")
//    Queue queue;
// 
//    public void sendMessage(String message) {
//        MessageProducer messageProducer;
//        TextMessage textMessage;
//        try {
//            Connection connection = connectionFactory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            messageProducer = session.createProducer(queue);
//            textMessage = session.createTextMessage();
//            //textMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
// 
//            textMessage.setText(message);
//            messageProducer.send(textMessage);
// 
//            messageProducer.close();
//            session.close();
//            connection.close();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//
//}  