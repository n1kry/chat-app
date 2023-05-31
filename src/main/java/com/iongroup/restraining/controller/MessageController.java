package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.Message;
import com.iongroup.restraining.service.MessageService;
import com.iongroup.restraining.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final UserService userService;

    private final MessageService messageService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("handling send message: " + message + " to: " + to);
        boolean isExists = userService.ifExist(to);
        if (isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
            messageService.addNewMessage(message);
        }
    }

    @GetMapping("/getmessages")
    public List<Message> getPrivateMessages(@RequestParam String sender, @RequestParam String recipient) {
        boolean isExists = userService.ifExist(sender) && userService.ifExist(recipient);
        if (isExists) {
            return messageService.findAllMessagesInChatRoom(sender, recipient);
        }
        return List.of(new Message());
    }
}
