package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.Message;
import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.dto.MessageDTO;
import com.iongroup.restraining.service.MessageEntService;
import com.iongroup.restraining.service.MessageService;
import com.iongroup.restraining.service.RoomService;
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
public class MessageEntController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final UserService userService;

    private final RoomService roomService;

    private final MessageEntService messageService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageDTO messageDto) {
        System.out.println("handling send message: " + messageDto + " to: " + to);

        MessageEntity message = new MessageEntity();

        message.setRoom(roomService.findByUsersUsernames(messageDto.getSender(), messageDto.getRecipient()));
        message.setUser(userService.findByUsername(messageDto.getSender()));
        message.setText(messageDto.getText());
        message.setTimestamp(messageDto.getTimestamp());

        boolean isExists = userService.ifExist(to);
        if (isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
            messageService.save(message);
        }
    }

    @GetMapping("/getmessages")
    public List<MessageEntity> getPrivateMessages(@RequestParam String sender, @RequestParam String recipient) {
        boolean isExists = userService.ifExist(sender) && userService.ifExist(recipient);
        if (isExists) {
            return messageService.findMessagesByUsers(sender, recipient);
        }
        return List.of(new MessageEntity());
    }
}
