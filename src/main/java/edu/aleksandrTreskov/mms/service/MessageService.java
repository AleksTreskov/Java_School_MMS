package edu.aleksandrTreskov.mms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * Service for sending messages on second app
 */
@Service
public class MessageService {
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    public void sendEmailMessage() {
        MessageCreator messageCreator = session -> session.createTextMessage("update");
        jmsTemplate.send(messageCreator);
    }


}
