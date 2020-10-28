package com.java.activiti.mq.producer.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *
 * @description  ������Ϣ�����ߣ�������Ϣ������
 *
 */
@Component("queueSender")
public class QueueSender {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;//ͨ��@Qualifier���η���ע���Ӧ��bean

    /**
     * ����һ����Ϣ��ָ���Ķ��У�Ŀ�꣩
     * @param queueName ��������
     * @param message ��Ϣ����
     */
    public void send(String queueName,final String message){
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
