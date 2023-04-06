package com.endava.restraining.service;

import com.endava.restraining.dao.MessageDAO;
import com.endava.restraining.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<Message> findAllMessagesInChatRoom(String sender, String recipient) {
        return messageDAO.findAllBySenderAndRecipientOrSenderAndRecipient(sender, recipient, recipient, sender);
    }

    public void addNewMessage(Message message) {messageDAO.save(message);}
}
