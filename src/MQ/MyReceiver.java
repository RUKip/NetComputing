package MQ;

import com.rabbitmq.client.*;
import Workload.Core;
import Workload.Processor;
import Workload.Task;

import java.io.IOException;
import java.util.logging.*;

/*
 * To be able to run this:
 * include the jar files in NetComputing/caches
 * Install RabbitMQServer (which requires Erlang)
 */

//class was based on the tutorial for Message Queues for RabbitMQ
public class MyReceiver {

  private static final String TASK_QUEUE_NAME = "task_queue";
  private static DefaultConsumer consumer;
  private static Channel channel;
private static Processor processor;
  
  private static final Logger LOGGER = Logger.getLogger( MyReceiver.class.getName() );

	public MyReceiver(Processor processor) throws Exception {
		MyReceiver.processor = processor;
	    init();
	    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
	}

	//for testing purposes
	public static void main(String[] argv) throws Exception {
		new Processor(2,14);
	}

  private static void init() throws Exception {
	  FileHandler handler = new FileHandler("myapp-log.%u.%g.txt");
	  handler.setFormatter(new SimpleFormatter());
	  LOGGER.addHandler(handler);
	  
	  ConnectionFactory factory = new ConnectionFactory();
	  factory.setHost("127.0.0.1");
	  Connection connection = factory.newConnection();
	  channel = connection.createChannel();
      channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
	  //System.out.println(" [*] Waiting for messages");
      LOGGER.log( Level.FINE, "{X} Waiting for message");

	  channel.basicQos(1);

	  consumer = new DefaultConsumer(channel) {
		  @Override
		  public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
			  String message = new String(body, "UTF-8");
			  long tag = envelope.getDeliveryTag();
		      //System.out.println(" [x] Received '" + message + "'");
			  LOGGER.log( Level.FINE, "{X} Received '" + message + "'");
			  try {
				  Task t = new Task(message);
				  Core core = processor.getLeastActiveCore();
				  if (t.getLoadperSec() + t.getDeviation() > core.space()) {
					  //System.out.println("Refused");
					  channel.basicNack(envelope.getDeliveryTag(), false, true);
				  } else {
					  core.addTask(t);
					  channel.basicAck(tag, false);
				  }
			  } finally {
				  try {
					  Thread.sleep(1000);
				  } catch (InterruptedException e) {
					  e.printStackTrace();
				  }
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
}


