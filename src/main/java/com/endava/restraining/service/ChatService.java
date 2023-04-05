package com.endava.restraining.service;

import com.endava.restraining.dao.UserDAO;
import com.endava.restraining.entity.Message;
import com.endava.restraining.entity.UserEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserDAO userDAO;

    public ChatService(SimpMessagingTemplate messagingTemplate, UserDAO userDAO) {
        this.messagingTemplate = messagingTemplate;
        this.userDAO = userDAO;
        System.out.println("fsdgdgd");
    }

    public void sendMessage(String text, Principal principal) {
        UserEntity user = userDAO.findByUsername(principal.getName());
        System.out.println(principal.getName());
        if (user != null) {
            Message message = new Message(user.getUsername(), text);
            messagingTemplate.convertAndSend("/topic/messages", message);
        }
    }
}