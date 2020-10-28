import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueReceiverTest implements MessageListener {
    @Test
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("QueueReceiver接收到消息:"+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
