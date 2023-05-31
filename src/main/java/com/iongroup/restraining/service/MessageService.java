package com.iongroup.restraining.service;

import com.iongroup.restraining.dao.MessageDAO;
import com.iongroup.restraining.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageDAO messageDAO;

    public List<Message> findAllMessagesInChatRoom(String sender, String recipient) {
        return messageDAO.findAllBySenderAndRecipientOrSenderAndRecipient(sender, recipient, recipient, sender);
    }

    public void addNewMessage(Message message) {
        messageDAO.save(message);
    }
}
