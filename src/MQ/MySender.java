package MQ;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import Workload.Task;

//class was based on the tutorial for Message Queues for RabbitMQ
public class MySender implements Runnable {

  private static final String TASK_QUEUE_NAME = "task_queue";
  private static Queue<Task> q;
  private static boolean run;
  private static Random r = new Random();

  public static void main(String[] argv) throws Exception {
	 MySender.body();
  }
  
  @Override
  public void run() {
	  MySender.body();
  }
  
  private static void body() {
	  try {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

	    q = new LinkedList<Task>();
	    //for testing purposes
	    q.add(new Task(5,2,5));
	    q.add(new Task(3,1,4));
	    q.add(new Task(5,0,3));
	    q.add(new Task(4,1,4));
	    q.add(new Task(3,1,7));
	    q.add(new Task(5,0,9));
	    
	    run = true;
	    while(run) { 
    		int rand = r.nextInt(10);
    		//if(rand == 1) q.add(new Task(3,0,7));
	    	Task t = q.poll();
	    	if(t == null) {
	    		//q.add(new Task(5,1,9));
	    		Thread.sleep(500);
	    	} else {
			    String message = t.toString();
			
			    channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			    //System.out.println(" [x] Sent '" + message + "'");
	    	} 
	    }

	    channel.close();
	    connection.close();
	  } catch (Exception e) {
		e.printStackTrace();
	}
}

public void addTask(Task t) {
	  q.add(t);
  }

public void stop() {
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	run = false;
}

public void addTask(List<Task> list) {
	for (Task t : list) {
		this.addTask(t);
	}
}
}
