package com.java.activiti.mq.consumer.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TopicReceiver implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("TopicReceiver���յ���Ϣ:"+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
