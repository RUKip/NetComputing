package MQ;

import com.rabbitmq.client.*;
import Workload.Core;
import Workload.Processor;

import java.io.IOException;

public class MyReceiver {

  private static final String TASK_QUEUE_NAME = "task_queue";
  private static Core core;
  private static DefaultConsumer consumer;
  private static Channel channel;

	public MyReceiver(Core core) throws Exception {
		MyReceiver.core = core;
	    init();
	    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
	}

	public static void main(String[] argv) throws Exception {
//		core = null;
//	    init();
//	    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
		Processor p = new Processor(2,10);
	}

  private static void init() throws Exception {
	  ConnectionFactory factory = new ConnectionFactory();
	  factory.setHost("localhost");
	  Connection connection = factory.newConnection();
	  channel = connection.createChannel();
      channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
	  System.out.println(" [*] Waiting for messages");

	  channel.basicQos(1);

	  consumer = new DefaultConsumer(channel) {
		  @Override
		  public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
			  String message = new String(body, "UTF-8");
		
		      System.out.println(" [x] Received '" + message + "'");
			  try {
				  if (core == null)
					  doWork(message);
				  else {
					  System.out.println("core received task");
				  }
			  } finally {
				  System.out.println(" [x] Done");
				  channel.basicAck(envelope.getDeliveryTag(), false);
			  }
		  }
	  };
  }
  
  public void stop() {
	  try {
		channel.abort();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }

	private static void doWork(String task) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
			  try {
				  Thread.sleep(1000);
			  } catch (InterruptedException _ignored) {
				  Thread.currentThread().interrupt();
			  }
	      }
    }
  }
}


