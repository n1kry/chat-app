package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.dto.MessageDTO;
import com.iongroup.restraining.entity.dto.UserDTO;
import com.iongroup.restraining.service.MessageEntService;
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

    @MessageMapping("/chat/{recipient}")
    public void sendMessage(@DestinationVariable Long recipient, MessageDTO messageDto) {
        System.out.println("handling send message: " + messageDto + " to: " + recipient);

        MessageEntity message = new MessageEntity();

        message.setRoom(roomService.findByUsersIds(messageDto.getSenderId(), messageDto.getRecipientId()));
        message.setUser(userService.findById(messageDto.getSenderId()));
        message.setText(messageDto.getText());
        message.setTimestamp(messageDto.getTimestamp());

        if (userService.ifExistById(recipient)) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + recipient, message);
            messageService.save(message);
        }
    }

    @GetMapping("/getmessages")
    public List<MessageEntity> getPrivateMessages(@RequestParam Long sender, @RequestParam Long recipient) {
        System.out.println(sender);
        System.out.println(recipient);
        boolean isExists = userService.ifExistById(sender) && userService.ifExistById(recipient);
        if (isExists) {
            return messageService.findMessagesByUsersId(sender, recipient);
        }
//        simpMessagingTemplate.convertAndSend("/getmessages", "Hello");
        return List.of(new MessageEntity());
    }
}
