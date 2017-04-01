package MQ;

import java.util.LinkedList;
import java.util.Queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import Workload.Task;

public class MySender {

  private static final String TASK_QUEUE_NAME = "task_queue";
  private static Queue<Task> q;
  private static boolean run;

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

    q = new LinkedList<Task>();
    q.add(new Task(5,1,5));
    q.add(new Task(5,1,4));
    q.add(new Task(5,1,3));
    
    run = true;
    int i = 0;
    while(run && i < 3) {
    	Task t = q.poll();
    	if(t == null) {
    		Thread.sleep(500);
    	} else {
		    String message = t.toString();//getMessage(argv);
		
		    channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
    	} 
    	i++;
    }

    channel.close();
    connection.close();
  }
  
  public void addTask(Task t) {
	  q.add(t);
  }

//  private static String getMessage(String[] strings) {
//    if (strings.length < 1)
//      return "Hello World!";
//    return joinStrings(strings, " ");
//  }

//  private static String joinStrings(String[] strings, String delimiter) {
//    int length = strings.length;
//    if (length == 0) return "";
//    StringBuilder words = new StringBuilder(strings[0]);
//    for (int i = 1; i < length; i++) {
//      words.append(delimiter).append(strings[i]);
//    }
//    return words.toString();
//  }
}
