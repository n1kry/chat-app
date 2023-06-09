package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.UserEntity;
import com.iongroup.restraining.entity.dto.*;
import com.iongroup.restraining.service.MessageEntService;
import com.iongroup.restraining.service.RoomService;
import com.iongroup.restraining.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

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
            message = messageService.save(message);
            simpMessagingTemplate.convertAndSend(
                    "/topic/messages/" + recipient,
                    MessagesDataDTO.getMessageDataDtoFromMessageEntity(message)
            );
        }
    }

    @PutMapping("/updatemessage")
    public void updateMessage(@RequestParam Long timestamp, @RequestBody UpdateMessageDTO messageDTO, Principal principal) {
        System.out.println("handling update message: " + messageDTO + " to: " + timestamp );

        UserEntity user = userService.findByUsername(principal.getName());

        MessageEntity message = messageService.findByTimestamp(timestamp);

        if (message.getUser().equals(user)) {
            message.setText(messageDTO.getText());

            messageService.save(message);

            simpMessagingTemplate.convertAndSend(
                    "/topic/updatemessage/" + messageDTO.getRecipient(),
                    MessagesDataDTO.getMessageDataDtoFromMessageEntity(message)
            );
        }
    }

    @DeleteMapping("/deletemessage")
    public void deleteMessage(@RequestBody DeleteMessageDTO messageDTO) {
        System.out.println("delete");

        MessageEntity message = messageService.findByTimestamp(messageDTO.getTimestamp());

        if (message != null) {
            messageService.delete(message);

            simpMessagingTemplate.convertAndSend(
                    "/topic/deletemsg/" + messageDTO.getRecipient(),
                    MessagesDataDTO.getMessageDataDtoFromMessageEntity(message)
            );
            simpMessagingTemplate.convertAndSend(
                    "/topic/deletemsg/" + messageDTO.getPrincipal(),
                    MessagesDataDTO.getMessageDataDtoFromMessageEntity(message)
            );
        }
    }

    @GetMapping("/getmessages")
    public List<MessagesDataDTO> getPrivateMessages(@RequestParam Long sender, @RequestParam Long recipient) {
        boolean isExists = userService.ifExistById(sender) && userService.ifExistById(recipient);

        if (isExists) {
            System.out.println(messageService.findMessagesByUsersId(sender, recipient).stream()
                    .map(e -> e.getTimestamp().getTime())
                    .toList());
            return messageService.findMessagesByUsersId(sender, recipient).stream()
                    .map(MessagesDataDTO::getMessageDataDtoFromMessageEntity)
                    .toList();
        }
//        simpMessagingTemplate.convertAndSend("/getmessages", "Hello");
        return List.of(new MessagesDataDTO());
    }
}
