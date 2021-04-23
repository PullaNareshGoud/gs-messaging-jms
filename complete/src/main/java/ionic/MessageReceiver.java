package ionic;

import ionic.Msmq.MessageQueueException;
import ionic.Msmq.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Component
@EnableScheduling
public class MessageReceiver {
    @Autowired
    @Qualifier("test_queue")
    private Queue queue;
    private int i=1;
    @Scheduled(fixedDelay = 3000)
    public void receive() throws MessageQueueException {
        System.out.println("------Received message "+ i++);
        System.out.println(Optional.of(queue.receive()).map(message -> {
            try {
                System.out.println(message);
                return message.getBodyAsString();
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return null;
        }).orElse("No Message"));
    }

}
