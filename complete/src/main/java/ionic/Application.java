
package ionic;

import javax.jms.ConnectionFactory;

import ionic.Msmq.MessageQueueException;
import ionic.Msmq.Queue;
import org.apache.activemq.memory.buffer.MessageQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
@EnableJms
public class Application {

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
													DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean(name = "test_queue")
	public Queue testQueue() throws MessageQueueException {
		Queue queue =null;
		/*String fullname= ".\\private$\\" + "test";
		System.out.println("create (" + fullname + ")");
		String qLabel="Created by " + this.getClass().getName() + ".java";
		boolean transactional= false;  // should the queue be transactional
		Queue queue = Queue.create(fullname, qLabel, transactional);
		System.out.println("-----"+fullname+" created--------");*/
		///////////////

		try {
			if (queue!=null) {
				queue.close();
				queue= null;
			}
			String fullname="DIRECT=OS:.\\private$\\test"; //1
//			String fullname=".\\private$\\" + "test4"; //2
			System.out.println("open (" + fullname + ")");
			queue= new Queue(fullname); //1
//			String qLabel="Created by " + this.getClass().getName() + ".java"; //2

//			queue= Queue.create(fullname, qLabel, false); //2
			//queue= new Queue(fullname, 0x02); // 0x02 == SEND only
			System.out.println("open: OK.");
		}
		catch (MessageQueueException ex1) {
			System.out.println("Queue open failure: " + ex1);
		}
		return queue;
	}

	public static void main(String[] args) {
		// Launch the application
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	}

}
